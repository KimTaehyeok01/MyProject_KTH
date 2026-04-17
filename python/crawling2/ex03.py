# 네이버 뉴스 헤드라인 5개 크롤링 by 셀레니엄

import sys

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

URL = "https://news.naver.com/section/105"
HEADLINE_SELECTOR = "ul[id^='_SECTION_HEADLINE_LIST'] div.sa_text > a"
FALLBACK_SELECTOR = "div.sa_text > a[href*='/article/']"

if hasattr(sys.stdout, "reconfigure"):
    sys.stdout.reconfigure(encoding="utf-8")

driver = webdriver.Chrome()

try:
    driver.get(URL)
    wait = WebDriverWait(driver, 10)

    wait.until(
        EC.presence_of_all_elements_located((By.CSS_SELECTOR, HEADLINE_SELECTOR))
    )
    headlines = driver.find_elements(By.CSS_SELECTOR, HEADLINE_SELECTOR)

    if len(headlines) < 5:
        headlines = driver.find_elements(By.CSS_SELECTOR, FALLBACK_SELECTOR)

    titles = []
    links = []

    for headline in headlines[:5]:
        titles.append(headline.text.strip())
        links.append(headline.get_attribute("href"))

    print("[헤드라인 타이틀 5개]")
    for idx, title in enumerate(titles, start=1):
        print(f"{idx}. {title}")

    print("\n[헤드라인 링크 5개]")
    for idx, link in enumerate(links, start=1):
        print(f"{idx}. {link}")

finally:
    driver.quit()


# ============================================================
# CSS 셀렉터 문법 정리
# ============================================================
#
# [기본 셀렉터]
#   div              → 해당 태그 전체
#   .sa_text         → class="sa_text" 인 요소
#   #header          → id="header" 인 요소
#   *                → 모든 요소
#
# [조합 셀렉터]
#   div a            → div 안의 모든 a (자손, 깊이 무관)
#   div > a          → div 바로 아래 직계 자식 a 만
#   div + p          → div 바로 다음 형제 p
#   div ~ p          → div 이후 모든 형제 p
#
# [속성 셀렉터]
#   [href]           → href 속성이 존재하는 요소
#   [href="url"]     → href 가 정확히 "url" 인 요소
#   [id^="prefix"]   → id 가 "prefix" 로 시작하는 요소  (^ : 시작 일치)
#   [id$="suffix"]   → id 가 "suffix" 로 끝나는 요소   ($ : 끝 일치)
#   [id*="text"]     → id 에 "text" 가 포함된 요소      (* : 부분 일치)
#
# [의사 클래스 (Pseudo-class)]
#   li:nth-child(1)  → 첫 번째 li
#   li:nth-child(odd)→ 홀수 번째 li
#   li:first-child   → 첫 번째 자식
#   li:last-child    → 마지막 자식
#
# [다중 선택]
#   h1, h2, h3       → 쉼표로 여러 셀렉터 동시 선택
#
# [이 파일에서 사용한 셀렉터 읽는 법]
#   ul[id^='_SECTION_HEADLINE_LIST'] div.sa_text > a
#   └ ① ul 중 id 가 '_SECTION_HEADLINE_LIST' 로 시작하는 것
#     ② 그 자손인 class="sa_text" 의 div
#     ③ 그 div 의 직계 자식 <a> 태그
# ============================================================
