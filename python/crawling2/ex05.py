"""
인프런 인강 사이트 크롤링 / 인사이트 분석
─────────────────────────────────────────
  [1] 인기 강의 Top 10          (구독자 수 기준)
  [2] 인기 카테고리 Top 5       (카테고리별 총 수강생 합산)
  [3] 강사 추정 수익 Top 10     (수강생 수 × 현재가 단순 추정, 플랫폼 수수료 미포함)
  [4] 구독자 많은 강의 Top 30   (전체 수집 강의 중 수강생 수 상위)

결과는 inflearn_insights.db (SQLite)  와  inflearn_insights.csv  두 파일로 저장됩니다.
직접 열어서 데이터를 확인하세요.
"""

import sys
import time
import re
import csv
import sqlite3
import os
import unicodedata
from datetime import datetime
from collections import defaultdict

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup

# ── Windows 터미널 한글 깨짐 방지 ─────────────────────────────────────────────
if hasattr(sys.stdout, "reconfigure"):
    sys.stdout.reconfigure(encoding="utf-8")

# ── 크롤링 대상 URL ───────────────────────────────────────────────────────────
# 인기순 정렬 페이지: 실제 인기 강의가 상위에 노출됨
POPULAR_URL = "https://www.inflearn.com/courses?order=POPULARITY"
# 최신순 정렬 페이지: 최근 등록된 강의 포함
NEW_URL     = "https://www.inflearn.com/courses?order=NEW"

# ── CSS 셀렉터 (Mantine UI + Next.js 기반, ex04.py 와 동일) ──────────────────
# 사이트가 SPA(Single Page Application)이기 때문에 Selenium 으로 렌더링 후 파싱
CARD_SEL         = "article.mantine-Card-root"   # 강의 카드 전체 컨테이너
TITLE_SEL        = "p.mantine-fcy4ne"            # 강의 제목
AUTHOR_SEL       = "p.mantine-aiouth"            # 강사(지식공유자) 이름
STUDENT_SEL      = "span.mantine-jkxzgx"         # 수강생 수  예: "3,100+"
RATING_SEL       = "p.mantine-3qdwx9"            # 평점       예: "4.9"
REVIEW_SEL       = "p.mantine-1s1zpjz"           # 리뷰 수    예: "(113)"
PRICE_ORIGIN_SEL = "p.mantine-13cvopi"           # 정상가 (할인 없으면 요소 없음)
PRICE_CURR_SEL   = "p.mantine-cm9qo8"            # 현재가 (할인가 또는 "무료")
THUMBNAIL_SEL    = "picture > img"               # 썸네일 이미지
LINK_SEL         = "a[href*='/course/']"          # 강의 상세 링크
BADGE_SEL        = "span.mantine-Badge-label"     # 카테고리 배지 텍스트

# ── 저장 파일 경로 (스크립트 파일과 같은 폴더에 저장) ────────────────────────
SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
DB_FILE    = os.path.join(SCRIPT_DIR, "inflearn_insights.db")
CSV_FILE   = os.path.join(SCRIPT_DIR, "inflearn_insights.csv")


# ─────────────────────────────────────────────────────────────────────────────
# 헬퍼 함수들
# ─────────────────────────────────────────────────────────────────────────────

def bs_text(tag, selector):
    """
    BeautifulSoup 태그에서 CSS 셀렉터로 요소를 찾아 텍스트 반환.
    요소가 없으면 'N/A' 반환.
    """
    el = tag.select_one(selector)
    return el.get_text(strip=True) if el else "N/A"


def bs_attr(tag, selector, attr):
    """
    BeautifulSoup 태그에서 CSS 셀렉터로 요소를 찾아 특정 속성값 반환.
    요소가 없으면 'N/A' 반환.
    """
    el = tag.select_one(selector)
    return el.get(attr, "N/A") if el else "N/A"


def parse_student_count(text):
    """
    수강생 수 문자열을 정수로 변환.
    예)  '3,100+'  → 3100
         '1.5만+'  → 15000
         '2만'     → 20000
    변환 실패 시 0 반환.
    """
    if not text or text == "N/A":
        return 0
    text = text.replace(",", "").replace("+", "").strip()
    if "만" in text:
        try:
            return int(float(text.replace("만", "")) * 10000)
        except ValueError:
            return 0
    try:
        return int(float(text))
    except ValueError:
        return 0


def parse_review_count(text):
    """
    리뷰 수 문자열에서 괄호를 제거하고 정수로 변환.
    예)  '(113)'  → 113
    """
    if not text or text == "N/A":
        return 0
    cleaned = text.strip("()")
    try:
        return int(cleaned.replace(",", ""))
    except ValueError:
        return 0


def parse_price(text):
    """
    가격 문자열을 정수(원)로 변환.
    예)  '₩55,000'  → 55000
         '무료'      → 0
         'N/A'       → 0
    """
    if not text or text in ("N/A", "무료", "FREE"):
        return 0
    # ₩ 기호, 쉼표, 공백, '원' 글자 제거 후 숫자만 추출
    cleaned = re.sub(r"[₩,원\s]", "", text)
    try:
        return int(cleaned)
    except ValueError:
        return 0


# ─────────────────────────────────────────────────────────────────────────────
# 크롤링 함수
# ─────────────────────────────────────────────────────────────────────────────

def crawl_page(url, scroll_steps=10):
    """
    Selenium 으로 URL 을 열고 스크롤을 내려 지연 로딩된 카드를 모두 로드한 뒤
    페이지 HTML 소스를 문자열로 반환.

    scroll_steps: 스크롤 반복 횟수 (많을수록 더 많은 강의 카드 로드)
    """
    driver = webdriver.Chrome()
    try:
        print(f"  브라우저 열기: {url}")
        driver.get(url)

        # WebDriverWait: 강의 카드가 DOM 에 나타날 때까지 최대 20초 대기
        # React/Next.js SPA 는 JS 렌더링이 끝나야 카드가 표시됨
        wait = WebDriverWait(driver, 20)
        wait.until(
            EC.presence_of_all_elements_located((By.CSS_SELECTOR, CARD_SEL))
        )
        time.sleep(1.5)  # 초기 렌더링 완료 후 추가 대기

        # 단계적 스크롤: 한 번에 600px 씩 내려가며 Lazy-load 이미지/카드 로드
        for step in range(scroll_steps):
            driver.execute_script("window.scrollBy(0, 600);")
            time.sleep(0.5)

        # 맨 아래까지 한 번 더 스크롤 후 최종 렌더링 대기
        driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
        time.sleep(1.5)

        # page_source: 브라우저가 렌더링한 완성된 HTML 가져오기
        html = driver.page_source
        print(f"  HTML 수집 완료 ({len(html):,} bytes)")
        return html

    finally:
        # 데이터를 다 가져온 뒤 브라우저 반드시 종료 (세션 문제 방지)
        driver.quit()


# ─────────────────────────────────────────────────────────────────────────────
# 파싱 함수
# ─────────────────────────────────────────────────────────────────────────────

def parse_cards(html):
    """
    HTML 에서 강의 카드를 파싱해 딕셔너리 리스트로 반환.
    동일 제목의 중복 카드는 자동으로 제거 (인프런은 같은 카드를 2벌 렌더링).
    """
    soup   = BeautifulSoup(html, "html.parser")
    cards  = soup.select(CARD_SEL)
    print(f"  카드 요소 발견: {len(cards)}개 → 중복 제거 중...")

    courses     = []
    seen_titles = set()   # 이미 처리한 제목을 기억해 중복 방지

    for card in cards:
        title = bs_text(card, TITLE_SEL)

        # 제목이 없거나 이미 처리된 카드는 건너뜀
        if not title or title == "N/A" or title in seen_titles:
            continue
        seen_titles.add(title)

        author          = bs_text(card, AUTHOR_SEL)
        student_raw     = bs_text(card, STUDENT_SEL)      # 예: "3,100+"
        rating          = bs_text(card, RATING_SEL)
        review_raw      = bs_text(card, REVIEW_SEL)       # 예: "(113)"
        price_origin_raw= bs_text(card, PRICE_ORIGIN_SEL)
        price_curr_raw  = bs_text(card, PRICE_CURR_SEL)
        thumbnail       = bs_attr(card, THUMBNAIL_SEL, "src")

        # 강의 상세 링크 (없으면 'N/A')
        link_el = card.select_one(LINK_SEL)
        href    = link_el.get("href", "N/A") if link_el else "N/A"
        # 상대 경로이면 절대 URL 로 변환
        if href and href.startswith("/"):
            href = "https://www.inflearn.com" + href

        # 카테고리 배지: 여러 개 있을 수 있으므로 첫 번째만 사용
        badges   = card.select(BADGE_SEL)
        category = badges[0].get_text(strip=True) if badges else "기타"

        # 할인 없는 강의는 정상가 요소가 없으므로 현재가로 대체
        if price_origin_raw == "N/A":
            price_origin_raw = price_curr_raw

        # 문자열 → 숫자 변환
        student_cnt  = parse_student_count(student_raw)
        review_cnt   = parse_review_count(review_raw)
        price_origin = parse_price(price_origin_raw)
        price_curr   = parse_price(price_curr_raw)

        # 추정 수익 = 수강생 수 × 현재가
        # (실제 수익은 인프런 수수료 약 20~30% 차감 후이며, 이 값은 단순 추정치)
        est_revenue = student_cnt * price_curr

        courses.append({
            "title":        title,
            "author":       author,
            "category":     category,
            "student_cnt":  student_cnt,    # 수강생 수 (정수)
            "rating":       rating,
            "review_cnt":   review_cnt,     # 리뷰 수 (정수)
            "price_origin": price_origin,   # 정상가 (원, 정수)
            "price_curr":   price_curr,     # 현재가 (원, 정수)
            "est_revenue":  est_revenue,    # 추정 수익 (원, 정수)
            "thumbnail":    thumbnail,
            "href":         href,
        })

    print(f"  유효 강의 수집: {len(courses)}개")
    return courses


# ─────────────────────────────────────────────────────────────────────────────
# 분석 함수들
# ─────────────────────────────────────────────────────────────────────────────

def merge_courses(lists):
    """
    여러 URL 에서 수집한 강의 목록을 하나로 합치고 중복(같은 제목)을 제거.
    딕셔너리 키를 제목으로 사용해 나중에 들어온 값은 무시.
    """
    merged = {}
    for courses in lists:
        for c in courses:
            if c["title"] not in merged:
                merged[c["title"]] = c
    return list(merged.values())


def top_popular(courses, n=10):
    """
    인기 강의 Top N.
    기준: 수강생 수(student_cnt) 내림차순 정렬.
    """
    return sorted(courses, key=lambda x: x["student_cnt"], reverse=True)[:n]


def top_categories(courses, n=5):
    """
    인기 카테고리 Top N.
    기준: 카테고리별 총 수강생 수 합산 후 내림차순 정렬.
    '기타' 카테고리는 배지 인식 실패한 강의들의 집합.
    """
    cat_students = defaultdict(int)  # 카테고리 → 총 수강생
    cat_count    = defaultdict(int)  # 카테고리 → 강의 수

    for c in courses:
        cat_students[c["category"]] += c["student_cnt"]
        cat_count[c["category"]]    += 1

    result = [
        {
            "category":       k,
            "total_students": v,
            "course_count":   cat_count[k],
        }
        for k, v in cat_students.items()
    ]
    return sorted(result, key=lambda x: x["total_students"], reverse=True)[:n]


def top_instructors(courses, n=10):
    """
    강사 추정 수익 Top N.
    기준: 강사별 (수강생 수 × 현재가) 합산 후 내림차순 정렬.
    주의: 실제 수익이 아닌 단순 추정값 (플랫폼 수수료·환불 미포함).
    """
    data = defaultdict(lambda: {
        "est_revenue":    0,
        "courses":        0,
        "total_students": 0,
    })

    for c in courses:
        author = c["author"]
        data[author]["est_revenue"]    += c["est_revenue"]
        data[author]["courses"]        += 1
        data[author]["total_students"] += c["student_cnt"]

    result = [
        {
            "author":         k,
            "est_revenue":    v["est_revenue"],
            "courses":        v["courses"],
            "total_students": v["total_students"],
        }
        for k, v in data.items()
    ]
    return sorted(result, key=lambda x: x["est_revenue"], reverse=True)[:n]


def top_by_students(courses, n=30):
    """
    수강생 많은 강의 Top N.
    기준: 수강생 수(student_cnt) 내림차순 정렬.
    인기 강의 Top 10 과 기준은 같지만 더 넓은 범위(30개)를 봄.
    """
    return sorted(courses, key=lambda x: x["student_cnt"], reverse=True)[:n]


# ─────────────────────────────────────────────────────────────────────────────
# 저장 함수들
# ─────────────────────────────────────────────────────────────────────────────

def save_to_db(db_path, all_courses, pop10, cat5, inst10, sub30):
    """
    SQLite 데이터베이스에 5개 테이블로 결과를 저장.
      - courses        : 전체 수집 강의 (원시 데이터)
      - top_popular    : 인기 강의 Top 10
      - top_categories : 인기 카테고리 Top 5
      - top_instructors: 강사 추정 수익 Top 10
      - top_by_students: 수강생 많은 강의 Top 30

    DB Browser for SQLite (무료 툴) 로 .db 파일을 열면 테이블을 시각적으로 확인 가능.
    """
    conn = sqlite3.connect(db_path)
    cur  = conn.cursor()

    # ── 테이블 생성 (없으면 생성, 있으면 유지) ─────────────────────────────
    cur.execute("""
        CREATE TABLE IF NOT EXISTS courses (
            id           INTEGER PRIMARY KEY AUTOINCREMENT,
            title        TEXT,
            author       TEXT,
            category     TEXT,
            student_cnt  INTEGER,
            rating       TEXT,
            review_cnt   INTEGER,
            price_origin INTEGER,
            price_curr   INTEGER,
            est_revenue  INTEGER,
            thumbnail    TEXT,
            href         TEXT,
            crawled_at   TEXT
        )
    """)

    cur.execute("""
        CREATE TABLE IF NOT EXISTS top_popular (
            rank        INTEGER,
            title       TEXT,
            author      TEXT,
            student_cnt INTEGER,
            rating      TEXT,
            price_curr  INTEGER
        )
    """)

    cur.execute("""
        CREATE TABLE IF NOT EXISTS top_categories (
            rank           INTEGER,
            category       TEXT,
            course_count   INTEGER,
            total_students INTEGER
        )
    """)

    cur.execute("""
        CREATE TABLE IF NOT EXISTS top_instructors (
            rank           INTEGER,
            author         TEXT,
            courses        INTEGER,
            total_students INTEGER,
            est_revenue    INTEGER
        )
    """)

    cur.execute("""
        CREATE TABLE IF NOT EXISTS top_by_students (
            rank        INTEGER,
            title       TEXT,
            author      TEXT,
            student_cnt INTEGER,
            rating      TEXT,
            price_curr  INTEGER
        )
    """)

    # ── 기존 데이터 삭제 후 재삽입 (매번 최신 데이터로 갱신) ─────────────
    now = datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    cur.execute("DELETE FROM courses")
    for c in all_courses:
        cur.execute(
            """INSERT INTO courses
               (title,author,category,student_cnt,rating,review_cnt,
                price_origin,price_curr,est_revenue,thumbnail,href,crawled_at)
               VALUES (?,?,?,?,?,?,?,?,?,?,?,?)""",
            (c["title"], c["author"], c["category"], c["student_cnt"],
             c["rating"], c["review_cnt"], c["price_origin"], c["price_curr"],
             c["est_revenue"], c["thumbnail"], c["href"], now),
        )

    cur.execute("DELETE FROM top_popular")
    for rank, c in enumerate(pop10, 1):
        cur.execute(
            "INSERT INTO top_popular VALUES (?,?,?,?,?,?)",
            (rank, c["title"], c["author"], c["student_cnt"],
             c["rating"], c["price_curr"]),
        )

    cur.execute("DELETE FROM top_categories")
    for rank, c in enumerate(cat5, 1):
        cur.execute(
            "INSERT INTO top_categories VALUES (?,?,?,?)",
            (rank, c["category"], c["course_count"], c["total_students"]),
        )

    cur.execute("DELETE FROM top_instructors")
    for rank, c in enumerate(inst10, 1):
        cur.execute(
            "INSERT INTO top_instructors VALUES (?,?,?,?,?)",
            (rank, c["author"], c["courses"],
             c["total_students"], c["est_revenue"]),
        )

    cur.execute("DELETE FROM top_by_students")
    for rank, c in enumerate(sub30, 1):
        cur.execute(
            "INSERT INTO top_by_students VALUES (?,?,?,?,?,?)",
            (rank, c["title"], c["author"], c["student_cnt"],
             c["rating"], c["price_curr"]),
        )

    conn.commit()
    conn.close()
    print(f"[DB]  저장 완료 → {db_path}")


def save_to_csv(csv_path, all_courses):
    """
    전체 수집 강의 데이터를 CSV 파일로 저장.
    인코딩: utf-8-sig (BOM 포함) → 엑셀에서 한글이 깨지지 않음.
    """
    if not all_courses:
        print("[CSV] 저장할 데이터가 없습니다.")
        return

    fieldnames = [
        "title", "author", "category",
        "student_cnt", "rating", "review_cnt",
        "price_origin", "price_curr", "est_revenue",
        "thumbnail", "href",
    ]

    with open(csv_path, "w", newline="", encoding="utf-8-sig") as f:
        writer = csv.DictWriter(f, fieldnames=fieldnames)
        writer.writeheader()
        for c in all_courses:
            # 필요한 필드만 추출해 저장 (extra key 는 무시)
            writer.writerow({k: c.get(k, "") for k in fieldnames})

    print(f"[CSV] 저장 완료 → {csv_path}")


# ─────────────────────────────────────────────────────────────────────────────
# 출력 헬퍼
# ─────────────────────────────────────────────────────────────────────────────

_W = 76  # 콘솔 출력 너비

def _dw(s):
    """유니코드 표시 너비 계산 (한글·한자 등 전각 문자 = 2, 나머지 = 1)"""
    return sum(2 if unicodedata.east_asian_width(c) in ("W", "F") else 1 for c in str(s))

def _rpad(s, w):
    """표시 너비(display width) 기준 오른쪽 공백 패딩"""
    s = str(s)
    return s + " " * max(0, w - _dw(s))

def _trunc(s, max_w, suffix="…"):
    """표시 너비 기준 텍스트 자르기 (넘치면 suffix 로 대체)"""
    s = str(s)
    w, out = 0, []
    for ch in s:
        cw = 2 if unicodedata.east_asian_width(ch) in ("W", "F") else 1
        if w + cw > max_w - 1:
            return "".join(out) + suffix
        out.append(ch)
        w += cw
    return s

def _price_str(price):
    return f"{price:,}원" if price else "무료"

def print_section(header):
    """섹션 구분선과 제목 출력"""
    bar = "═" * _W
    print(f"\n{bar}")
    print(f"  {header}")
    print(bar)


# ─────────────────────────────────────────────────────────────────────────────
# 메인 실행
# ─────────────────────────────────────────────────────────────────────────────

if __name__ == "__main__":

    # ── 1단계: 크롤링 ─────────────────────────────────────────────────────────
    print("═" * _W)
    print("  인프런 크롤링 시작")
    print("═" * _W)

    # 인기순 페이지 크롤링 (더 많은 스크롤로 더 많은 카드 로드)
    print("\n[1/2] 인기순 페이지 크롤링...")
    html_popular    = crawl_page(POPULAR_URL, scroll_steps=12)
    courses_popular = parse_cards(html_popular)

    # 최신순 페이지 크롤링 (최근 등록 강의 확보)
    print("\n[2/2] 최신순 페이지 크롤링...")
    html_new    = crawl_page(NEW_URL, scroll_steps=8)
    courses_new = parse_cards(html_new)

    # 두 페이지 데이터 합치기 (중복 강의 자동 제거)
    all_courses = merge_courses([courses_popular, courses_new])
    print(f"\n총 수집 강의 수 (중복 제거 후): {len(all_courses)}개")

    # ── 2단계: 분석 ───────────────────────────────────────────────────────────
    pop10  = top_popular(all_courses, n=10)    # 인기 강의 Top 10
    cat5   = top_categories(all_courses, n=5)  # 인기 카테고리 Top 5
    inst10 = top_instructors(all_courses, n=10)# 강사 추정 수익 Top 10
    sub30  = top_by_students(all_courses, n=30)# 수강생 많은 강의 Top 30

    # ── 3단계: 콘솔 출력 ──────────────────────────────────────────────────────
    now_str = datetime.now().strftime("%Y-%m-%d %H:%M")
    print("\n" + "═" * _W)
    print("  인프런 인사이트 분석 결과")
    print(f"  수집 일시 : {now_str}  │  총 강의 수 : {len(all_courses)}개")
    print("═" * _W)

    # ── 인기 강의 Top 10 ──────────────────────────────────────────────────────
    print_section("인기 강의 Top 10  (수강생 수 기준)")
    print(f"  {'':2}   {'수강생':^9}   {'강의명':<28}  {'평점':^4}  {'강사':<12}  {'가격'}")
    print("  " + "─" * (_W - 2))
    for i, c in enumerate(pop10, 1):
        print(
            f"  {i:>2}.  {c['student_cnt']:>7,}명   "
            f"{_rpad(_trunc(c['title'], 28), 28)}  "
            f"★{c['rating']}  "
            f"{_rpad(_trunc(c['author'], 12), 12)}  "
            f"{_price_str(c['price_curr'])}"
        )

    # ── 인기 카테고리 Top 5 ───────────────────────────────────────────────────
    print_section("인기 카테고리 Top 5  (총 수강생 합산 기준)")
    print(f"  {'':2}   {'카테고리':<16}  {'강의 수':^7}  {'총 수강생':^12}")
    print("  " + "─" * (_W - 2))
    for i, c in enumerate(cat5, 1):
        print(
            f"  {i}.   "
            f"{_rpad(c['category'], 16)}  "
            f"강의 {c['course_count']:>3}개   "
            f"총 수강생  {c['total_students']:>8,}명"
        )

    # ── 강사 추정 수익 Top 10 ──────────────────────────────────────────────────
    print_section("강사 추정 수익 Top 10  (수강생 × 현재가, 단순 추정)")
    print(f"  {'':2}   {'강사':<16}  {'강의':^4}  {'추정 수익':^12}  {'총 수강생':^10}")
    print("  " + "─" * (_W - 2))
    for i, c in enumerate(inst10, 1):
        rev = c["est_revenue"] / 1_0000_0000
        print(
            f"  {i:>2}.  "
            f"{_rpad(_trunc(c['author'], 16), 16)}  "
            f"강의 {c['courses']:>2}개   "
            f"추정 수익  {rev:>5.1f}억원   "
            f"수강생 {c['total_students']:>6,}명"
        )

    # ── 수강생 많은 강의 Top 30 ───────────────────────────────────────────────
    print_section("수강생 많은 강의 Top 30  (전체 수집 강의 기준)")
    print(f"  {'':2}   {'수강생':^9}   {'강의명':<32}  {'강사':<14}  {'가격'}")
    print("  " + "─" * (_W - 2))
    for i, c in enumerate(sub30, 1):
        print(
            f"  {i:>2}.  {c['student_cnt']:>7,}명   "
            f"{_rpad(_trunc(c['title'], 32), 32)}  "
            f"{_rpad(_trunc(c['author'], 14), 14)}  "
            f"{_price_str(c['price_curr'])}"
        )

    # ── 4단계: 파일 저장 ──────────────────────────────────────────────────────
    print("\n" + "─" * _W)
    save_to_db(DB_FILE,  all_courses, pop10, cat5, inst10, sub30)
    save_to_csv(CSV_FILE, all_courses)
    print("─" * _W)
    print()
    print(f"  저장 경로")
    print(f"    DB  →  {DB_FILE}")
    print(f"    CSV →  {CSV_FILE}")
    print()
    print("  DB 파일은 'DB Browser for SQLite',  CSV 파일은 엑셀로 바로 열 수 있습니다.")
    print("═" * _W)
