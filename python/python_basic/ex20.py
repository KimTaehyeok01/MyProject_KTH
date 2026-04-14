# 판다스 pandas 라이브러리 csv파일을 읽고 쓰기
# pandas : 데이터 분석에 특화된 라이브러리
#        : Data Frame 행열 2차원 데이터 처리에 유용함.
#        : 금융 시계열 데이터를 다루기 위해 만든다.

# pandas 라이브러리 설치
# pip install pandas

import pandas as pd  # pd라는 별명으로 사용한다.

# 딕셔너리를 데이터 프레임으로 변환한다.
data = {
    "이름": ["홍길동", "김철수", "이영희"],
    "나이": [30, 25, 23],
    "도시": ["서울", "부산", "인천"],
}

# 데이터 프레임(Data Frame) 객체 생성
df = pd.DataFrame(data)
print(df)

# csv 파일로 저장하기
df.to_csv("people_pandas.csv", index=False, encoding="UTF-8")
print("CSV 파일 저장 완료")

# CSV파일을 읽어서 데이터 프레임으로 가져오기
# UTF-8-SIG : 이모지 지원
df = pd.read_csv("people_pandas.csv", encoding="UTF-8-SIG")
print(df)
print("CSV 파일 읽기 성공!")

# Pandas Data Frame : 2차원 데이터(행열)의 데이터를 다루기 쉽다.
# 변수, 상수, 리스트-튜플, 배열, 맵(dict, js, obj), 집합
