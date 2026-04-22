# PlayBoard 하이브리드 크롤러
# YouTube 검색으로 video_id 수집 → PlayBoard에서 정확한 조회수/구독자 통계 수집
# PlayBoard 데이터 없으면 YouTube 채널 페이지로 fallback

import re
import time

from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from webdriver_manager.chrome import ChromeDriverManager

PLAYBOARD_VIDEO = "https://playboard.co/video/{}"
PLAYBOARD_CH    = "https://playboard.co/channel/{}"


# ──────────────────────────────────────────
# 유틸
# ──────────────────────────────────────────

def parse_korean_number(text: str) -> int:
    """'1.2만 회', '100만명', '1,234' 등 한국어 숫자 표기를 정수로 변환"""
    if not text:
        return 0
    text = re.sub(r"[조회수\s명회,\+]", "", text).strip()
    try:
        if "억" in text:
            return int(float(text.replace("억", "")) * 1_0000_0000)
        if "만" in text:
            return int(float(text.replace("만", "")) * 10_000)
        if "천" in text:
            return int(float(text.replace("천", "")) * 1_000)
        return int(float(text))
    except (ValueError, TypeError):
        return 0


def build_driver(headless: bool = True) -> webdriver.Chrome:
    opts = Options()
    if headless:
        opts.add_argument("--headless=new")
    opts.add_argument("--no-sandbox")
    opts.add_argument("--disable-dev-shm-usage")
    opts.add_argument("--disable-blink-features=AutomationControlled")
    opts.add_argument("--window-size=1920,1080")
    opts.add_argument("--lang=ko-KR,ko")
    opts.add_argument(
        "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"
    )
    opts.add_experimental_option("excludeSwitches", ["enable-automation"])
    opts.add_experimental_option("useAutomationExtension", False)
    service = Service(ChromeDriverManager().install())
    driver = webdriver.Chrome(service=service, options=opts)
    driver.execute_cdp_cmd(
        "Page.addScriptToEvaluateOnNewDocument",
        {"source": "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"},
    )
    return driver


def _accept_consent(driver: webdriver.Chrome) -> None:
    """YouTube 쿠키 동의 팝업 처리"""
    try:
        btn = WebDriverWait(driver, 4).until(
            EC.element_to_be_clickable((
                By.XPATH,
                "//button[.//span[contains(text(),'모두 수락') "
                "or contains(text(),'Accept all') "
                "or contains(text(),'동의')]]",
            ))
        )
        btn.click()
        time.sleep(1)
    except Exception:
        pass


def _wait_react(driver: webdriver.Chrome, timeout: int = 10) -> None:
    """React SPA 렌더링 완료 대기"""
    try:
        WebDriverWait(driver, timeout).until(
            lambda d: d.execute_script("return document.readyState") == "complete"
        )
    except Exception:
        pass
    time.sleep(1.5)


# ──────────────────────────────────────────
# Step 1: YouTube 검색 → video_id 목록 수집
# ──────────────────────────────────────────

def _search_yt_for_ids(driver: webdriver.Chrome, keyword: str, max_results: int) -> list[dict]:
    """
    YouTube 검색 결과 페이지에서 video_id + 기본 메타 수집
    조회수는 여기서 수집하지 않고 PlayBoard에서 가져옴
    """
    driver.get(f"https://www.youtube.com/results?search_query={keyword}&sp=CAASAhAB")
    _accept_consent(driver)

    try:
        WebDriverWait(driver, 15).until(
            EC.presence_of_element_located((By.TAG_NAME, "ytd-video-renderer"))
        )
    except Exception:
        pass
    time.sleep(2)

    videos: list[dict] = []
    for renderer in driver.find_elements(By.TAG_NAME, "ytd-video-renderer")[:max_results]:
        try:
            title_el = renderer.find_element(By.CSS_SELECTOR, "a#video-title")
            title    = (title_el.get_attribute("title") or title_el.text).strip()
            href     = title_el.get_attribute("href") or ""
            video_id = href.split("v=")[-1].split("&")[0] if "v=" in href else ""
            if not title or not video_id:
                continue

            channel, channel_url, channel_id = "", "", ""
            try:
                ch = renderer.find_element(By.CSS_SELECTOR, "ytd-channel-name a")
                channel     = ch.text.strip()
                channel_url = ch.get_attribute("href") or ""
                channel_id  = channel_url.rstrip("/").split("/")[-1]
            except Exception:
                pass

            published = ""
            rough_views = 0
            try:
                spans = renderer.find_elements(By.CSS_SELECTOR, "#metadata-line span")
                for span in spans:
                    txt = span.text.strip()
                    if txt and ("회" in txt or re.search(r"[\d.]+[만억천]", txt)):
                        rough_views = parse_korean_number(txt)
                    else:
                        published = txt
            except Exception:
                pass

            videos.append({
                "video_id":    video_id,
                "title":       title,
                "channel":     channel,
                "channel_url": channel_url,
                "channel_id":  channel_id,
                "thumbnail":    f"https://i.ytimg.com/vi/{video_id}/hqdefault.jpg",
                "published":    published,
                "url":          f"https://www.youtube.com/watch?v={video_id}",
                "rough_views":  rough_views,
            })
        except Exception:
            continue

    return videos


# ──────────────────────────────────────────
# Step 2: PlayBoard → 정확한 조회수/구독자 수
# ──────────────────────────────────────────

def _parse_numbers_from_text(body: str) -> tuple[int, int]:
    """
    페이지 전체 텍스트에서 조회수·구독자 수를 정규식으로 추출
    PlayBoard는 React SPA라 selector가 불안정하므로 텍스트 파싱이 더 안정적
    """
    views = 0
    subs  = 0

    # 조회수: "총 조회수 1,234,567" / "조회수 1.2만" 등
    for pat in [
        r"총\s*조회수\s*([\d,.]+[만억천]?)",
        r"조회수\s*([\d,.]+[만억천]?)",
        r"([\d,.]+[만억천]?)\s*회",
    ]:
        m = re.search(pat, body)
        if m:
            v = parse_korean_number(m.group(1))
            if v > views:
                views = v

    # 구독자: "구독자 1.23만명" / "구독자수 1,234,567" 등
    for pat in [
        r"구독자\s*수?\s*([\d,.]+[만억천]?)\s*명",
        r"구독자\s*([\d,.]+[만억천]?)",
        r"([\d,.]+[만억천]?)\s*구독",
    ]:
        m = re.search(pat, body)
        if m:
            s = parse_korean_number(m.group(1))
            if s > subs:
                subs = s

    return views, subs


def _get_pb_video_stats(driver: webdriver.Chrome, video_id: str) -> dict | None:
    """
    PlayBoard 영상 페이지에서 조회수 + 채널 구독자 수 수집
    PlayBoard가 해당 영상을 인덱싱하지 않으면 None 반환
    """
    try:
        driver.get(PLAYBOARD_VIDEO.format(video_id))
        _wait_react(driver)

        # 404/미인덱싱 감지
        url_now = driver.current_url
        if "404" in url_now or "not-found" in url_now:
            return None

        body = driver.find_element(By.TAG_NAME, "body").text

        # PlayBoard가 콘텐츠를 로드했는지 최소 검증
        if len(body.strip()) < 100:
            return None

        views, subs = _parse_numbers_from_text(body)

        # 숫자가 너무 작으면 페이지 미로드로 간주
        if views == 0 and subs == 0:
            return None

        return {"views": views, "subs": subs}

    except Exception:
        return None


def _get_pb_channel_subs(driver: webdriver.Chrome, channel_id: str) -> int:
    """PlayBoard 채널 페이지에서 구독자 수만 수집"""
    if not channel_id:
        return 0
    try:
        driver.get(PLAYBOARD_CH.format(channel_id))
        _wait_react(driver)
        body = driver.find_element(By.TAG_NAME, "body").text
        _, subs = _parse_numbers_from_text(body)
        return subs
    except Exception:
        return 0


# ──────────────────────────────────────────
# Step 3: Fallback — YouTube 채널 페이지
# ──────────────────────────────────────────

def _get_yt_channel_subs(driver: webdriver.Chrome, channel_url: str) -> int:
    """PlayBoard에 없을 때 YouTube 채널 페이지에서 구독자 수 수집"""
    if not channel_url:
        return 1
    try:
        driver.get(channel_url)
        time.sleep(2)
        for sel in ["yt-formatted-string#subscriber-count", "#subscriber-count"]:
            try:
                el  = WebDriverWait(driver, 5).until(
                    EC.presence_of_element_located((By.CSS_SELECTOR, sel))
                )
                txt = el.text.strip()
                if txt:
                    return parse_korean_number(txt)
            except Exception:
                continue

        body = driver.find_element(By.TAG_NAME, "body").text
        m = re.search(r"구독자\s*([\d.]+[만억천]?)\s*명", body)
        if m:
            return parse_korean_number(m.group(1) + "명")
    except Exception:
        pass
    return 1


# ──────────────────────────────────────────
# 메인 공개 함수
# ──────────────────────────────────────────

def find_hot_videos(keyword: str, max_results: int = 12) -> list[dict]:
    """
    1) YouTube 검색 → video_id 목록
    2) PlayBoard 영상 페이지 → 정확한 조회수 + 구독자 수
       (PlayBoard 미인덱싱 시 YouTube 채널 페이지로 fallback)
    3) ratio = views / subs 로 정렬 → 핫 영상 반환
    """
    driver = build_driver(headless=True)
    try:
        videos = _search_yt_for_ids(driver, keyword, max_results)
        if not videos:
            return []

        # rough_views 기준 상위 5개만 PlayBoard 방문 (속도 최적화)
        pb_candidates = sorted(videos, key=lambda x: x["rough_views"], reverse=True)[:5]
        pb_candidate_ids = {v["video_id"] for v in pb_candidates}

        subs_pb_cache: dict[str, int] = {}   # channel_id → PlayBoard subs
        subs_yt_cache: dict[str, int] = {}   # channel_url → YouTube subs

        for v in videos:
            vid   = v["video_id"]
            ch_id = v["channel_id"]
            ch_url = v["channel_url"]

            # 상위 5개만 PlayBoard 방문, 나머지는 YouTube rough_views 사용
            pb = _get_pb_video_stats(driver, vid) if vid in pb_candidate_ids else None

            if pb and pb["views"] > 0:
                views = pb["views"]
                subs  = pb["subs"]

                # 구독자가 PlayBoard 영상 페이지에 없으면 PlayBoard 채널 페이지 시도
                if subs == 0 and ch_id:
                    if ch_id not in subs_pb_cache:
                        subs_pb_cache[ch_id] = _get_pb_channel_subs(driver, ch_id)
                    subs = subs_pb_cache[ch_id]

            else:
                # PlayBoard 미방문/미인덱싱 → YouTube rough_views 사용
                views = v["rough_views"]
                if ch_url not in subs_yt_cache:
                    subs_yt_cache[ch_url] = _get_yt_channel_subs(driver, ch_url)
                subs = subs_yt_cache[ch_url]

            v["views"]    = views
            v["subs"]     = max(subs, 1)
            v["likes"]    = 0
            v["comments"] = 0
            v["ratio"]    = round(views / max(subs, 1), 4)
            time.sleep(0.2)

        videos.sort(key=lambda x: x["ratio"], reverse=True)
        # ratio 0인 항목(PlayBoard 미인덱싱 + 조회수 미수집)은 후순위로
        return videos

    finally:
        driver.quit()


if __name__ == "__main__":
    import sys
    sys.stdout.reconfigure(encoding="utf-8")  # type: ignore[union-attr]

    kw = "먹방"
    print(f"\n[PlayBoard 하이브리드] 키워드 '{kw}' 핫 영상 분석\n")
    for i, v in enumerate(find_hot_videos(kw, max_results=10), 1):
        print(f"  [{i:>2}] ratio={v['ratio']:>8.2f}  조회={v['views']:>10,}  구독={v['subs']:>10,}")
        print(f"        {v['title']}")
        print(f"        {v['url']}\n")
