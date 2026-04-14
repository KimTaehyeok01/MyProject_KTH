# 연산자


# 연산자 우선 순위
print(10 - 2 * 4 / 2)
# 우선순위가 높은 연산자가 먼저 연산된다.
# 우선순위가 같으면 왼쪽 -> 오른쪽으로 연산된다.
# () 소괄호가 우선순위가 가장 높다.

# 1. **             거듭제곱
# 2. * / // %       곱셈, 나눗셈
# 3. + -            덧셈, 뺄셈셈
# 4. = != < > <= >= 비교 연산
# 5. not            부정 로직
# 6. and            AND 로직
# 7. or             OR 로직
# 8. = += -=        복합 대입연산자

print(10 ** 3) # 10 * 10 * 10
print(10 * 2)

# 비교 연산자
a = 5 
b = 4
print(a == b)
print(a != b)
print(a >= b)
print(a <= b)

# 논리연산자
print(True)
print(False)
print(not True) # 논리 부정

# and
print(True and True)
print(False and True)
print(False and False)
print(True and False)

# or
print(True or True)
print(True or False)
print(False or False)
print(False or True)