# proj01.py

# 1인 개인 미니 프로젝트

# 간단한 계산기
# 콘솔 기반의 간단한 사칙연산이 가능한 계산기를 만들어 봅니다.
# 클래스로 설계하면 더 좋습니다.

# 입력/출력 예시

"""
=== 간단한 계산기 ===
1. 덧셈
2. 뺄셈
3. 곱셈
4. 나눗셈
5. 종료

선택하세요 (1-5): 1
첫 번째 숫자를 입력하세요: 10
두 번째 숫자를 입력하세요: 5
결과: 10.0 + 5.0 = 15.0

=== 간단한 계산기 ===
1. 덧셈
2. 뺄셈
3. 곱셈
4. 나눗셈
5. 종료

선택하세요 (1-5): 5
프로그램을 종료합니다.
"""

class Calc:
    def add(self, a, b):
        return a + b

    def sub(self, a, b):
        return a - b

    def mul(self, a, b):
        return a * b

    def div(self, a, b):
        if b == 0:
            raise ZeroDivisionError("0으로 나눌 수 없습니다.")
        return a / b


calc = Calc()
ops = {
    "1": ("+", calc.add),
    "2": ("-", calc.sub),
    "3": ("*", calc.mul),
    "4": ("/", calc.div),
}

while True:
    print("\n=== 간단한 계산기 ===")
    print("1. 덧셈")
    print("2. 뺄셈")
    print("3. 곱셈")
    print("4. 나눗셈")
    print("5. 종료")

    choice = input("\n선택하세요 (1-5): ")

    if choice == "5":
        print("프로그램을 종료합니다.")
        break

    if choice not in ops:
        print("잘못된 입력입니다. 1~5 중에서 선택하세요.")
        continue

    try:
        a = float(input("첫 번째 숫자를 입력하세요: "))
        b = float(input("두 번째 숫자를 입력하세요: "))
        symbol, func = ops[choice]
        result = func(a, b)
        print(f"결과: {a} {symbol} {b} = {result}")
    except ZeroDivisionError as e:
        print(f"오류: {e}")
    except ValueError:
        print("오류: 숫자를 입력해주세요.")

