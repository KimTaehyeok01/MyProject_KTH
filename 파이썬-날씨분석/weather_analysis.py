import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm
import numpy as np
from datetime import datetime, timedelta

# 한글 폰트 설정 (Windows)
plt.rcParams['font.family'] = 'Malgun Gothic'
plt.rcParams['axes.unicode_minus'] = False

# ──────────────────────────────────────────
# 1. 샘플 날씨 데이터 생성 (30일치)
# ──────────────────────────────────────────
np.random.seed(42)
days = 30
start_date = datetime(2026, 2, 5)
dates = [start_date + timedelta(days=i) for i in range(days)]

data = {
    "날짜": dates,
    "최고기온(°C)": np.round(np.random.uniform(0, 15, days) + np.sin(np.linspace(0, np.pi, days)) * 5, 1),
    "최저기온(°C)": np.round(np.random.uniform(-10, 5, days) + np.sin(np.linspace(0, np.pi, days)) * 3, 1),
    "강수량(mm)":   np.round(np.random.exponential(2, days), 1),
    "습도(%)":      np.round(np.random.uniform(40, 90, days), 1),
    "풍속(m/s)":    np.round(np.random.uniform(1, 10, days), 1),
}

df = pd.DataFrame(data)
df["평균기온(°C)"] = ((df["최고기온(°C)"] + df["최저기온(°C)"]) / 2).round(1)
df["날짜_str"] = df["날짜"].dt.strftime("%m/%d")

# ──────────────────────────────────────────
# 2. 기초 통계
# ──────────────────────────────────────────
print("=" * 50)
print("       날씨 기초 통계 요약 (최근 30일)")
print("=" * 50)
stats = df[["최고기온(°C)", "최저기온(°C)", "평균기온(°C)", "강수량(mm)", "습도(%)", "풍속(m/s)"]].describe().round(2)
print(stats.to_string())

print("\n[특이사항]")
hot_day  = df.loc[df["최고기온(°C)"].idxmax(), "날짜_str"]
cold_day = df.loc[df["최저기온(°C)"].idxmin(), "날짜_str"]
rain_day = df.loc[df["강수량(mm)"].idxmax(), "날짜_str"]
print(f"  • 최고 기온일: {hot_day}  ({df['최고기온(°C)'].max()}°C)")
print(f"  • 최저 기온일: {cold_day}  ({df['최저기온(°C)'].min()}°C)")
print(f"  • 최다 강수일: {rain_day}  ({df['강수량(mm)'].max()}mm)")
print(f"  • 총 강수량  : {df['강수량(mm)'].sum():.1f}mm")
print(f"  • 강수 발생일: {(df['강수량(mm)'] > 0.1).sum()}일")

# ──────────────────────────────────────────
# 3. 시각화 (2×2 대시보드)
# ──────────────────────────────────────────
fig, axes = plt.subplots(2, 2, figsize=(14, 9))
fig.suptitle("최근 30일 날씨 분석 대시보드", fontsize=16, fontweight="bold", y=1.01)

x = df["날짜_str"]
tick_idx = list(range(0, days, 5))   # 5일 간격 눈금

# ① 기온 추이
ax1 = axes[0, 0]
ax1.fill_between(range(days), df["최저기온(°C)"], df["최고기온(°C)"], alpha=0.2, color="tomato", label="기온 범위")
ax1.plot(range(days), df["평균기온(°C)"], color="tomato", marker="o", markersize=3, label="평균기온")
ax1.set_title("일별 기온 추이")
ax1.set_ylabel("기온 (°C)")
ax1.set_xticks(tick_idx)
ax1.set_xticklabels([x[i] for i in tick_idx], rotation=45)
ax1.legend(fontsize=8)
ax1.grid(True, linestyle="--", alpha=0.5)

# ② 강수량 막대
ax2 = axes[0, 1]
colors = ["steelblue" if v > 5 else "skyblue" for v in df["강수량(mm)"]]
ax2.bar(range(days), df["강수량(mm)"], color=colors)
ax2.set_title("일별 강수량")
ax2.set_ylabel("강수량 (mm)")
ax2.set_xticks(tick_idx)
ax2.set_xticklabels([x[i] for i in tick_idx], rotation=45)
ax2.grid(True, axis="y", linestyle="--", alpha=0.5)

# ③ 습도 & 풍속 (이중축)
ax3 = axes[1, 0]
ax3b = ax3.twinx()
ax3.plot(range(days), df["습도(%)"], color="green", marker="s", markersize=3, label="습도")
ax3b.plot(range(days), df["풍속(m/s)"], color="orange", marker="^", markersize=3, label="풍속", linestyle="--")
ax3.set_title("습도 & 풍속 추이")
ax3.set_ylabel("습도 (%)", color="green")
ax3b.set_ylabel("풍속 (m/s)", color="orange")
ax3.set_xticks(tick_idx)
ax3.set_xticklabels([x[i] for i in tick_idx], rotation=45)
lines1, labels1 = ax3.get_legend_handles_labels()
lines2, labels2 = ax3b.get_legend_handles_labels()
ax3.legend(lines1 + lines2, labels1 + labels2, fontsize=8)
ax3.grid(True, linestyle="--", alpha=0.5)

# ④ 강수량 vs 습도 산점도
ax4 = axes[1, 1]
sc = ax4.scatter(df["습도(%)"], df["강수량(mm)"], c=df["평균기온(°C)"], cmap="RdYlBu_r",
                 s=60, edgecolors="gray", linewidths=0.5)
plt.colorbar(sc, ax=ax4, label="평균기온 (°C)")
# 추세선
z = np.polyfit(df["습도(%)"], df["강수량(mm)"], 1)
p = np.poly1d(z)
hx = np.linspace(df["습도(%)"].min(), df["습도(%)"].max(), 100)
ax4.plot(hx, p(hx), "r--", linewidth=1.2, label="추세선")
corr = df["습도(%)"].corr(df["강수량(mm)"])
ax4.set_title(f"습도 vs 강수량  (상관계수 r={corr:.2f})")
ax4.set_xlabel("습도 (%)")
ax4.set_ylabel("강수량 (mm)")
ax4.legend(fontsize=8)
ax4.grid(True, linestyle="--", alpha=0.5)

plt.tight_layout()
plt.savefig(r"c:\Users\taehy\Desktop\파이썬\weather_dashboard.png", dpi=150, bbox_inches="tight")
print("\n그래프 저장 완료 → weather_dashboard.png")
plt.show()
