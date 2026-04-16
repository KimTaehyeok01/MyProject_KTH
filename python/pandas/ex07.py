# ex07.py

import pandas as pd
import matplotlib.pyplot as plt

plt.rcParams["font.family"] = "Malgun Gothic"
plt.rcParams["axes.unicode_minus"] = False

df = pd.read_csv("./pandas/books2.csv")

# 저자별 도서 수 barplot
author_counts = df["저자"].value_counts()
author_counts.plot(kind="bar", color="lightgreen")
plt.title("저자별 도서 수")
plt.xlabel("저자")
plt.ylabel("도서 수")
plt.tight_layout()
plt.savefig("author_count.png")
plt.show()

# 중앙값 : 데이터를 크기 순으로 정렬했을 때, 정가운데 위치한 값.
# 평균값 : 모든 데이터의 값을 더해서 개수로 나눈 값.

# 가격 boxplot
plt.figure()
df.boxplot(column="가격")
plt.title("도서 가격 Boxplot")
plt.ylabel("가격")
plt.tight_layout()
plt.savefig("price_boxplot.png")
plt.show()

# 중앙선 (검정선): 도서 가격의 중앙값 (Median).
# 상자(Box): 전체 도서 가격 중에서 Q1 (25%) ~ Q3 (75%) 구간을 의미.
# 상자의 아래 경계 (Q1): 약 19,250원
# 상자의 위 경계 (Q3): 약 26,500원
# 상자 내부의 중앙선: 약 22,500원 → 중앙값
# 수염(Whiskers):
# 아래쪽: 약 17,000원(min)
# 위쪽: 약 35,000원(max)
# 이상치 없음: Boxplot 외부에 점들이 없는 것으로 보아, 특이하게 비싼/싼 책은 없음.
