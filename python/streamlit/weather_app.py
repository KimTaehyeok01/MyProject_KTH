"""
전국 날씨 비교 앱
기상청 단기예보 조회서비스 (VilageFcstInfoService_2.0) 사용
"""

import streamlit as st
import requests
import pandas as pd
import plotly.express as px
import plotly.graph_objects as go
import json
import os
from datetime import datetime, timedelta

# ── 설정 ─────────────────────────────────────────────────────────────
API_KEY = "6VIvLrD5g/6jgPZLjvZp3cyBuD12SAHbfrc3ZvNs7tlmTPUPN/cUD0GsYFjmh8cdWifVA/g9Uj5ig9Zuoy028w=="
BASE_URL = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"
MID_TA_URL = "https://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa"
MID_LAND_URL = "https://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst"

CITIES = {
    "서울": {
        "nx": 60,
        "ny": 127,
        "region": "서울특별시",
        "mid_ta_id": "11B10101",
        "mid_land_id": "11B00000",
    },
    "인천": {
        "nx": 55,
        "ny": 124,
        "region": "인천광역시",
        "mid_ta_id": "11B20201",
        "mid_land_id": "11B00000",
    },
    "수원": {
        "nx": 60,
        "ny": 121,
        "region": "경기도",
        "mid_ta_id": "11B20601",
        "mid_land_id": "11B00000",
    },
    "춘천": {
        "nx": 73,
        "ny": 134,
        "region": "강원특별자치도",
        "mid_ta_id": "11D10301",
        "mid_land_id": "11D10000",
    },
    "강릉": {
        "nx": 92,
        "ny": 131,
        "region": "강원특별자치도",
        "mid_ta_id": "11D20501",
        "mid_land_id": "11D20000",
    },
    "대전": {
        "nx": 67,
        "ny": 100,
        "region": "대전광역시",
        "mid_ta_id": "11C20401",
        "mid_land_id": "11C20000",
    },
    "세종": {
        "nx": 66,
        "ny": 103,
        "region": "세종특별자치시",
        "mid_ta_id": "11C20404",
        "mid_land_id": "11C20000",
    },
    "청주": {
        "nx": 69,
        "ny": 106,
        "region": "충청북도",
        "mid_ta_id": "11C10301",
        "mid_land_id": "11C10000",
    },
    "천안": {
        "nx": 63,
        "ny": 110,
        "region": "충청남도",
        "mid_ta_id": "11C20301",
        "mid_land_id": "11C20000",
    },
    "전주": {
        "nx": 63,
        "ny": 89,
        "region": "전북특별자치시",
        "mid_ta_id": "11F10201",
        "mid_land_id": "11F10000",
    },
    "광주": {
        "nx": 58,
        "ny": 74,
        "region": "광주광역시",
        "mid_ta_id": "11F20501",
        "mid_land_id": "11F20000",
    },
    "목포": {
        "nx": 50,
        "ny": 67,
        "region": "전라남도",
        "mid_ta_id": "21F20801",
        "mid_land_id": "11F20000",
    },
    "대구": {
        "nx": 89,
        "ny": 90,
        "region": "대구광역시",
        "mid_ta_id": "11H10701",
        "mid_land_id": "11H10000",
    },
    "안동": {
        "nx": 91,
        "ny": 106,
        "region": "경상북도",
        "mid_ta_id": "11H10501",
        "mid_land_id": "11H10000",
    },
    "부산": {
        "nx": 98,
        "ny": 76,
        "region": "부산광역시",
        "mid_ta_id": "11H20201",
        "mid_land_id": "11H20000",
    },
    "울산": {
        "nx": 102,
        "ny": 84,
        "region": "울산광역시",
        "mid_ta_id": "11H20101",
        "mid_land_id": "11H20000",
    },
    "창원": {
        "nx": 90,
        "ny": 77,
        "region": "경상남도",
        "mid_ta_id": "11H20301",
        "mid_land_id": "11H20000",
    },
    "제주": {
        "nx": 52,
        "ny": 38,
        "region": "제주특별자치도",
        "mid_ta_id": "11G00201",
        "mid_land_id": "11G00000",
    },
}

BASE_TIMES = ["0200", "0500", "0800", "1100", "1400", "1700", "2000", "2300"]

# ── 전체 지역 데이터 로드 (regions.json) ─────────────────────────────
_REGIONS_PATH = os.path.join(os.path.dirname(__file__), "regions.json")


@st.cache_data
def load_all_regions():
    """3800+ 전국 지역 데이터 로드"""
    with open(_REGIONS_PATH, encoding="utf-8") as f:
        return json.load(f)


def search_regions(query: str, limit: int = 30):
    """
    query 포함 지역 검색 → (nx, ny) 중복 제거 후 최대 limit개 반환
    반환: [(표시명, nx, ny, lv1), ...]
    """
    all_regions = load_all_regions()
    seen_coords = set()
    results = []
    for name, info in all_regions.items():
        if query in name:
            key = (info["nx"], info["ny"])
            if key not in seen_coords:
                seen_coords.add(key)
                results.append((name, info["nx"], info["ny"], info["lv1"]))
            if len(results) >= limit:
                break
    return results


# ── 유틸 함수 ────────────────────────────────────────────────────────
def get_base_date_time(now: datetime):
    available_minutes = [(int(t[:2]) * 60 + int(t[2:])) + 10 for t in BASE_TIMES]
    now_minutes = now.hour * 60 + now.minute
    for i in range(len(available_minutes) - 1, -1, -1):
        if now_minutes >= available_minutes[i]:
            return now.strftime("%Y%m%d"), BASE_TIMES[i]
    yesterday = now - timedelta(days=1)
    return yesterday.strftime("%Y%m%d"), "2300"


def get_mid_base_time(now: datetime):
    """중기예보 발표시각 (06시/18시)"""
    if now.hour < 6:
        yesterday = now - timedelta(days=1)
        return yesterday.strftime("%Y%m%d") + "1800"
    elif now.hour < 18:
        return now.strftime("%Y%m%d") + "0600"
    else:
        return now.strftime("%Y%m%d") + "1800"


def sky_label(val):
    return {"1": "맑음", "3": "구름많음", "4": "흐림"}.get(str(val), val)


def pty_label(val):
    return {"0": "없음", "1": "비", "2": "비/눈", "3": "눈", "4": "소나기"}.get(
        str(val), val
    )


def weather_icon(sky, pty):
    """하늘상태 + 강수형태 → 날씨 이모지"""
    if pty == "1":
        return "🌧️"
    if pty == "2":
        return "🌨️"
    if pty == "3":
        return "❄️"
    if pty == "4":
        return "⛈️"
    return {"1": "☀️", "3": "⛅", "4": "☁️"}.get(str(sky), "🌡️")


def wf_icon(wf_text):
    """중기예보 날씨 텍스트 → 이모지"""
    if not wf_text:
        return "🌡️"
    wf = str(wf_text)
    if "비/눈" in wf:
        return "🌨️"
    if "눈" in wf:
        return "❄️"
    if "소나기" in wf:
        return "⛈️"
    if "비" in wf:
        return "🌧️"
    if "흐" in wf:
        return "☁️"
    if "구름" in wf:
        return "⛅"
    if "맑" in wf:
        return "☀️"
    return "🌡️"


def card_gradient(sky, pty, tmp):
    """날씨 상태에 따른 카드 배경 그라데이션"""
    if pty in ("1", "4"):  # 비·소나기
        return "linear-gradient(150deg, #4e8de8 0%, #2563c4 100%)"
    if pty == "2":  # 비/눈
        return "linear-gradient(150deg, #74c9e8 0%, #3a9abf 100%)"
    if pty == "3":  # 눈
        return "linear-gradient(150deg, #a5d8f3 0%, #5baed4 100%)"
    if sky == "1":  # 맑음
        t = float(tmp)
        if t >= 28:
            return "linear-gradient(150deg, #ff7043 0%, #e53935 100%)"
        if t >= 20:
            return "linear-gradient(150deg, #ffb300 0%, #f57c00 100%)"
        if t >= 10:
            return "linear-gradient(150deg, #29b6f6 0%, #0277bd 100%)"
        return "linear-gradient(150deg, #64b5f6 0%, #1565c0 100%)"
    if sky == "3":  # 구름많음 → 인디고
        return "linear-gradient(150deg, #7986cb 0%, #3f51b5 100%)"
    return "linear-gradient(150deg, #7c93b8 0%, #4e6a9a 100%)"  # 흐림 → 스틸블루


@st.cache_data(ttl=600)
def fetch_weather(nx: int, ny: int, base_date: str, base_time: str):
    params = {
        "serviceKey": API_KEY,
        "pageNo": "1",
        "numOfRows": "1000",
        "dataType": "JSON",
        "base_date": base_date,
        "base_time": base_time,
        "nx": str(nx),
        "ny": str(ny),
    }
    try:
        res = requests.get(BASE_URL, params=params, timeout=10)
        res.raise_for_status()
        items = res.json()["response"]["body"]["items"]["item"]
        df = pd.DataFrame(items)
        first_time = df["fcstTime"].min()
        first_date = df[df["fcstTime"] == first_time]["fcstDate"].min()
        sub = df[(df["fcstDate"] == first_date) & (df["fcstTime"] == first_time)]
        result = {row["category"]: row["fcstValue"] for _, row in sub.iterrows()}
        result["_fcstTime"] = first_time
        return result, None
    except Exception as e:
        return None, str(e)


@st.cache_data(ttl=600)
def fetch_daily_forecast(nx: int, ny: int, base_date: str, base_time: str):
    params = {
        "serviceKey": API_KEY,
        "pageNo": "1",
        "numOfRows": "1000",
        "dataType": "JSON",
        "base_date": base_date,
        "base_time": base_time,
        "nx": str(nx),
        "ny": str(ny),
    }
    try:
        res = requests.get(BASE_URL, params=params, timeout=10)
        res.raise_for_status()
        items = res.json()["response"]["body"]["items"]["item"]
        df = pd.DataFrame(items)
        df["datetime"] = pd.to_datetime(
            df["fcstDate"] + df["fcstTime"], format="%Y%m%d%H%M"
        )
        df["fcstValue"] = pd.to_numeric(df["fcstValue"], errors="coerce")
        return df, None
    except Exception as e:
        return None, str(e)


@st.cache_data(ttl=3600)
def fetch_mid_ta(reg_id: str, tm_fc: str):
    """중기기온 조회 (3~10일 후 최저/최고기온)"""
    params = {
        "serviceKey": API_KEY,
        "pageNo": "1",
        "numOfRows": "10",
        "dataType": "JSON",
        "regId": reg_id,
        "tmFc": tm_fc,
    }
    try:
        res = requests.get(MID_TA_URL, params=params, timeout=10)
        res.raise_for_status()
        items = res.json()["response"]["body"]["items"]["item"]
        return items[0], None
    except Exception as e:
        return None, str(e)


@st.cache_data(ttl=3600)
def fetch_mid_land_fcst(reg_id: str, tm_fc: str):
    """중기육상예보 조회 (3~10일 후 날씨/강수확률)"""
    params = {
        "serviceKey": API_KEY,
        "pageNo": "1",
        "numOfRows": "10",
        "dataType": "JSON",
        "regId": reg_id,
        "tmFc": tm_fc,
    }
    try:
        res = requests.get(MID_LAND_URL, params=params, timeout=10)
        res.raise_for_status()
        items = res.json()["response"]["body"]["items"]["item"]
        return items[0], None
    except Exception as e:
        return None, str(e)


def extract_daily_summary(df):
    """단기예보 DataFrame → 일별 요약 리스트"""
    if df is None or df.empty:
        return []
    daily = []
    for date_val, grp in df.groupby(df["datetime"].dt.date):
        cat_data = {}
        for cat in ["TMP", "TMN", "TMX", "POP", "SKY", "PTY"]:
            vals = grp[grp["category"] == cat]["fcstValue"].dropna()
            if not vals.empty:
                cat_data[cat] = vals
        tmn = cat_data["TMN"].min() if "TMN" in cat_data else None
        tmx = cat_data["TMX"].max() if "TMX" in cat_data else None
        if tmn is None and "TMP" in cat_data:
            tmn = cat_data["TMP"].min()
        if tmx is None and "TMP" in cat_data:
            tmx = cat_data["TMP"].max()
        pop = int(cat_data["POP"].max()) if "POP" in cat_data else 0
        sky_s = cat_data["SKY"].mode()
        sky = int(sky_s.iloc[0]) if "SKY" in cat_data and not sky_s.empty else 1
        pty = int(cat_data["PTY"].max()) if "PTY" in cat_data else 0
        daily.append(
            {
                "date": date_val,
                "tmn": float(tmn) if pd.notna(tmn) else None,
                "tmx": float(tmx) if pd.notna(tmx) else None,
                "pop": pop,
                "sky": str(sky),
                "pty": str(pty) if pty > 0 else "0",
            }
        )
    return sorted(daily, key=lambda x: x["date"])


def build_weekly_forecast(short_daily, mid_ta, mid_land, mid_base):
    """단기(1~3일) + 중기(4~7일) 합쳐서 7일 예보 생성"""
    today = datetime.now().date()
    mid_base_date = datetime.strptime(mid_base[:8], "%Y%m%d").date()
    DAY_NAMES = ["월", "화", "수", "목", "금", "토", "일"]
    short_map = {d["date"]: d for d in short_daily}
    weekly = []
    for i in range(7):
        target = today + timedelta(days=i)
        day_name = DAY_NAMES[target.weekday()]
        if target in short_map:
            d = short_map[target]
            weekly.append(
                {
                    "date": target,
                    "day_name": day_name,
                    "tmn": d["tmn"],
                    "tmx": d["tmx"],
                    "pop": d["pop"],
                    "icon": weather_icon(d["sky"], d["pty"]),
                    "weather": (
                        sky_label(d["sky"]) if d["pty"] == "0" else pty_label(d["pty"])
                    ),
                }
            )
        elif mid_ta and mid_land:
            offset = (target - mid_base_date).days
            if 3 <= offset <= 10:
                tmn = mid_ta.get(f"taMin{offset}")
                tmx = mid_ta.get(f"taMax{offset}")
                if offset <= 7:
                    wf = (
                        mid_land.get(f"wf{offset}Pm")
                        or mid_land.get(f"wf{offset}Am")
                        or mid_land.get(f"wf{offset}", "맑음")
                    )
                    pa = (
                        mid_land.get(f"rnSt{offset}Am")
                        or mid_land.get(f"rnSt{offset}")
                        or 0
                    )
                    pp = (
                        mid_land.get(f"rnSt{offset}Pm")
                        or mid_land.get(f"rnSt{offset}")
                        or 0
                    )
                else:
                    wf = mid_land.get(f"wf{offset}", "맑음")
                    pa = pp = mid_land.get(f"rnSt{offset}") or 0
                weekly.append(
                    {
                        "date": target,
                        "day_name": day_name,
                        "tmn": tmn,
                        "tmx": tmx,
                        "pop": max(int(pa), int(pp)),
                        "icon": wf_icon(str(wf)),
                        "weather": str(wf),
                    }
                )
    return weekly


def render_weather_card(city, data, region_label=None):
    """날씨 카드 HTML 생성 (CSS 그리드용 고정 크기)
    city         : 카드 제목 (도시명 또는 상세 지역명)
    region_label : 카드 상단 작은 글씨 (시/도명). None이면 CITIES 참조
    """
    tmp = data.get("TMP", "-")
    tmn = data.get("TMN", "-")
    tmx = data.get("TMX", "-")
    reh = data.get("REH", "-")
    pop = data.get("POP", "-")
    wsd = data.get("WSD", "-")
    sky = data.get("SKY", "1")
    pty = data.get("PTY", "0")
    icon = weather_icon(sky, pty)
    grad = card_gradient(sky, pty, tmp if tmp != "-" else "15")
    sky_txt = sky_label(sky) if pty == "0" else pty_label(pty)
    region = region_label or CITIES.get(city, {}).get("region", "")
    tmn_str = f"{tmn}°" if tmn not in ("-", None) else "-"
    tmx_str = f"{tmx}°" if tmx not in ("-", None) else "-"

    return f"""
    <div style="
        background:{grad};
        border-radius:20px;
        padding:18px 18px 15px 18px;
        color:white;
        box-shadow:0 8px 24px rgba(0,0,0,0.14);
        width:100%;
        box-sizing:border-box;
        position:relative;
        overflow:hidden;
    ">
      <!-- 오른쪽 상단 반투명 원 장식 -->
      <div style="
        position:absolute;top:-20px;right:-20px;
        width:90px;height:90px;
        border-radius:50%;
        background:rgba(255,255,255,0.08);
        pointer-events:none;
      "></div>
      <div style="font-size:0.63rem;color:rgba(255,255,255,0.65);letter-spacing:0.05em;margin-bottom:2px;">{region}</div>
      <div style="font-size:1.1rem;font-weight:700;margin-bottom:10px;">{city}</div>
      <div style="display:flex;align-items:center;gap:10px;margin-bottom:5px;">
        <span style="font-size:2.2rem;line-height:1;filter:drop-shadow(0 2px 4px rgba(0,0,0,0.2));">{icon}</span>
        <span style="font-size:2.1rem;font-weight:800;line-height:1;letter-spacing:-0.02em;">
          {tmp}<span style="font-size:1rem;font-weight:400;margin-left:1px;">°C</span>
        </span>
      </div>
      <div style="font-size:0.73rem;color:rgba(255,255,255,0.78);margin-bottom:12px;">{sky_txt}</div>
      <div style="border-top:1px solid rgba(255,255,255,0.2);margin-bottom:11px;"></div>
      <div style="display:grid;grid-template-columns:1fr 1fr;gap:5px 4px;font-size:0.7rem;color:rgba(255,255,255,0.88);line-height:1.7;">
        <div>💧 습도 <b style="color:white;">{reh}%</b></div>
        <div>🌂 강수 <b style="color:white;">{pop}%</b></div>
        <div>💨 풍속 <b style="color:white;">{wsd}m/s</b></div>
        <div>🌡 <b style="color:white;">{tmn_str} / {tmx_str}</b></div>
      </div>
    </div>"""


def render_card_grid(items, weather_data):
    """
    카드 전체를 CSS 그리드로 렌더링 (중앙 정렬, 고정 크기)
    items : [(표시명, nx, ny, lv1), ...] 또는 [도시명, ...]
    weather_data : {key: API응답 dict}  key = (nx,ny) 튜플 or 도시명 str
    """
    cards = ""
    for item in items:
        if isinstance(item, tuple):
            name, nx, ny, lv1 = item
            key = (nx, ny)
            if key in weather_data:
                cards += render_weather_card(name, weather_data[key], region_label=lv1)
        else:
            if item in weather_data:
                cards += render_weather_card(item, weather_data[item])
    return f"""
    <div style="
        display:grid;
        grid-template-columns:repeat(auto-fill, 215px);
        gap:16px;
        justify-content:center;
        padding:8px 0 20px 0;
    ">
      {cards}
    </div>"""


def render_weekly_forecast(weekly):
    """7일 예보 가로 카드 UI"""
    if not weekly:
        return "<div class='no-result'>예보 데이터가 없습니다.</div>"
    today = datetime.now().date()
    cards = ""
    for day in weekly:
        tmn_s = f"{day['tmn']:.0f}°" if day["tmn"] is not None else "-"
        tmx_s = f"{day['tmx']:.0f}°" if day["tmx"] is not None else "-"
        date_s = day["date"].strftime("%m/%d")
        is_today = day["date"] == today
        label = (
            "오늘"
            if is_today
            else (
                "내일" if day["date"] == today + timedelta(days=1) else day["day_name"]
            )
        )
        border = (
            "3px solid rgba(255,255,255,0.6)"
            if is_today
            else "1px solid rgba(255,255,255,0.15)"
        )
        w = day["weather"]
        if "비" in w and "눈" not in w and "소나기" not in w:
            grad = "linear-gradient(160deg,#4e8de8 0%,#667eea 100%)"
        elif "눈" in w:
            grad = "linear-gradient(160deg,#a5d8f3 0%,#5baed4 100%)"
        elif "소나기" in w:
            grad = "linear-gradient(160deg,#5b7fb8 0%,#4568a0 100%)"
        elif "흐" in w:
            grad = "linear-gradient(160deg,#7c93b8 0%,#4e6a9a 100%)"
        elif "구름" in w:
            grad = "linear-gradient(160deg,#7986cb 0%,#5c6bc0 100%)"
        else:
            t = day["tmx"] or 15
            if t >= 28:
                grad = "linear-gradient(160deg,#ff7043 0%,#e53935 100%)"
            elif t >= 20:
                grad = "linear-gradient(160deg,#ffb300 0%,#f57c00 100%)"
            else:
                grad = "linear-gradient(160deg,#29b6f6 0%,#0288d1 100%)"
        cards += f"""
        <div style="
            min-width:100px;flex:1;
            background:{grad};border-radius:16px;
            padding:14px 8px;text-align:center;color:white;
            box-shadow:0 4px 16px rgba(0,0,0,0.12);
            border:{border};
        ">
            <div style="font-size:0.82rem;font-weight:700;">{label}</div>
            <div style="font-size:0.68rem;opacity:0.7;">{date_s}</div>
            <div style="font-size:1.9rem;margin:6px 0;
                filter:drop-shadow(0 2px 4px rgba(0,0,0,0.2));">{day['icon']}</div>
            <div style="font-size:0.7rem;opacity:0.85;margin-bottom:6px;
                white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">{w}</div>
            <div style="font-size:0.9rem;font-weight:700;">{tmn_s} / {tmx_s}</div>
            <div style="font-size:0.65rem;opacity:0.7;margin-top:3px;">🌂 {day['pop']}%</div>
        </div>"""
    return f"""
    <div style="
        display:flex;gap:8px;
        overflow-x:auto;padding:8px 0 14px 0;
        -webkit-overflow-scrolling:touch;
    ">{cards}</div>"""


# ── CSS 전역 스타일 ──────────────────────────────────────────────────
GLOBAL_CSS = """
<style>
[data-testid="stAppViewContainer"] {
    background: linear-gradient(160deg, #e8f0fe 0%, #f0f7ff 50%, #e6f0fb 100%);
    min-height: 100vh;
}
[data-testid="stHeader"] { background: transparent; }
[data-testid="stSidebar"] { background: #f0f6ff; border-right: 1px solid #dce8f8; }

/* 검색창 스타일 */
div[data-testid="stTextInput"] input {
    border-radius: 10px !important;
    border: 1.5px solid #cbd5e1 !important;
    background: #fff !important;
    font-size: 0.95rem !important;
    padding: 10px 14px !important;
    box-shadow: 0 1px 4px rgba(0,0,0,0.06) !important;
    transition: border-color 0.2s;
}
div[data-testid="stTextInput"] input:focus {
    border-color: #3b82f6 !important;
    box-shadow: 0 0 0 3px rgba(59,130,246,0.12) !important;
}

.section-title {
    font-size: 1.1rem;
    font-weight: 700;
    color: #1e3a5f;
    margin: 20px 0 10px 0;
}
.info-chip {
    display: inline-block;
    background: #dbeafe;
    color: #1d4ed8;
    border-radius: 999px;
    padding: 2px 10px;
    font-size: 0.78rem;
    font-weight: 600;
    margin-right: 6px;
}
.no-result {
    text-align: center;
    padding: 48px 20px;
    color: #94a3b8;
    font-size: 1rem;
    background: white;
    border-radius: 14px;
}
/* 버튼 미세 조정 */
div[data-testid="stButton"] button {
    border-radius: 9px !important;
}
</style>
"""


# ── 앱 시작 ───────────────────────────────────────────────────────────
st.set_page_config(page_title="전국 날씨 비교", page_icon="🌤️", layout="wide")
st.markdown(GLOBAL_CSS, unsafe_allow_html=True)

now = datetime.now()
base_date, base_time = get_base_date_time(now)

# ── 사이드바 ─────────────────────────────────────────────────────────
with st.sidebar:
    st.markdown("## ⚙️ 설정")
    st.info(
        f"📅 **기준 발표**\n"
        f"{base_date[:4]}-{base_date[4:6]}-{base_date[6:]}  "
        f"{base_time[:2]}:{base_time[2:]}"
    )
    st.divider()

    st.markdown("### 🏙️ 도시 선택")
    all_cities = list(CITIES.keys())
    DEFAULT_CITIES = [
        "서울",
        "인천",
        "수원",
        "부산",
        "대구",
        "광주",
        "대전",
        "울산",
        "제주",
    ]
    select_all = st.checkbox("전체 도시", value=False)
    if select_all:
        selected_cities = all_cities
    else:
        selected_cities = st.multiselect(
            "도시 선택",
            all_cities,
            default=DEFAULT_CITIES,
        )

    st.divider()
    st.markdown("### 📈 상세 예보 도시")
    detail_city = st.selectbox("시간별 예보 도시", all_cities, index=0)

    st.divider()
    if st.button("🔄 새로고침", use_container_width=True, type="primary"):
        st.cache_data.clear()
        st.rerun()

    st.markdown(
        f"<div style='font-size:0.75rem;color:#94a3b8;margin-top:12px;'>"
        f"마지막 갱신<br>{now.strftime('%Y-%m-%d %H:%M:%S')}</div>",
        unsafe_allow_html=True,
    )

# ── 헤더 ─────────────────────────────────────────────────────────────
st.markdown(
    "<h1 style='margin-bottom:2px; font-size:1.9rem;'>🌤️ 전국 날씨 비교</h1>"
    "<p style='color:#94a3b8; font-size:0.85rem; margin-bottom:16px;'>"
    "기상청 단기예보 · 발표 후 10분 이내 자동 반영</p>",
    unsafe_allow_html=True,
)

# ── 검색 바 ──────────────────────────────────────────────────────────
col_search, col_clear = st.columns([4, 1])
with col_search:
    search_query = st.text_input(
        "지역 검색",
        placeholder="🔍  읍·면·동까지 검색 가능  (예: 서울, 송파구, 해운대동...)",
        label_visibility="collapsed",
    )
with col_clear:
    if st.button("✕ 초기화", use_container_width=True):
        search_query = ""

# ── 검색 모드 vs 기본 모드 분기 ──────────────────────────────────────
search_mode = bool(search_query.strip())

if search_mode:
    # 전체 3833개 지역에서 검색
    search_results = search_regions(search_query.strip(), limit=30)
    if not search_results:
        st.markdown(
            "<div class='no-result'>😕 검색 결과가 없습니다.<br>"
            "<small>시/도·시/군/구·읍/면/동 이름으로 검색해보세요.</small></div>",
            unsafe_allow_html=True,
        )
        st.stop()

    st.markdown(
        f"<div style='margin:6px 0 4px 2px;'>"
        f"<span class='info-chip'>📍 {len(search_results)}개 지역</span>"
        f"<span style='color:#94a3b8;font-size:0.82rem;'>"
        f"'{search_query}' 검색 결과 (최대 30개 · 격자 중복 제거)</span></div>",
        unsafe_allow_html=True,
    )

    with st.spinner("📡 날씨 데이터 불러오는 중..."):
        weather_data = {}
        for name, nx, ny, lv1 in search_results:
            key = (nx, ny)
            if key not in weather_data:
                data, err = fetch_weather(nx, ny, base_date, base_time)
                if not err:
                    weather_data[key] = data

    if not weather_data:
        st.error("날씨 데이터를 불러올 수 없습니다.")
        st.stop()

    st.markdown("<div class='section-title'>📍 검색 결과</div>", unsafe_allow_html=True)
    st.markdown(render_card_grid(search_results, weather_data), unsafe_allow_html=True)
    st.divider()

    # 검색 결과 비교 테이블
    rows_s = []
    for name, nx, ny, lv1 in search_results:
        d = weather_data.get((nx, ny), {})
        if not d:
            continue
        tmp = d.get("TMP", "-")
        sky = d.get("SKY", "-")
        pty = d.get("PTY", "0")
        rows_s.append(
            {
                "지역명": name,
                "시/도": lv1,
                "기온(°C)": float(tmp) if tmp != "-" else None,
                "습도(%)": int(d.get("REH", 0)),
                "강수확률(%)": int(d.get("POP", 0)),
                "하늘": weather_icon(sky, pty)
                + " "
                + (sky_label(sky) if pty == "0" else pty_label(pty)),
                "풍속(m/s)": float(d.get("WSD", 0)),
            }
        )
    if rows_s:
        st.markdown(
            "<div class='section-title'>📋 검색 결과 비교</div>", unsafe_allow_html=True
        )
        df_s = pd.DataFrame(rows_s)

        def _color(v):
            if pd.isna(v):
                return ""
            if v >= 30:
                return "background-color:#fee2e2;color:#991b1b;"
            if v >= 25:
                return "background-color:#fef3c7;color:#92400e;"
            if v >= 15:
                return "background-color:#dcfce7;color:#166534;"
            if v >= 5:
                return "background-color:#dbeafe;color:#1e40af;"
            return "background-color:#ede9fe;color:#5b21b6;"

        st.dataframe(
            df_s.style.map(_color, subset=["기온(°C)"]),
            use_container_width=True,
            hide_index=True,
        )
    st.divider()
    st.markdown(
        f"<div style='text-align:center;color:#94a3b8;font-size:0.8rem;'>"
        f"데이터 출처: 기상청 단기예보 조회서비스 | 갱신: {now.strftime('%Y-%m-%d %H:%M:%S')}</div>",
        unsafe_allow_html=True,
    )
    st.stop()

# ── 기본 모드: 선택된 도시 표시 ─────────────────────────────────────
if not selected_cities:
    st.warning("사이드바에서 도시를 선택해주세요.")
    st.stop()

with st.spinner("📡 날씨 데이터 불러오는 중..."):
    weather_data = {}
    errors = []
    for city in selected_cities:
        info = CITIES[city]
        data, err = fetch_weather(info["nx"], info["ny"], base_date, base_time)
        if err:
            errors.append(f"{city}: {err}")
        else:
            weather_data[city] = data

if errors:
    with st.expander("⚠️ 일부 도시 오류"):
        for e in errors:
            st.error(e)

if not weather_data:
    st.error("날씨 데이터를 불러올 수 없습니다.")
    st.stop()

# ── 날씨 카드 그리드 ─────────────────────────────────────────────────
st.markdown(
    "<div class='section-title'>🗺️ 도시별 현재 날씨</div>", unsafe_allow_html=True
)
st.markdown(
    render_card_grid(list(weather_data.keys()), weather_data), unsafe_allow_html=True
)

st.divider()

# ── 비교 데이터프레임 ─────────────────────────────────────────────────
rows_data = []
for city, d in weather_data.items():
    tmp = d.get("TMP", "-")
    tmn = d.get("TMN", "-")
    tmx = d.get("TMX", "-")
    reh = d.get("REH", "-")
    pop = d.get("POP", "-")
    sky = d.get("SKY", "-")
    pty = d.get("PTY", "0")
    wsd = d.get("WSD", "-")
    pcp = d.get("PCP", "강수없음")
    rows_data.append(
        {
            "도시": city,
            "지역": CITIES.get(city, {}).get("region", ""),
            "기온(°C)": float(tmp) if tmp != "-" else None,
            "최저(°C)": tmn,
            "최고(°C)": tmx,
            "습도(%)": int(reh) if reh not in ("-", None) else None,
            "강수확률(%)": int(pop) if pop not in ("-", None) else None,
            "하늘": weather_icon(sky, pty)
            + " "
            + (sky_label(sky) if pty == "0" else pty_label(pty)),
            "풍속(m/s)": float(wsd) if wsd not in ("-", None) else None,
            "1h강수": pcp,
        }
    )

df_main = pd.DataFrame(rows_data)

# ── 비교 테이블 ───────────────────────────────────────────────────────
st.markdown(
    "<div class='section-title'>📋 전체 비교 테이블</div>", unsafe_allow_html=True
)


# 기온으로 배경 색조 적용
def color_tmp(val):
    if pd.isna(val):
        return ""
    if val >= 30:
        return "background-color:#fee2e2; color:#991b1b;"
    if val >= 25:
        return "background-color:#fef3c7; color:#92400e;"
    if val >= 15:
        return "background-color:#dcfce7; color:#166534;"
    if val >= 5:
        return "background-color:#dbeafe; color:#1e40af;"
    return "background-color:#ede9fe; color:#5b21b6;"


styled = df_main.style.map(color_tmp, subset=["기온(°C)"])
st.dataframe(styled, use_container_width=True, hide_index=True)

st.divider()

# ── 비교 차트 ─────────────────────────────────────────────────────────
st.markdown(
    "<div class='section-title'>📊 도시별 비교 차트</div>", unsafe_allow_html=True
)

tab1, tab2, tab3 = st.tabs(["🌡️ 기온 비교", "💧 습도 & 강수확률", "💨 풍속 비교"])

with tab1:
    fig = px.bar(
        df_main,
        x="도시",
        y="기온(°C)",
        color="기온(°C)",
        color_continuous_scale="RdYlBu_r",
        text="기온(°C)",
        title="도시별 현재 기온 (°C)",
        template="plotly_white",
    )
    fig.update_traces(
        texttemplate="%{text}°C", textposition="outside", marker_line_width=0
    )
    fig.update_layout(coloraxis_showscale=False, height=420, margin=dict(t=50, b=20))
    st.plotly_chart(fig, use_container_width=True)

with tab2:
    fig2 = go.Figure()
    fig2.add_trace(
        go.Bar(
            name="습도(%)",
            x=df_main["도시"],
            y=df_main["습도(%)"],
            marker_color="#3b82f6",
            text=df_main["습도(%)"],
            texttemplate="%{text}%",
            textposition="outside",
        )
    )
    fig2.add_trace(
        go.Bar(
            name="강수확률(%)",
            x=df_main["도시"],
            y=df_main["강수확률(%)"],
            marker_color="#06b6d4",
            text=df_main["강수확률(%)"],
            texttemplate="%{text}%",
            textposition="outside",
        )
    )
    fig2.update_layout(
        barmode="group",
        title="도시별 습도 & 강수확률",
        height=420,
        template="plotly_white",
        margin=dict(t=50, b=20),
    )
    st.plotly_chart(fig2, use_container_width=True)

with tab3:
    fig3 = px.bar(
        df_main,
        x="도시",
        y="풍속(m/s)",
        color="풍속(m/s)",
        color_continuous_scale="Blues",
        text="풍속(m/s)",
        title="도시별 풍속 (m/s)",
        template="plotly_white",
    )
    fig3.update_traces(
        texttemplate="%{text}m/s", textposition="outside", marker_line_width=0
    )
    fig3.update_layout(coloraxis_showscale=False, height=420, margin=dict(t=50, b=20))
    st.plotly_chart(fig3, use_container_width=True)

st.divider()

# ── 상세 예보 데이터 로드 ─────────────────────────────────────────────
with st.spinner(f"{detail_city} 예보 데이터 로드 중..."):
    city_info = CITIES[detail_city]
    df_detail, detail_err = fetch_daily_forecast(
        city_info["nx"], city_info["ny"], base_date, base_time
    )
    mid_base = get_mid_base_time(now)
    mid_ta, mid_ta_err = fetch_mid_ta(city_info["mid_ta_id"], mid_base)
    mid_land, mid_land_err = fetch_mid_land_fcst(city_info["mid_land_id"], mid_base)

# ── 7일 예보 ─────────────────────────────────────────────────────────
short_daily = (
    extract_daily_summary(df_detail) if not detail_err and df_detail is not None else []
)
weekly = build_weekly_forecast(short_daily, mid_ta, mid_land, mid_base)

st.markdown(
    f"<div class='section-title'>📅 {detail_city} 7일 예보</div>",
    unsafe_allow_html=True,
)

if mid_ta_err or mid_land_err:
    st.info(
        "💡 중기예보 서비스를 활용 신청하면 7일 예보를 볼 수 있습니다. "
        "[data.go.kr](https://www.data.go.kr)에서 **중기예보 조회서비스** 검색 → 활용 신청 (무료)"
    )

if weekly:
    st.markdown(render_weekly_forecast(weekly), unsafe_allow_html=True)

    if len(weekly) >= 3:
        dates = [f"{d['date'].strftime('%m/%d')}({d['day_name']})" for d in weekly]
        tmn_vals = [d["tmn"] for d in weekly]
        tmx_vals = [d["tmx"] for d in weekly]
        pop_vals = [d["pop"] for d in weekly]
        fig_w = go.Figure()
        fig_w.add_trace(
            go.Scatter(
                x=dates,
                y=tmx_vals,
                name="최고기온",
                mode="lines+markers+text",
                text=[f"{v:.0f}°" if v is not None else "" for v in tmx_vals],
                textposition="top center",
                line=dict(color="#ef4444", width=2.5),
                marker_size=8,
            )
        )
        fig_w.add_trace(
            go.Scatter(
                x=dates,
                y=tmn_vals,
                name="최저기온",
                mode="lines+markers+text",
                text=[f"{v:.0f}°" if v is not None else "" for v in tmn_vals],
                textposition="bottom center",
                line=dict(color="#3b82f6", width=2.5),
                marker_size=8,
                fill="tonexty",
                fillcolor="rgba(59,130,246,0.08)",
            )
        )
        fig_w.add_trace(
            go.Bar(
                x=dates,
                y=pop_vals,
                name="강수확률(%)",
                marker_color="rgba(6,182,212,0.35)",
                yaxis="y2",
            )
        )
        fig_w.update_layout(
            title=f"{detail_city} 주간 기온 추이",
            yaxis=dict(title="기온(°C)"),
            yaxis2=dict(
                title="강수확률(%)", overlaying="y", side="right", range=[0, 100]
            ),
            height=400,
            template="plotly_white",
            legend=dict(orientation="h", y=1.1),
            margin=dict(t=60, b=20),
        )
        st.plotly_chart(fig_w, use_container_width=True)

st.divider()

# ── 시간별 상세 예보 ─────────────────────────────────────────────────
st.markdown(
    f"<div class='section-title'>🕐 {detail_city} 시간별 예보</div>",
    unsafe_allow_html=True,
)

if detail_err:
    st.error(f"상세 예보 오류: {detail_err}")
else:
    available_dates = sorted(df_detail["datetime"].dt.date.unique())
    today = datetime.now().date()
    _day_kr = ["월", "화", "수", "목", "금", "토", "일"]

    def _dlabel(d):
        diff = (d - today).days
        dn = _day_kr[d.weekday()]
        if diff == 0:
            return f"📌 오늘 ({d.strftime('%m/%d')} {dn})"
        if diff == 1:
            return f"내일 ({d.strftime('%m/%d')} {dn})"
        if diff == 2:
            return f"모레 ({d.strftime('%m/%d')} {dn})"
        return f"{d.strftime('%m/%d')} ({dn})"

    date_labels = [_dlabel(d) for d in available_dates]
    all_opt = f"전체 ({len(available_dates)}일)"
    sel_date = st.radio(
        "날짜 선택",
        [all_opt] + date_labels,
        horizontal=True,
        label_visibility="collapsed",
    )
    if sel_date != all_opt:
        chosen = available_dates[date_labels.index(sel_date)]
        df_detail = df_detail[df_detail["datetime"].dt.date == chosen]

    c1, c2 = st.columns(2)
    with c1:
        tmp_df = df_detail[df_detail["category"] == "TMP"].sort_values("datetime")
        fig_t = px.line(
            tmp_df,
            x="datetime",
            y="fcstValue",
            markers=True,
            title=f"{detail_city} 시간별 기온 (°C)",
            labels={"fcstValue": "기온(°C)", "datetime": "시각"},
            template="plotly_white",
        )
        fig_t.update_traces(line_color="#ef4444", line_width=2.5, marker_size=7)
        fig_t.update_layout(height=360, margin=dict(t=50, b=20))
        st.plotly_chart(fig_t, use_container_width=True)

    with c2:
        pop_df = df_detail[df_detail["category"] == "POP"].sort_values("datetime")
        reh_df = df_detail[df_detail["category"] == "REH"].sort_values("datetime")
        fig_pr = go.Figure()
        fig_pr.add_trace(
            go.Bar(
                name="강수확률(%)",
                x=pop_df["datetime"],
                y=pop_df["fcstValue"],
                marker_color="#3b82f6",
                opacity=0.7,
            )
        )
        fig_pr.add_trace(
            go.Scatter(
                name="습도(%)",
                x=reh_df["datetime"],
                y=reh_df["fcstValue"],
                mode="lines+markers",
                yaxis="y2",
                line=dict(color="#10b981", width=2.5),
                marker_size=7,
            )
        )
        fig_pr.update_layout(
            title=f"{detail_city} 강수확률 & 습도",
            yaxis=dict(title="강수확률(%)"),
            yaxis2=dict(title="습도(%)", overlaying="y", side="right", range=[0, 100]),
            height=360,
            template="plotly_white",
            legend=dict(orientation="h", y=1.08),
            margin=dict(t=60, b=20),
        )
        st.plotly_chart(fig_pr, use_container_width=True)

    # 시간표
    pivot_cats = ["TMP", "REH", "POP", "SKY", "WSD", "PTY"]
    pivot_df = df_detail[df_detail["category"].isin(pivot_cats)].copy()
    pivot_df["시각"] = pivot_df["datetime"].dt.strftime("%m/%d %H:%M")
    table = pivot_df.pivot_table(
        index="시각", columns="category", values="fcstValue", aggfunc="first"
    )
    rename_map = {
        "TMP": "기온(°C)",
        "REH": "습도(%)",
        "POP": "강수확률(%)",
        "SKY": "하늘",
        "WSD": "풍속(m/s)",
        "PTY": "강수형태",
    }
    table = table[[c for c in rename_map if c in table.columns]].rename(
        columns=rename_map
    )
    if "하늘" in table.columns:
        table["하늘"] = table["하늘"].apply(
            lambda v: (
                weather_icon(str(int(v)), "0") + " " + sky_label(str(int(v)))
                if pd.notna(v)
                else "-"
            )
        )
    if "강수형태" in table.columns:
        table["강수형태"] = table["강수형태"].apply(
            lambda v: pty_label(str(int(v))) if pd.notna(v) else "-"
        )
    st.dataframe(table.reset_index(), use_container_width=True, hide_index=True)

st.divider()
st.markdown(
    f"<div style='text-align:center; color:#94a3b8; font-size:0.8rem;'>"
    f"데이터 출처: 기상청 단기예보 조회서비스(VilageFcstInfoService_2.0) &nbsp;|&nbsp; "
    f"갱신: {now.strftime('%Y-%m-%d %H:%M:%S')}"
    f"</div>",
    unsafe_allow_html=True,
)
