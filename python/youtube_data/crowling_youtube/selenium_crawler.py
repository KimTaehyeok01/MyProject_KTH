# Selenium 크롤러 - PlayBoard (React 기반 유튜브 분석 사이트)
# https://playboard.co - 채널 순위, 인기 영상 등 제공

import time
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from webdriver_manager.chrome import ChromeDriverManager


# PlayBoard 유효 URL 목록 (2026.04 기준)
PLAYBOARD_URLS = {
    "인기채널_전체":    "https://playboard.co/youtube-ranking/most-popular-all-channels-in-south-korea-daily",
    "구독급상승":       "https://playboard.co/youtube-ranking/most-growth-all-channels-in-south-korea-daily",
    "최다라이브시청":   "https://playboard.co/youtube-ranking/most-watched-all-channels-in-south-korea-daily",
    "슈퍼챗순위":       "https://playboard.co/youtube-ranking/most-superchatted-all-channels-in-south-korea-daily",
    "최다조회영상":     "https://playboard.co/chart/video/most-viewed-all-videos-in-south-korea-daily",
    "먹방채널":         "https://playboard.co/youtube-ranking/most-popular-mukbang-channels-in-south-korea-daily",
}


def build_driver(headless: bool = True) -> webdriver.Chrome:
    """webdriver-manager로 버전 자동 맞춤 ChromeDriver 생성"""
    opts = Options()
    if headless:
        opts.add_argument("--headless=new")
    opts.add_argument("--no-sandbox")
    opts.add_argument("--disable-dev-shm-usage")
    opts.add_argument("--disable-blink-features=AutomationControlled")
    opts.add_argument("--window-size=1920,1080")
    opts.add_argument(
        "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"
    )
    opts.add_experimental_option("excludeSwitches", ["enable-automation"])

    service = Service(ChromeDriverManager().install())
    return webdriver.Chrome(service=service, options=opts)


def _wait_and_get(driver: webdriver.Chrome, timeout: int = 12) -> None:
    """React SPA 렌더링 완료 대기"""
    try:
        WebDriverWait(driver, timeout).until(
            lambda d: d.execute_script("return document.readyState") == "complete"
        )
    except Exception:
        pass
    time.sleep(3)  # JS 번들 렌더링 추가 대기


def crawl_playboard_home() -> dict:
    """
    PlayBoard 메인 페이지에서 카테고리별 채널 목록 수집
    (로그인 불필요 공개 데이터)
    """
    driver = build_driver(headless=True)
    result = {"channels": [], "videos": [], "live": []}

    try:
        driver.get("https://playboard.co/")
        _wait_and_get(driver)

        links = driver.find_elements(By.TAG_NAME, "a")

        for link in links:
            href = link.get_attribute("href") or ""
            text = link.text.strip()
            if not text or not href:
                continue

            # 채널 링크 수집
            if "/channel/" in href and text:
                result["channels"].append({
                    "name": text,
                    "url":  href,
                    "id":   href.split("/channel/")[-1],
                })

            # 영상 링크 수집
            elif "/video/" in href and text and len(text) > 5:
                result["videos"].append({
                    "title": text,
                    "url":   href,
                    "id":    href.split("/video/")[-1].split("?")[0],
                })

            # 라이브 링크 수집
            elif "/live" in href and text.isdigit() is False and len(text) > 3:
                pass

        # 중복 제거
        seen_ch = set()
        unique_channels = []
        for ch in result["channels"]:
            if ch["id"] not in seen_ch:
                seen_ch.add(ch["id"])
                unique_channels.append(ch)
        result["channels"] = unique_channels

        seen_v = set()
        unique_videos = []
        for v in result["videos"]:
            if v["id"] not in seen_v:
                seen_v.add(v["id"])
                unique_videos.append(v)
        result["videos"] = unique_videos

    except Exception as e:
        print(f"  [PlayBoard 홈 오류] {e}")
    finally:
        driver.quit()

    return result


def crawl_playboard_ranking(rank_type: str = "인기채널_전체", limit: int = 20) -> list[dict]:
    """
    PlayBoard 랭킹 페이지 크롤링
    rank_type: PLAYBOARD_URLS의 키 중 하나
    """
    url = PLAYBOARD_URLS.get(rank_type)
    if not url:
        print(f"  [알 수 없는 rank_type] {rank_type}. 사용 가능: {list(PLAYBOARD_URLS.keys())}")
        return []

    driver = build_driver(headless=True)
    results = []

    try:
        driver.get(url)
        _wait_and_get(driver)

        # 채널/영상 링크 수집
        links = driver.find_elements(By.TAG_NAME, "a")
        rank = 1
        for link in links:
            href = link.get_attribute("href") or ""
            text = link.text.strip()
            if not text or not href:
                continue

            if "/channel/" in href and text and rank <= limit:
                results.append({
                    "rank":    rank,
                    "name":    text,
                    "url":     href,
                    "channel_id": href.split("/channel/")[-1],
                })
                rank += 1
            elif "/video/" in href and text and len(text) > 5 and rank <= limit:
                results.append({
                    "rank":     rank,
                    "title":    text,
                    "url":      href,
                    "video_id": href.split("/video/")[-1].split("?")[0],
                })
                rank += 1

    except Exception as e:
        print(f"  [PlayBoard 랭킹 오류] {e}")
    finally:
        driver.quit()

    return results[:limit]


if __name__ == "__main__":
    import sys
    sys.stdout.reconfigure(encoding="utf-8")  # type: ignore[union-attr]

    print("=" * 60)
    print("  PlayBoard 메인 - 채널 & 영상 수집")
    print("=" * 60)

    data = crawl_playboard_home()

    print(f"\n  [채널 목록] {len(data['channels'])}개")
    for i, ch in enumerate(data["channels"][:15], 1):
        print(f"    {i:>2}. {ch['name']}")
        print(f"        {ch['url']}")

    print(f"\n  [영상 목록] {len(data['videos'])}개")
    for i, v in enumerate(data["videos"][:10], 1):
        print(f"    {i:>2}. {v['title'][:50]}")
        print(f"        https://www.youtube.com/watch?v={v['id']}")
