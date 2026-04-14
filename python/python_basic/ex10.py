# 반복문 : while()문

# 형식 
# 반복변수 초기화
# while 조건식:
#  참일 때 수행하는 수행문
#  반복변수 증감

# 5초 카운드 다운
count = 5
while count > 0:
    print(count)
    count -= 1
print("발사")

# 무한루프에 빠지는 경우
# 1. 콘솔 화면을 클릭하고 CTRL + C로 빠져나온다.
# 2. 콘솔 세션을 종료(휴지통)한다.
# 3. 프로그램을 종료 후 재실행.
# 4. 작업관리자에서 Python 프로세스를 끝내기 한다.
# 5. pc 종료 후 재실행

# 무한루프 만들기 - 자판기, 엘레베이터 프로그램
count = 0
while True :
    count += 1
    if count == 100:
        break
    print(count)
print("무한루프 종료")

# continue문
for i in range(1, 11):
    if i % 2 == 0:
        continue
    print(i)

# 연습문제
# while문을 사용하여 사용자가 입력한 숫자의 자릿수를 세어서 출력하시오.
# 예시:
# 입력: 12345 → 출력: "5자리 숫자입니다"
# 입력: 789 → 출력: "3자리 숫자입니다"
# 힌트: 숫자를 10으로 나누면서 0이 될 때까지 반복
print()
print()
number = int(input("숫자 입력: "))
count = 0

while number > 0:
    number //= 10
    count += 1

print(f"{count}자리 숫자입니다.")