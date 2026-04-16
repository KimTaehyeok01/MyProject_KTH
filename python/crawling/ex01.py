# request 라이브러리 : http 통신 라이브러리
# pip install requests
# py -m pip install requests
import requests

url = "https://www.naver.com"

response = requests.get(url)

print(response.text)
