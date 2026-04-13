# 조건문
# 특정 조건식(절)이 참일 때 선택적 실행을 하는 문장

# 형식
# if 조건(절, True of False):
# 조건이 참일 때 수행되는 코드(들여쓰기 필수)

# 파이썬의 Scope(Block)은 들여쓰기로 구분된다.
# Java, JS의 경우는 {} 중괄호

# if문의 4가지 형식
# 1. 단순 if문
score = 85
if score >= 60 :
    print("합격")

# 2. if else문
age = 18
if age >= 20:
    print("성인입니다.")
else :
    print("미성년자입니다.")

# 3. if elif문 : elif란 java나 js에서 else if랑 같음
month = 4
if month <= 3:
    print("1,2,3월입니다.")
elif month <= 6:
    print("4,5,6월입니다.")
elif month <= 9:
    print("7,8,9월입니다.")
elif month <= 12:
    print("10,11,12월입니다.")   

# 4. 중첩 if문(if문 안에 if문)
if True :
    if False:
        print("True and False")
    else :
        print("True and True")
        if True:
            print("True and True and True")
        elif False :
            print("True and True and False")

# 파이썬 랜덤 함수
import random
# 0, 1, 2중 하나가 랜덤하게 선택된다.
random_int = random.randint(0,2) # 0이상 2이하의 점수를 정수를 반환환
print(random_int)

# 연습문제
# 1. 마이클의 영어 점수를 입력받고,
# 영어 점수가 90점 이상이면, 'A학점'
# 영어 점수가 80점 이상이면, 'B학점'
# 영어 점수가 70점 이상이면, 'C학점'
# 영어 점수가 70점 미만이면, 'D학점' 으로 출력하시오.
eng_score = int(input("영어 점수를 입력하세요: "))

if eng_score >= 90:
    print("A학점")
elif eng_score >= 80:
    print("B학점")
elif eng_score >= 70:
    print("C학점")
else:
    print("D학점")


# 2. 오늘의 날씨 예보 출력
# 랜덤값으로 0, 1, 2 중 하나의 수를 발생시키고
# 0이면, "오늘의 날씨는 비 입니다. 우산을 챙기세요!"
# 1이면, "오늘의 날씨는 흐림 입니다. 나들이 가기 좋을 수도 있어요."
# 2이면, "오늘의 날씨는 맑음 입니다. 화창한 하루 되세요!"
# 을 출력하시오.
randNumber = random.randint(0,2)

if randNumber == 0:
    print("오늘의 날씨는 비 입니다. 우산을 챙기세요!")
elif randNumber == 1:
     print("오늘의 날씨는 흐림 입니다. 나들이 가기 좋을 수도 있어요.")
else:
    print("오늘의 날씨는 맑음 입니다. 화창한 하루 되세요!")