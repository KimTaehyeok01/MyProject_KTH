# Gemini API + Google Search Grounding → 핫 키워드 수집

import os
import json
import re
from dotenv import load_dotenv
from google import genai
from google.genai import types

load_dotenv()
GEMINI_API_KEY = os.getenv("GEMINI_API_KEY")


# Gemini API 호출 불가 시 사용할 폴백 키워드 (한국 유튜브 최신 트렌드 기반)
FALLBACK_KEYWORDS = [
    "먹방", "브이로그", "여행", "요리", "게임",
    "운동", "패션", "뷰티", "일상", "리뷰",
    "ASMR", "드라마 리뷰", "영화 리뷰", "주식", "부동산",
]


def get_hot_keywords(topic: str = "한국 유튜브", count: int = 10) -> list[str]:
    """
    Gemini로 핫 키워드 수집. 실패 시 폴백 키워드 반환.
    """
    if not GEMINI_API_KEY:
        print("  [WARN] GEMINI_API_KEY 없음 → 폴백 키워드 사용")
        return FALLBACK_KEYWORDS[:count]

    client = genai.Client(api_key=GEMINI_API_KEY)

    prompt = f"""
지금 {topic} 분야에서 가장 핫하고 인기 있는 유튜브 검색 키워드 {count}개를 찾아줘.
최신 트렌드, 화제의 인물, 인기 콘텐츠 기반으로 선정해줘.
반드시 아래 JSON 형식으로만 답해줘. 다른 설명은 하지 마.

{{"keywords": ["키워드1", "키워드2", "키워드3", ...]}}
"""

    # 사용 가능한 모델 순서대로 시도
    models_to_try = [
        "gemini-2.0-flash-lite",
        "gemini-2.0-flash",
        "gemini-2.5-flash",
    ]

    for model_name in models_to_try:
        try:
            response = client.models.generate_content(
                model=model_name,
                contents=prompt,
            )
            raw = response.text.strip()

            json_match = re.search(r'\{.*?\}', raw, re.DOTALL)
            if json_match:
                result = json.loads(json_match.group())
                keywords = result.get("keywords", [])
                if keywords:
                    print(f"  [Gemini] 모델 사용: {model_name}")
                    return keywords[:count]

            # JSON 파싱 실패 시 줄 단위 추출
            lines = [l.strip().lstrip("-•*0123456789. ").strip('"') for l in raw.splitlines()]
            keywords = [l for l in lines if l and not l.startswith("{") and not l.startswith("}")]
            if keywords:
                print(f"  [Gemini] 모델 사용: {model_name}")
                return keywords[:count]

        except Exception as e:
            err = str(e)
            if "RESOURCE_EXHAUSTED" in err or "limit: 0" in err:
                print(f"  [WARN] {model_name} 쿼터 초과, 다음 모델 시도...")
                continue
            print(f"  [WARN] {model_name} 오류: {err[:80]}")
            continue

    print("  [WARN] Gemini API 사용 불가 (쿼터 초과 또는 결제 필요)")
    print("         → 폴백 키워드로 진행합니다")
    print("         → 해결: https://aistudio.google.com 에서 결제 설정 확인")
    return FALLBACK_KEYWORDS[:count]


if __name__ == "__main__":
    import sys
    sys.stdout.reconfigure(encoding="utf-8")  # type: ignore[union-attr]

    print("=" * 50)
    print("  Gemini 핫 키워드 수집")
    print("=" * 50)
    keywords = get_hot_keywords("한국 유튜브 트렌드", count=10)
    for i, kw in enumerate(keywords, 1):
        print(f"  {i:>2}. {kw}")
    print("=" * 50)
