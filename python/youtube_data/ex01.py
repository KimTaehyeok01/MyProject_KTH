# .env : API KEY
# 구글 트렌드(실시간 인기검색어)
# 인기 채널 분석(플레이보드, 민인터 사이트)

import os
from dotenv import load_dotenv
import requests

load_dotenv()
API_KEY = os.getenv("YOUTUBE_API_KEY")

url = f"https://www.googleapis.com/youtube/v3/videos?part=snippet&chart=mostPopular&maxResults=1&key={API_KEY}"
response = requests.get(url)

if response.status_code == 200:
    print("[OK] API KEY 활성화 확인! 상태코드:", response.status_code)
else:
    print("[ERROR] API KEY 오류! 상태코드:", response.status_code)
    print(response.json())
