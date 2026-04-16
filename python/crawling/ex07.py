import requests
from bs4 import BeautifulSoup
from urllib.parse import urlparse, parse_qs

url = "https://finance.naver.com/"
headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36",
}
response = requests.get(url, headers=headers)  # 크롬 브라우저처럼 요청
soup = BeautifulSoup(response.text, "html.parser")

# kospi_point
css_selector_kospi_point = "#content > div.article > div.section2 > div.section_stock_market > div.section_stock > div.kospi_area.group_quot.quot_opn > div.heading_area > a > span > span.num"
kospi_point_tag = soup.select(css_selector_kospi_point)
print(kospi_point_tag)  # [<span class="num">6,147.36</span>]
print(kospi_point_tag[0].text)  # 6,145.13

# 등락율
css_selector_kospi_rate_point = "#content > div.article > div.section2 > div.section_stock_market > div.section_stock > div.kospi_area.group_quot.quot_opn > div.heading_area > a > span > span.num3"
kospi_rate_point_tag = soup.select(css_selector_kospi_rate_point)
print(kospi_rate_point_tag)
print(kospi_rate_point_tag[0].text)  # +3.09%

# 미국 주식 정보 요청
url = "https://finance.naver.com/world/"
headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36",
}
response = requests.get(url, headers=headers)  # 크롬 브라우저처럼 요청
soup = BeautifulSoup(response.text, "html.parser")

# S&P 500 : 미국 주식거래소의 우량한 500개를 모아논 것
css_selector_snp500_point = "#worldIndexColumn3 > li.on > dl > dd.point_status > strong"
snp500_point_tag = soup.select(css_selector_snp500_point)
print(snp500_point_tag)
print(snp500_point_tag[0].text)  # 1,569.19

css_selector_snp500_rate = (
    "#worldIndexColumn2 > li.on > dl > dd.point_status > span:nth-child(3)"
)
snp500_rate_tag = soup.select(css_selector_snp500_rate)
print(snp500_rate_tag)
print(snp500_rate_tag[0].text)  # +0.34%

# 네이버 금융 주요뉴스 가져오기
url = "https://finance.naver.com/news/mainnews.naver"
headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36",
}
response = requests.get(url, headers=headers)  # 크롬 브라우저처럼 요청
soup = BeautifulSoup(response.text, "html.parser")

for i in range(1, 6):
    css_selector_news_tag = f"#contentarea_left > div.mainNewsList._replaceNewsLink > ul > li:nth-child({i}) > dl > dd.articleSubject > a"
    news_tag = soup.select(css_selector_news_tag)
    print(news_tag)
    print(news_tag[0].text)  # 뉴스기사 타이틀
    print(
        news_tag[0]["href"]
    )  # 뉴스기사 링크 : /news/news_read.naver?article_id=0005275198&office_id=015&mode=mainnews&type=&date=2026-04-15&page=1
    #  https://n.news.naver.com/mnews/article/015/0005275198

    news_href = news_tag[0]["href"]

    params = parse_qs(urlparse(news_href).query)
    article_id = params["article_id"][0]
    office_id = params["office_id"][0]
    print(article_id)  # 0005275198
    print(office_id)  # 015

    # 최종 뉴스 기사 링크
    news_link = f"https://n.news.naver.com/mnews/article/{office_id}/{article_id}"
    print(news_link)
