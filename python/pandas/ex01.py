# ex01.py
# 판다스 Pandas는 파이썬의 데이터 분석 및 조작을 위한
#       대표적인 오픈소스 라이브러리이다.
# Panel Data(패널 데이터)의 줄임말
#  패널 데이터 : 계량경제학,통계학에서 다루는 다차원 데이터를 의미
# 개발자: 웨스 맥키니 - 2008년 투자자문사에서 일하다가
#         금융데이터(시계열-시간에 따라 축적되는 데이터)를 분석
#         하기 위해서 직접 제작
# 자료 구조
# 1. 1차원 배열 : Series(인덱스 + 값)
# 2. 2차원 배열 : DataFrame(행,열, 엑셀 시트와 유사)
# 주요 기능
# 1. CSV, Excel, JSON, SQL 등 다양한 형태의 데이터 읽기/쓰기
# 2. 결측치(Null,값없음,NaN) 처리, 필터링, 정렬, 그룹화(groupby)
# 3. 데이터 병합(merge, join, concat)
# 4. 통계 요약(describe, mean, sum 등)
# 5. 시계열 데이터 처리

# pip install pandas
# py -m pip install pandas
# type: ignore

import pandas as pd

# type: ignore

# 딕셔너리 => 데이터 프레임
data = {
    "이름": ["홍길동", "김영희", "김철수"],
    "나이": [28, 34, 45],
    "직업": ["개발자", "디자이너", "데이터분석가"],
}

df = pd.DataFrame(data)
print("기본 DataFrame")
print(df)
print(type(df))  # <class 'pandas.DataFrame'>
print()

# 데이터프레임은 2차원 배열
# 행과 열을 인덱싱하여 데이터 가져오기
# iloc (정수 위치 기반 인덱스)
print(df.iloc[0])  # 첫번째 행
print(type(df.iloc[0]))  # <class 'pandas.Series'>
print(df.iloc[1])  # 두번째 행

# 첫번째 행, 첫번째 열 : "홍길동"
print(df.iloc[0, 0])
# 첫번째 행, 두번째 열 : 28
print(df.iloc[0, 1])
# 첫번째 행, 세번째 열 : "개발자"
print(df.iloc[0, 2])

# 범위 연산자(슬라이스 연산자)  : 콜론
# 첫번째 행부터 세번째 행까지
print()
print(df.iloc[0:3])
print(df.iloc[0:2])
# 첫번째 행에서 첫번째 열~두번째 열까지 지정
print()
print(df.iloc[0, 0:2])

# loc (라벨-컬럼이름 기반 인덱싱)
# 인덱스 0인 행
print("=" * 20)
print(df.loc[0])
print(type(df.loc[0]))  # <class 'pandas.Series'>
# 라벨(열)이름을 지정하여
print(df.loc[0, "이름"])
print(df.loc[0, "나이"])
print(df.loc[0, "직업"])
print(df.loc[0, ["이름", "나이"]])

# 라벨(열,컬럼)만 가져오기
print("=" * 10)
print(df["이름"])
print(type(df["이름"]))  # <class 'pandas.Series'>
print(df["나이"])

"""
주요 구조 용어
용어       설명
row(행)    가로 방향 데이터 (ex: DB의 레코드, 한 사람의 정보)
column(열) 세로 방향 데이터 (ex: 이름, 나이, 직업)
index      각 행의 고유 번호 (기본값은 0부터 시작)
dtype      각 열의 데이터 타입
"""
# 데이터프레임의 구조 정보
print(df.shape)  # 3,3 (행 수, 열 수)
# 컬럼 목록
print(df.columns.tolist())  # ['이름', '나이', '직업']
# 인덱스 목록
print(df.index.tolist())  # [0, 1, 2]
# 열별 데이터타입
print(df.dtypes)  # 이름 str 나이 int64 직업 str

# 전체 정보 요약
print("=" * 10)
print(df.info())
