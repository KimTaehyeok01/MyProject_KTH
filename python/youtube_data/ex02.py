# 채널 분석 : @kimsookTV
# YouTube Data API v3 - channels 엔드포인트

import os
import sys
from dotenv import load_dotenv
import requests

sys.stdout.reconfigure(encoding="utf-8")

load_dotenv()
API_KEY = os.getenv("YOUTUBE_API_KEY")

CHANNEL_HANDLE = "kimsookTV"

url = "https://www.googleapis.com/youtube/v3/channels"
params = {
    "part": "snippet,statistics,contentDetails,brandingSettings,topicDetails,status",
    "forHandle": CHANNEL_HANDLE,
    "key": API_KEY,
}

response = requests.get(url, params=params)
data = response.json()

if response.status_code != 200 or not data.get("items"):
    print("[ERROR]", data)
else:
    ch = data["items"][0]
    snippet = ch.get("snippet", {})
    stats = ch.get("statistics", {})
    branding = ch.get("brandingSettings", {}).get("channel", {})
    topics = ch.get("topicDetails", {}).get("topicCategories", [])
    status = ch.get("status", {})

    print("=" * 60)
    print("  채널 기본 정보")
    print("=" * 60)
    print(f"  채널명       : {snippet.get('title')}")
    print(f"  채널 ID      : {ch.get('id')}")
    print(f"  핸들         : @{CHANNEL_HANDLE}")
    print(f"  개설일       : {snippet.get('publishedAt', '')[:10]}")
    print(f"  국가         : {snippet.get('country', '미설정')}")
    print(f"  설명         :\n{snippet.get('description', '').strip()}")

    print()
    print("=" * 60)
    print("  채널 통계")
    print("=" * 60)
    print(f"  구독자 수    : {int(stats.get('subscriberCount', 0)):,} 명")
    print(f"  총 영상 수   : {int(stats.get('videoCount', 0)):,} 개")
    print(f"  총 조회수    : {int(stats.get('viewCount', 0)):,} 회")
    print(f"  구독자 공개  : {stats.get('hiddenSubscriberCount', False)}")

    print()
    print("=" * 60)
    print("  브랜딩 설정")
    print("=" * 60)
    print(f"  키워드       : {branding.get('keywords', '없음')}")
    print(f"  채널 설명    : {branding.get('description', '없음')}")

    print()
    print("=" * 60)
    print("  토픽 카테고리")
    print("=" * 60)
    if topics:
        for t in topics:
            print(f"  - {t.split('/')[-1].replace('_', ' ')}")
    else:
        print("  없음")

    print()
    print("=" * 60)
    print("  계정 상태")
    print("=" * 60)
    print(f"  개인정보 상태 : {status.get('privacyStatus')}")
    print(f"  연결된 계정   : {status.get('isLinked')}")
    print(f"  장기 회원     : {status.get('longUploadsStatus')}")
    print("=" * 60)

    # ── 채널 분석 디스크립션 ──────────────────────────────────
    from datetime import date

    subs       = int(stats.get("subscriberCount", 0))
    videos     = int(stats.get("videoCount", 0))
    views      = int(stats.get("viewCount", 0))
    created    = snippet.get("publishedAt", "")[:10]
    channel_name = snippet.get("title", "")

    avg_views  = views // videos if videos else 0
    start_year = int(created[:4]) if created else 2020
    years_active = date.today().year - start_year or 1
    avg_videos_per_year = videos // years_active

    if subs >= 1_000_000:
        tier = f"{subs / 1_000_000:.1f}M (골드 버튼 달성)"
    elif subs >= 100_000:
        tier = f"{subs / 1_000:.0f}K (실버 버튼 달성)"
    else:
        tier = f"{subs:,} (성장 중)"

    topic_names = [t.split("/")[-1].replace("_", " ") for t in topics]
    topic_str   = ", ".join(topic_names) if topic_names else "미분류"

    print()
    print("=" * 60)
    print("  채널 분석 요약 (Description)")
    print("=" * 60)
    print(f"""
  [{channel_name}] 채널은 {created} 에 개설된 대한민국 유튜브 채널로,
  현재 구독자 {subs:,}명({tier})을 보유하고 있습니다.

  총 {videos:,}개의 영상을 업로드하여 누적 조회수 {views:,}회를
  기록했으며, 영상 1개당 평균 조회수는 약 {avg_views:,}회입니다.

  채널 개설 후 약 {years_active}년간 운영되었으며,
  연평균 약 {avg_videos_per_year}개의 영상을 꾸준히 업로드하고 있습니다.

  주요 콘텐츠 카테고리는 [{topic_str}]이며,
  키워드: {branding.get('keywords', '없음')}
    """.rstrip())
    print("=" * 60)
