# 형변환 type casting

# 암묵적 형변환
x = 3
y = 4.12
z = x + y # 더 큰 범위의 타입으로 형변환 된다.
print(type(z)) # type : float

# 명시적 형변환
myInt = int("123")
myFloat = float(123)
myBoolean = bool(0)
print(myBoolean)

try: 
    myInt2 = int("123abc한글")
except:
    print("타입 변경 오류!")

print(str(123))
print(str(3.14))
print(str([1, 2, 3])) # 리스트 list

# 파이썬에서 false로 간주되는 값
# 숫자 : 0 0.0 (정수나 실수가 0이면 false로 간주함)
# 빈 문자열 ""
# 빈 리스트 []
# 빈 튜플 ()
# 빈 딕셔너리 {}
# 빈 세트 set()
# None

# 위의 값 외에는 모두 True로 간주됩니다.
print( bool(0) )
print( bool(1) )
print( bool(0.0) )
print( bool("Hello") )
print( bool("") )
print( bool([]) )
print( bool([1,2]) )
print( bool(()) )
print( bool(None) )

# 리스트로 형변환
print( list("Python") ) # 문자열 ['P', 'y', 't', 'h', 'o', 'n']
print( list((1,2,3)) ) # 튜플
print( list({1,2,3}) ) # 딕셔너리 - java map, js object랑 비슷하다.

# 튜플 - 길이와 값이 고정된 리스트
# 튜플로 형변환
print( tuple("Python") )
print( tuple([1,2,3]) )

# 집합으로 형변환
print( set("Python") )
print( set([1,2,3]) )

# 파이썬에서 변수 이름 짓는 법
# 1. 숫자로 시작하면 안됨.
# 2. 특수문자(!@#)을 사용할 수 없음. 단, _(언더바)는 사용 가능.
# 3. 영소문자로 시작하고
# 4. 단어와 단어 사이에는 _를 넣으면, Snake Case (더 많이 사용한다.)
#  예) student_count, user_passowrd
#  변수와 함수에는 Snake Case, 클래스에는 Camel(Pascal) Case를 사용한다.
#     (자바 - Camel Case)

# 변수 이름 짓기 연습!
# 1. 고객의 생일 이벤트를 위한 포인트
# => 영어로 바꾸기. 콩글리쉬(한국인) 잉글리쉬(원어민)
customer_birth_event_point = 1000 # 나
point_customer_birthday_event = 1000 # 원어민

# 2. 다음달에 다가오는 구독 고객의 마감일
next_month_subscribe_deadline = "2026-12-25" # 나
due_date_subscribe_next_month = "2026-12-21" # 원어민

# 상수 : 대문자 Snake Case
PI = 3.14
MY_PASSWORD = "1234"