# YouTube Data API - 키워드별 핫 영상 탐색
# 핵심 지표: 구독자 대비 조회수 비율 (views / subscribers)

import os
import time
import requests
from dotenv import load_dotenv

load_dotenv()
API_KEY = os.getenv("YOUTUBE_API_KEY")
BASE    = "https://www.googleapis.com/youtube/v3"


def search_videos(keyword: str, max_results: int = 20) -> list[dict]:
    """키워드로 유튜브 영상 검색 → video_id 목록 반환"""
    res = requests.get(f"{BASE}/search", params={
        "part":       "snippet",
        "q":          keyword,
        "type":       "video",
        "order":      "viewCount",
        "maxResults": max_results,
        "regionCode": "KR",
        "relevanceLanguage": "ko",
        "key":        API_KEY,
    }).json()

    return [
        {
            "video_id":   item["id"]["videoId"],
            "title":      item["snippet"]["title"],
            "channel_id": item["snippet"]["channelId"],
            "channel":    item["snippet"]["channelTitle"],
            "published":  item["snippet"]["publishedAt"][:10],
            "thumbnail":  (
                item["snippet"]["thumbnails"].get("high") or
                item["snippet"]["thumbnails"].get("default", {})
            ).get("url", ""),
        }
        for item in res.get("items", [])
    ]


def get_video_stats(video_ids: list[str]) -> dict[str, dict]:
    """영상 ID 목록 → 통계(조회수·좋아요·댓글·길이) 반환"""
    stats = {}
    for i in range(0, len(video_ids), 50):
        batch = video_ids[i:i+50]
        res = requests.get(f"{BASE}/videos", params={
            "part": "statistics,contentDetails",
            "id":   ",".join(batch),
            "key":  API_KEY,
        }).json()
        for item in res.get("items", []):
            stats[item["id"]] = {
                "views":    int(item["statistics"].get("viewCount",    0)),
                "likes":    int(item["statistics"].get("likeCount",    0)),
                "comments": int(item["statistics"].get("commentCount", 0)),
                "duration": item["contentDetails"].get("duration", ""),
            }
    return stats


def get_channel_subs(channel_ids: list[str]) -> dict[str, int]:
    """채널 ID 목록 → 구독자 수 반환"""
    subs = {}
    unique = list(set(channel_ids))
    for i in range(0, len(unique), 50):
        batch = unique[i:i+50]
        res = requests.get(f"{BASE}/channels", params={
            "part": "statistics",
            "id":   ",".join(batch),
            "key":  API_KEY,
        }).json()
        for item in res.get("items", []):
            subs[item["id"]] = int(
                item["statistics"].get("subscriberCount", 1)
            )
    return subs


def find_hot_videos(keyword: str, max_results: int = 20) -> list[dict]:
    """
    키워드로 영상 검색 후 구독자 대비 조회수 비율로 정렬한 핫 영상 반환
    ratio = views / subscribers  (높을수록 채널 규모 대비 폭발적 반응)
    """
    videos = search_videos(keyword, max_results)
    if not videos:
        return []

    video_ids   = [v["video_id"]   for v in videos]
    channel_ids = [v["channel_id"] for v in videos]

    stats   = get_video_stats(video_ids)
    subs    = get_channel_subs(channel_ids)
    time.sleep(0.2)  # API 쿼터 여유

    result = []
    for v in videos:
        vid = v["video_id"]
        cid = v["channel_id"]
        s   = stats.get(vid, {})
        sub = subs.get(cid, 1)
        views = s.get("views", 0)
        ratio = round(views / sub, 4) if sub > 0 else 0

        result.append({
            **v,
            "views":    views,
            "likes":    s.get("likes", 0),
            "comments": s.get("comments", 0),
            "subs":     sub,
            "ratio":    ratio,
            "url":      f"https://www.youtube.com/watch?v={vid}",
        })

    result.sort(key=lambda x: x["ratio"], reverse=True)
    return result


if __name__ == "__main__":
    import sys
    sys.stdout.reconfigure(encoding="utf-8")  # type: ignore[union-attr]

    keyword = "먹방"
    print(f"\n키워드 [{keyword}] 핫 영상 분석\n")
    videos = find_hot_videos(keyword, max_results=10)
    for i, v in enumerate(videos, 1):
        print(f"  [{i:>2}] ratio={v['ratio']:>6.2f}  조회={v['views']:>8,}  구독={v['subs']:>8,}")
        print(f"        {v['title']}")
        print(f"        {v['url']}")
        print()
