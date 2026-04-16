# ex02.py
import pandas as pd

df = pd.read_csv("./pandas/books.csv")
print(df.info())

# 첫 5행을 읽어옴 (수만행의 데이터를 한번 보고 싶진?)
print(df.head())
print()

# 결측치 확인 및 제거 (데이터 정제 작업)
print(df.isnull().sum())
"""
제목      0
카테고리    1
가격      1
dtype: int64
"""
df = df.dropna()  # 결측치 있는 행 제거
print(df)
print(df.info())  #  9 entries

print()
# 중복된 행 제거
df = df.drop_duplicates()
print(df)

# 컬럼 타입 변환
df["가격"] = df["가격"].astype(int)  # float64 => int64
print(df.info())
