# 공공 오픈 API 서비스 사용해보기
# 공공데이터 포털 사이트를 이용.
# 기상청 단기 예보 조회서비스를 이용한 날씨 예고 웹앱을 만들어보기.

import requests
import os

# END POINT : https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0
# 일반 인증키(decoding) : 6VIvLrD5g/6jgPZLjvZp3cyBuD12SAHbfrc3ZvNs7tlmTPUPN/cUD0GsYFjmh8cdWifVA/g9Uj5ig9Zuoy028w==
API_KEY = "6VIvLrD5g/6jgPZLjvZp3cyBuD12SAHbfrc3ZvNs7tlmTPUPN/cUD0GsYFjmh8cdWifVA/g9Uj5ig9Zuoy028w=="
URL = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"

params = {
    "serviceKey": API_KEY,
    "pageNo": "1",
    "numOfRows": "10",
    "dataType": "JSON",
    "base_date": "20260416",
    "base_time": "1100",
    "nx": "55",
    "ny": "127",
}

print("🌤️ 기상청 API 호출 중...")
response = requests.get(URL, params=params)
print(f"✅ 상태 코드: {response.status_code}")
if response.status_code == 200:
    try:
        print(response.json())
    except Exception:
        print("⚠️ JSON 파싱 실패. 원본 응답:")
        print(response.text[:500])
else:
    print("❌ 요청 실패:")
    print(response.text[:500])
