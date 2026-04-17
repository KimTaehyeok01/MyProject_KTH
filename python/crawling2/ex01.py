# 리액트로 만들어진 동적인 웹 사이트 크롤링하기
# 셀레니움 Lib(webDriver)를 이용한다.
# webdriver : 마치 사람처럼 브라우저를 조작할 수 있다.
#           : 자덩 예메같은 매크로 작성이 가능하다.
# 단, 네이버, 구글, 쿠팡 같은 보안 강화된 사이트는 자주 막힐 수 있다.
# 네이버 계정으로 자동로그인(막힘), 캡차(그림맞추기, 글자맞추기)

# pip install selenium

from selenium import webdriver
import time

# 크롬 드라이버 경로 지정
driver = webdriver.Chrome()
driver.get("https://www.google.com")

print("브라우저가 열렸습니다.")
time.sleep(5)
driver.quit()
