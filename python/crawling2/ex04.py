# 인프런 사이트 크롤링

# 첫 페이지의 강의 목록 가져오기
# 추출 데이터: 강의 제목, 저자(지식공유자), 구독자 수, 평점, 리뷰 개수, 강의 가격(정상가·할인가), 썸네일 이미지 URL
#
# 인프런은 Mantine UI + Next.js(React) 기반 SPA 이므로 셀레니엄 필수.
# 셀레니엄으로 페이지 로드 후 page_source 를 BeautifulSoup 으로 파싱하면
# 브라우저 세션 문제(InvalidSessionIdException) 없이 안정적으로 데이터 추출 가능.

import sys
import time

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup

URL = "https://www.inflearn.com/courses"

# ── CSS 셀렉터 (BeautifulSoup / Mantine UI 기반) ────────────────────────────
# Mantine 컴포넌트 해시 클래스(mantine-xxxxx)는 props 기반으로 생성되어 안정적.
# 사이트 구조가 크게 바뀌면 F12 → Elements 탭에서 재확인이 필요합니다.
CARD_SEL         = "article.mantine-Card-root"  # 강의 카드 컨테이너 (셀레니엄 대기용)
TITLE_SEL        = "p.mantine-fcy4ne"           # 강의 제목
AUTHOR_SEL       = "p.mantine-aiouth"           # 저자(지식공유자)
STUDENT_SEL      = "span.mantine-jkxzgx"        # 구독자 수  (예: "3,100+")
RATING_SEL       = "p.mantine-3qdwx9"           # 평점       (예: "4.9")
REVIEW_SEL       = "p.mantine-1s1zpjz"          # 리뷰 개수  (예: "(113)")
PRICE_ORIGIN_SEL = "p.mantine-13cvopi"          # 정상가     (할인 없으면 요소 없음)
PRICE_CURR_SEL   = "p.mantine-cm9qo8"           # 현재가(할인가 또는 "무료")
THUMBNAIL_SEL    = "picture > img"              # 썸네일 이미지
# ────────────────────────────────────────────────────────────────────────────

if hasattr(sys.stdout, "reconfigure"):
    sys.stdout.reconfigure(encoding="utf-8")


def bs_text(card, selector):
    """카드에서 셀렉터에 해당하는 요소의 텍스트를 반환. 없으면 'N/A'."""
    el = card.select_one(selector)
    return el.get_text(strip=True) if el else "N/A"


def bs_attr(card, selector, attr):
    """카드에서 셀렉터에 해당하는 요소의 속성값을 반환. 없으면 'N/A'."""
    el = card.select_one(selector)
    return el.get(attr, "N/A") if el else "N/A"


driver = webdriver.Chrome()

try:
    driver.get(URL)
    wait = WebDriverWait(driver, 15)

    # React/Next.js 렌더링 완료까지 강의 카드가 DOM에 등장할 때까지 대기
    wait.until(
        EC.presence_of_all_elements_located((By.CSS_SELECTOR, CARD_SEL))
    )

    # 지연 로딩(Lazy-load) 이미지를 모두 로드하기 위해 단계적으로 스크롤
    total_height = driver.execute_script("return document.body.scrollHeight")
    step = total_height // 6 or 500
    for pos in range(0, total_height + step, step):
        driver.execute_script(f"window.scrollTo(0, {pos});")
        time.sleep(0.4)
    driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")

    # 브라우저 세션에서 HTML 수집 후 즉시 종료 → 세션 단절 문제 방지
    html = driver.page_source

finally:
    driver.quit()

# ── BeautifulSoup 으로 파싱 (브라우저 없이 처리) ────────────────────────────
soup = BeautifulSoup(html, "html.parser")
cards = soup.select(CARD_SEL)
print(f"총 {len(cards)}개 강의 카드 수집\n")

courses = []
seen_titles = set()  # 중복 카드 제거용 (인프런은 동일 카드를 2벌 렌더링)
for card in cards:
    title   = bs_text(card, TITLE_SEL)

    # 중복 카드 건너뜀
    if title in seen_titles:
        continue
    seen_titles.add(title)

    author  = bs_text(card, AUTHOR_SEL)
    student = bs_text(card, STUDENT_SEL)
    rating  = bs_text(card, RATING_SEL)
    review  = bs_text(card, REVIEW_SEL)

    # 정상가: 할인이 있을 때만 별도 표시됨 (없으면 현재가 = 정상가)
    price_origin = bs_text(card, PRICE_ORIGIN_SEL)
    price_curr   = bs_text(card, PRICE_CURR_SEL)
    if price_origin == "N/A":
        price_origin = price_curr  # 할인 없음 → 정상가 = 현재가

    thumbnail = bs_attr(card, THUMBNAIL_SEL, "src")

    courses.append(
        {
            "title":        title,
            "author":       author,
            "student":      student,
            "rating":       rating,
            "review":       review,
            "price_origin": price_origin,
            "price_curr":   price_curr,
            "thumbnail":    thumbnail,
        }
    )

print("=" * 60)
for idx, c in enumerate(courses, start=1):
    print(f"[{idx}] {c['title']}")
    print(f"  저자     : {c['author']}")
    print(f"  구독자수 : {c['student']}")
    print(f"  평점     : {c['rating']}")
    print(f"  리뷰     : {c['review']}")
    print(f"  정상가   : {c['price_origin']}")
    print(f"  할인가   : {c['price_curr']}")
    print(f"  썸네일   : {c['thumbnail']}")
    print("-" * 60)
