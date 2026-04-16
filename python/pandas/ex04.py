# ex04.py
import pandas as pd
import matplotlib.pyplot as plt

# 파이썬 시각화 라이브러리 : 매트롯립 matplotlib
# pip install matplotlib

# 한글 폰트 설정
plt.rcParams["font.family"] = "Malgun Gothic"
plt.rcParams["axes.unicode_minus"] = False  # 음수 부호 하이픈(-)으로

df = pd.read_csv("./pandas/books.csv")
print(df.info())

grouped = df.groupby("카테고리")["가격"].mean()

# 카테고리별 평균 가격을 시각화
grouped.plot(kind="bar")  # 막대 차트
plt.title("카테고리별 평균 가격")
plt.xlabel("카테고리")
plt.ylabel("가격")
plt.tight_layout()  # 타이트하게 여백을 맞춤

# 시각화 뷰를 이미지로 저장(show()함수 이전에!)
plt.savefig("mean_report.png")

plt.show()

df.to_html("mean_report.html")
