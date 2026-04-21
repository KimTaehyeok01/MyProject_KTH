# 채널 전체 영상 목록 수집 : @kimsookTV
# 롱폼 / 숏폼 구분, 썸네일 링크·상세 정보 포함

import os
import sys
import re
from dotenv import load_dotenv
import requests

sys.stdout.reconfigure(encoding="utf-8")
load_dotenv()
API_KEY = os.getenv("YOUTUBE_API_KEY")
BASE = "https://www.googleapis.com/youtube/v3"

# ── 1. 채널 업로드 플레이리스트 ID 조회 ─────────────────────────
ch_res = requests.get(
    f"{BASE}/channels",
    params={
        "part": "snippet,contentDetails,statistics",
        "forHandle": "kimsookTV",
        "key": API_KEY,
    },
).json()

ch = ch_res["items"][0]
channel_id = ch["id"]
ch_title = ch["snippet"]["title"]
uploads_id = ch["contentDetails"]["relatedPlaylists"]["uploads"]
total_videos = int(ch["statistics"]["videoCount"])

print(f"채널명   : {ch_title}")
print(f"채널 ID  : {channel_id}")
print(f"총 영상  : {total_videos:,} 개")
print(f"업로드PL : {uploads_id}")
print()

# ── 2. 플레이리스트 아이템 전체 수집 (페이지네이션) ──────────────
video_ids = []
next_page = None
page = 1

while True:
    params = {
        "part": "contentDetails",
        "playlistId": uploads_id,
        "maxResults": 50,
        "key": API_KEY,
    }
    if next_page:
        params["pageToken"] = next_page

    res = requests.get(f"{BASE}/playlistItems", params=params).json()
    ids = [item["contentDetails"]["videoId"] for item in res.get("items", [])]
    video_ids.extend(ids)
    print(f"  페이지 {page:>2} 수집 : {len(ids)}개 (누적 {len(video_ids)}개)")

    next_page = res.get("nextPageToken")
    if not next_page:
        break
    page += 1

print(f"\n총 수집된 영상 ID : {len(video_ids)}개\n")


# ── 3. 영상 상세정보 배치 조회 (50개씩) ─────────────────────────
def parse_duration(iso: str) -> int:
    """ISO 8601 duration → 초 변환 (PT1M30S → 90)"""
    m = re.match(r"PT(?:(\d+)H)?(?:(\d+)M)?(?:(\d+)S)?", iso)
    if not m:
        return 0
    h, mn, s = (int(x or 0) for x in m.groups())
    return h * 3600 + mn * 60 + s


videos = []
for i in range(0, len(video_ids), 50):
    batch = video_ids[i : i + 50]
    res = requests.get(
        f"{BASE}/videos",
        params={
            "part": "snippet,statistics,contentDetails",
            "id": ",".join(batch),
            "key": API_KEY,
        },
    ).json()

    for item in res.get("items", []):
        s = item["snippet"]
        st = item.get("statistics", {})
        dur = parse_duration(item["contentDetails"]["duration"])

        # 썸네일 : maxres > standard > high > medium > default 순서
        thumbs = s.get("thumbnails", {})
        thumb = (
            thumbs.get("maxres")
            or thumbs.get("standard")
            or thumbs.get("high")
            or thumbs.get("medium")
            or thumbs.get("default")
            or {}
        ).get("url", "")

        videos.append(
            {
                "id": item["id"],
                "title": s.get("title", ""),
                "published": s.get("publishedAt", "")[:10],
                "duration_s": dur,
                "type": "Shorts" if dur <= 60 else "Long",
                "views": int(st.get("viewCount", 0)),
                "likes": int(st.get("likeCount", 0)),
                "comments": int(st.get("commentCount", 0)),
                "thumbnail": thumb,
                "url": f"https://www.youtube.com/watch?v={item['id']}",
            }
        )

# 최신순 정렬
videos.sort(key=lambda v: v["published"], reverse=True)

shorts = [v for v in videos if v["type"] == "Shorts"]
longs = [v for v in videos if v["type"] == "Long"]


# ── 4. 콘솔 출력 ────────────────────────────────────────────────
def fmt_dur(sec: int) -> str:
    if sec < 3600:
        return f"{sec//60}:{sec%60:02d}"
    return f"{sec//3600}:{(sec%3600)//60:02d}:{sec%60:02d}"


def print_videos(video_list, label):
    print("=" * 80)
    print(f"  {label}  ({len(video_list)}개)")
    print("=" * 80)
    for i, v in enumerate(video_list, 1):
        print(
            f"  [{i:>3}] {v['published']}  [{fmt_dur(v['duration_s'])}]  {v['title']}"
        )
        print(
            f"         조회수: {v['views']:>10,}  좋아요: {v['likes']:>7,}  댓글: {v['comments']:>6,}"
        )
        print(f"         URL      : {v['url']}")
        print(f"         썸네일   : {v['thumbnail']}")
        print()


print_videos(longs, "롱폼 영상")
print_videos(shorts, "숏폼(Shorts) 영상")

# ── 5. 전체 요약 ─────────────────────────────────────────────────
total_views = sum(v["views"] for v in videos)
top5 = sorted(videos, key=lambda v: v["views"], reverse=True)[:5]

print("=" * 80)
print("  전체 요약")
print("=" * 80)
print(f"  롱폼 영상  : {len(longs):,} 개")
print(f"  숏폼 영상  : {len(shorts):,} 개")
print(f"  합계       : {len(videos):,} 개")
print(f"  총 조회수  : {total_views:,} 회")
print()
print("  [인기 TOP 5]")
for i, v in enumerate(top5, 1):
    print(f"  {i}. ({v['type']:6}) {v['views']:>10,}회  {v['title']}")
print("=" * 80)
