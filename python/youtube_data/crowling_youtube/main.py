# 핫 유튜브 영상 탐색기
# 파이프라인: Gemini 핫 키워드 → YouTube 영상 검색 → 구독자 대비 조회수 비율 분석

import sys
sys.stdout.reconfigure(encoding="utf-8")  # type: ignore[union-attr]

from gemini_trends    import get_hot_keywords
from youtube_hotvideos import find_hot_videos
from selenium_crawler  import crawl_playboard_home

KEYWORD_COUNT    = 5    # Gemini에서 가져올 핫 키워드 수
VIDEOS_PER_KW    = 15   # 키워드당 분석할 영상 수
TOP_N            = 3    # 키워드당 출력할 상위 영상 수


def print_section(title: str):
    print()
    print("=" * 70)
    print(f"  {title}")
    print("=" * 70)


def main():
    print_section("STEP 1 | Gemini + Google Search → 핫 키워드 수집")

    keywords = get_hot_keywords("한국 유튜브 트렌드", count=KEYWORD_COUNT)

    if not keywords:
        print("  [ERROR] 키워드를 가져오지 못했습니다.")
        return

    print(f"\n  수집된 핫 키워드 ({len(keywords)}개):")
    for i, kw in enumerate(keywords, 1):
        print(f"    {i}. {kw}")

    # ── PlayBoard 크롤링 (보조 데이터) ────────────────────────────
    print_section("STEP 2 | Selenium → PlayBoard 채널 & 영상 수집")
    pb_data = crawl_playboard_home()

    channels = pb_data.get("channels", [])
    videos   = pb_data.get("videos", [])

    if channels:
        print(f"\n  [PlayBoard 인기 채널] 총 {len(channels)}개 수집")
        for i, ch in enumerate(channels[:10], 1):
            print(f"    {i:>2}. {ch['name']}")
    else:
        print("  채널 데이터 없음")

    if videos:
        print(f"\n  [PlayBoard 인기 영상] 총 {len(videos)}개 수집")
        for i, v in enumerate(videos[:5], 1):
            title = v['title'][:45] + "..." if len(v['title']) > 45 else v['title']
            print(f"    {i:>2}. {title}")
    else:
        print("  영상 데이터 없음")

    # ── 키워드별 핫 영상 분석 ─────────────────────────────────────
    print_section("STEP 3 | YouTube API → 구독자 대비 조회수 비율 핫 영상 분석")

    all_hot = []

    for kw in keywords:
        print(f"\n  [키워드] {kw}")
        videos = find_hot_videos(kw, max_results=VIDEOS_PER_KW)

        if not videos:
            print("    결과 없음")
            continue

        top = videos[:TOP_N]
        for i, v in enumerate(top, 1):
            print(f"    {i}. ratio={v['ratio']:>7.2f}  "
                  f"조회={v['views']:>9,}  구독={v['subs']:>8,}")
            print(f"       제목 : {v['title']}")
            print(f"       채널 : {v['channel']}")
            print(f"       URL  : {v['url']}")
            print(f"       썸네일: {v['thumbnail']}")

        all_hot.extend(top)

    # ── 최종 종합 TOP 영상 ────────────────────────────────────────
    print_section("STEP 4 | 종합 결과 - 전체 핫 영상 TOP 10")

    all_hot_sorted = sorted(all_hot, key=lambda x: x["ratio"], reverse=True)
    seen = set()
    unique_hot = []
    for v in all_hot_sorted:
        if v["video_id"] not in seen:
            seen.add(v["video_id"])
            unique_hot.append(v)

    print(f"\n  {'순위':<4} {'ratio':>7}  {'조회수':>10}  {'구독자':>9}  제목")
    print("  " + "-" * 68)
    for i, v in enumerate(unique_hot[:10], 1):
        title = v["title"][:35] + "..." if len(v["title"]) > 35 else v["title"]
        print(f"  {i:<4} {v['ratio']:>7.2f}  {v['views']:>10,}  {v['subs']:>9,}  {title}")

    print()
    print("  [상세 정보 - TOP 3]")
    for i, v in enumerate(unique_hot[:3], 1):
        print(f"\n  {'─'*66}")
        print(f"  #{i}  {v['title']}")
        print(f"  채널  : {v['channel']}  (구독자 {v['subs']:,}명)")
        print(f"  조회수 : {v['views']:,}회  |  ratio: {v['ratio']:.2f}")
        print(f"  URL   : {v['url']}")
        print(f"  썸네일 : {v['thumbnail']}")

    print_section("완료")
    print(f"  분석 키워드 : {len(keywords)}개")
    print(f"  수집 영상   : {len(all_hot)}개")
    print(f"  종합 TOP 10 : {min(10, len(unique_hot))}개 선정")


if __name__ == "__main__":
    main()
