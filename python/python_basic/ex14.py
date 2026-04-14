# 파이썬 함수
# 용도
# 1. 코드 중복 제거
# 2. 코드 재활용
# 3. 모듈화, 간격화


def add(x, y):
    return x + y


# 함수 호출
print(add(10, 20))


# 매개변수 기본값
def show_msg(message, sender="익명"):
    print(f"{message} {sender}")


show_msg("안녕하세요.", "손님1")
show_msg("안녕하세요.")


# 가변 인자 리스트 : 매개변수가 여러개, *을 씀.
def func_sum(*numbers):
    print(type(numbers))
    sum = 0
    for i in numbers:
        sum += i
    return sum


print(func_sum(1, 2, 3))
print(func_sum(1, 2, 3, 4, 5))


# 함수의 반환값 타입(타입 힌트 사용시)
def greeting(message: str) -> str:
    return f"{message}"


greeting("안녕하세요")
