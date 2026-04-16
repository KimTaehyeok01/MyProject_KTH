# ex02.py

import requests

# 라이브서버 test1.html 주소
url = "http://127.0.0.1:5500/crawling/test1.html"

html: str = requests.get(url).text
print(html)

# h1 태그 내용 추출
start = html.find("<h1>") + 4
end = html.find("</h1>")
h1_content = html[start:end]

print(h1_content)
