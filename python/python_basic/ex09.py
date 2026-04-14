# 반복문 : 반복적으로 코드를 수행하는 문장
# for문 while문  (do while문 없음)

# for문 형식
# for문 변수 in 시퀀스(range, list, tuple, string)
#   print(f'반복 변수(인덱스): {i}')

for i in range(5) : # 0~4까지의 범위를 가짐
    print(i)
# 1부터 20까지 반복변수를 출력
for i in range(1, 21):
    print(i) # 줄바꿈
    print(i, end=" ") #1,2,3,...20

# 1부터 100까지 출력하면서 2씩 증가하면서(1,3,5,7,...)
for i in range(1, 101, 2):
    print(i, end=" ")

# 연습문제
# 1. 1부터 100사이의 2의 배수를 출력하시오.
#    반복문 안에서 조건문+연산자를 사용하는 문제
print()
print()
for i in range(1,101):
    if i % 2 == 0 :
        print(i, end= " ")

# 2. 1부터 100사이의 2의 배수이면서, 3의 배수인 수들을 모두 출력하시오.
print()
print()
for i in range(1,101):
    if i % 2 == 0 and i % 3 == 0:
        print(i, end = " ")

# 3. 1부터 100까지의 합을 출력하시오.
#    반복문 시작하기 전에 sum = 0, 반복문 안에서 sum = sum + n을 하면 됨.
print()
print()
sum = 0
for i in range(1,101):
    sum += i
print(sum)

print()
# 4. 1부터 100사이의 수 중에 숫자안에 3이 들어간 수를 모두 출력하시오.
#    (삼삼칠 박수: 3 13 23 30 ~ 39 43 ... )
for i in range(1,101):
    if i // 10 == 3 or i % 10 == 3:
        print(i, end = " ")