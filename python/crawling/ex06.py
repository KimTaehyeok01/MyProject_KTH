# 크롤링한 결과 CSV파일(로컬파일 Sqlite)에 저장하기
from msilib import datasizemask
import requests
from bs4 import BeautifulSoup
import csv
import json
import datetime


def fetch_articles(url="https://news.ycombinator.com/"):
    response = requests.get(url)
    soup = BeautifulSoup(response.text, "html.parser")
    articles = soup.select(".titleline > a")

    data = []
    for a in articles:
        title = a.text.strip()  # 좌우공백 제거
        link = a["href"].strip()
        data.append({"title": title, "link": link})  # dict 타입으로 추가

    return data


def save_to_csv(data, filename):
    with open(filename, "w", newline="", encoding="utf-8") as f:
        # dict 형식의 데이터를 csv파일로 쓰기한다.
        writer = csv.DictWriter(f, fieldnames=["title", "link"])
        writer.writeheader()
        writer.writerows(data)


def save_to_json(data, filename):
    with open(filename, "w", encoding="utf-8") as f:
        # ensure_ascii=True 이면 이스케이프 유니코드 형태로 저장됨.
        # \ub0a8 \uc131 이런 형식으로 한글이 써진다.
        json.dump(data, f, ensure_ascii=False, indent=2)


def main():
    print("해커 뉴스 기사 크롤링 중...")
    data = fetch_articles()
    print(data)

    # 현재시간
    today = datetime.datetime.now().strftime("%Y-%m-%d")
    print(today)  # 2026-04-14

    csv_file = f"hacker_news_{today}.csv"
    json_file = f"hacker_news_{today}.json"

    save_to_csv(data, csv_file)  # 데이터, 파일명
    save_to_json(data, json_file)

    print("CSV 저장 완료")
    print("JSON 저장 완료")
    print(f"총 {len(data)}개의 기사를 수집했습니다.")


# 현재 파일에서 실행되었는지?
if __name__ == "__main__":
    main()
