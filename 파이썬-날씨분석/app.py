from flask import Flask, render_template, jsonify
import requests
from datetime import datetime, timedelta
from concurrent.futures import ThreadPoolExecutor

app = Flask(__name__)

# ───────────────────────────────────────────
# Open-Meteo API로 실제 날씨 데이터 가져오기
# 기본 위치: 서울 (위도 37.5665, 경도 126.9780)
# ───────────────────────────────────────────
def get_weather_data(lat=37.5665, lon=126.9780):
    url = "https://api.open-meteo.com/v1/forecast"
    params = {
        "latitude": lat,
        "longitude": lon,
        "daily": [
            "temperature_2m_max",
            "temperature_2m_min",
            "precipitation_sum",
            "windspeed_10m_max",
            "relative_humidity_2m_max",
        ],
        "timezone": "Asia/Seoul",
        "past_days": 7,
        "forecast_days": 1,  # 오늘 포함
    }

    resp = requests.get(url, params=params, timeout=10)
    resp.raise_for_status()
    raw = resp.json()["daily"]

    # 오늘 이전 7일치만 사용 (forecast 제외)
    today_str = datetime.now().strftime("%Y-%m-%d")
    indices = [i for i, d in enumerate(raw["time"]) if d <= today_str]

    dates      = [raw["time"][i][5:]          for i in indices]   # MM-DD
    high_temp  = [round(raw["temperature_2m_max"][i] or 0, 1)     for i in indices]
    low_temp   = [round(raw["temperature_2m_min"][i] or 0, 1)     for i in indices]
    avg_temp   = [round((h + l) / 2, 1)       for h, l in zip(high_temp, low_temp)]
    rainfall   = [round(raw["precipitation_sum"][i] or 0, 1)      for i in indices]
    humidity   = [round(raw["relative_humidity_2m_max"][i] or 0, 1) for i in indices]
    wind_speed = [round(raw["windspeed_10m_max"][i] or 0, 1)      for i in indices]

    n = len(dates)
    stats = {
        "avg_high":   round(sum(high_temp) / n, 1),
        "avg_low":    round(sum(low_temp) / n, 1),
        "avg_temp":   round(sum(avg_temp) / n, 1),
        "total_rain": round(sum(rainfall), 1),
        "max_temp":   max(high_temp),
        "min_temp":   min(low_temp),
        "period_start": dates[0],
        "period_end":   dates[-1],
    }

    return {
        "dates": dates,
        "high_temp": high_temp,
        "low_temp": low_temp,
        "avg_temp": avg_temp,
        "rainfall": rainfall,
        "humidity": humidity,
        "wind_speed": wind_speed,
        "stats": stats,
    }


# 전국 주요 도시
CITIES = [
    {"name": "서울",  "lat": 37.5665, "lon": 126.9780},
    {"name": "인천",  "lat": 37.4563, "lon": 126.7052},
    {"name": "강릉",  "lat": 37.7519, "lon": 128.8761},
    {"name": "대전",  "lat": 36.3504, "lon": 127.3845},
    {"name": "청주",  "lat": 36.6424, "lon": 127.4890},
    {"name": "전주",  "lat": 35.8242, "lon": 127.1480},
    {"name": "광주",  "lat": 35.1595, "lon": 126.8526},
    {"name": "대구",  "lat": 35.8714, "lon": 128.6014},
    {"name": "부산",  "lat": 35.1796, "lon": 129.0756},
    {"name": "제주",  "lat": 33.4996, "lon": 126.5312},
]

def fetch_city_today(city):
    params = {
        "latitude": city["lat"],
        "longitude": city["lon"],
        "daily": ["temperature_2m_max", "temperature_2m_min",
                  "precipitation_sum", "weathercode"],
        "timezone": "Asia/Seoul",
        "forecast_days": 1,
    }
    resp = requests.get("https://api.open-meteo.com/v1/forecast",
                        params=params, timeout=10)
    resp.raise_for_status()
    d = resp.json()["daily"]
    return {
        "name": city["name"],
        "high": round(d["temperature_2m_max"][0] or 0, 1),
        "low":  round(d["temperature_2m_min"][0] or 0, 1),
        "rain": round(d["precipitation_sum"][0] or 0, 1),
        "code": d["weathercode"][0],
    }


@app.route("/")
def index():
    return render_template("index.html")


@app.route("/api/weather")
def api_weather():
    response = jsonify(get_weather_data())
    response.headers.add("Access-Control-Allow-Origin", "http://127.0.0.1:5500")
    return response


@app.route("/api/cities")
def api_cities():
    with ThreadPoolExecutor(max_workers=10) as ex:
        results = list(ex.map(fetch_city_today, CITIES))
    response = jsonify(results)
    response.headers.add("Access-Control-Allow-Origin", "http://127.0.0.1:5500")
    return response


if __name__ == "__main__":
    print("서버 시작: http://127.0.0.1:5000")
    app.run(debug=True)
