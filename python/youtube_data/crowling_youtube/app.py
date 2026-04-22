# 핫 유튜브 검색 웹앱 (Flask)
# 실행: python app.py  →  http://127.0.0.1:5000

from pathlib import Path

from dotenv import load_dotenv

load_dotenv(Path(__file__).resolve().parent / ".env")

from flask import Flask, render_template, request

from youtube_hotvideos import find_hot_videos

app = Flask(__name__)
app.config["MAX_CONTENT_LENGTH"] = 16 * 1024


def _format_vs_percent(ratio) -> str:
    """조회÷구독 비율을 '구독자 1명당 조회가 몇 %인지' 형태로 표시 (ratio × 100)."""
    try:
        p = float(ratio) * 100
    except (TypeError, ValueError):
        return "—"
    if p >= 1_000_000:
        s = f"{p / 1_000_000:.2f}".rstrip("0").rstrip(".") + "M"
    elif p >= 10_000:
        s = f"{p / 1_000:.1f}".rstrip("0").rstrip(".") + "k"
    elif p >= 1_000:
        s = f"{int(round(p)):,}"
    elif p >= 100:
        s = str(int(round(p)))
    else:
        s = f"{p:.1f}".rstrip("0").rstrip(".")
    return s + "%"


app.add_template_filter(_format_vs_percent, "vs_percent")

RESULT_LIMIT = 3
SEARCH_POOL = 20


def _format_int(n: int) -> str:
    return f"{n:,}"


def _build_analysis(videos: list[dict]) -> dict:
    """화면에 넣을 분석 요약·설명용 데이터"""
    if not videos:
        return {}

    ratios = [v["ratio"] for v in videos]
    views = [v["views"] for v in videos]

    top = videos[0]
    avg_ratio = sum(ratios) / len(ratios)
    total_views = sum(views)

    title_short = top["title"][:40] + ("…" if len(top["title"]) > 40 else "")
    others = [v["channel"] for v in videos[1:3]]
    others_txt = " · ".join(f"「{n}」" for n in others) if others else ""

    summary = (
        f"가장 두드러지는 유튜버는 「{top['channel']}」입니다. "
        f"대표 영상 「{title_short}」은 구독자 {_format_int(top['subs'])}명 대비 "
        f"조회 {_format_int(top['views'])}회(비율 {top['ratio']:.2f})입니다."
    )
    if others_txt:
        summary += f" 같은 기준으로 상위권에 이름을 올린 크리에이터는 {others_txt}입니다."

    bullets = [
        "먼저 키워드로 영상을 조회수 많은 순으로 가져온 뒤, 채널 구독자 수로 나눈 조회÷구독 비율로 다시 정렬합니다.",
        "비율이 높을수록 구독자 규모 대비 화제가 몰린 영상·크리에이터로 볼 수 있습니다.",
        "화면에서는 유튜버(채널) 이름을 앞에 두고, 그 채널에서 튄 영상을 바로 이어서 보여 줍니다.",
        "상위 3명(3채널)만 가로로 한 줄에 배치해 스크롤을 줄였습니다.",
    ]

    return {
        "summary": summary,
        "bullets": bullets,
        "avg_ratio": round(avg_ratio, 2),
        "max_ratio": round(max(ratios), 2),
        "total_views": total_views,
    }


@app.route("/", methods=["GET"])
def index():
    q = (request.args.get("q") or "").strip()
    error = None
    videos = []
    analysis = None

    if q:
        if len(q) > 80:
            error = "검색어는 80자 이내로 입력해 주세요."
        else:
            try:
                ranked = find_hot_videos(q, max_results=SEARCH_POOL)
                videos = ranked[:RESULT_LIMIT]
                if not videos:
                    error = "검색 결과가 없습니다. 다른 키워드를 시도해 보세요."
                else:
                    analysis = _build_analysis(videos)
            except Exception as e:
                error = f"데이터를 불러오는 중 오류가 발생했습니다: {e}"

    return render_template(
        "index.html",
        q=q,
        videos=videos,
        analysis=analysis,
        error=error,
        format_int=_format_int,
    )


if __name__ == "__main__":
    app.run(host="127.0.0.1", port=5000, debug=True)
