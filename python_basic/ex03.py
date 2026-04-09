# 문자열 다루기
str = "Life is too short, You need Python"

# 문자열을 여러줄로
str = """
Life is too short, 
You need Python
"""

# 문자열 중간에 글자 '을 넣고 싶다.
print("python\'s farvortie food is")

# 문자열 합치기
print("python " + "is fun!")
print("python" * 3)
print("=" * 50)

# 문자열 인덱싱
str = "Life is too short, You need Python"
print(str[0])
print(str[1])
print(str[-1]) # 문자열 맨 마지막 문자를 출력함 : n
print(str[-2]) # : o

# 웹 개발
# 파이썬 : 웹개발(Django-무겁다), streamlit
# js : React.js, Next.js (복잡하다)
# java : html + spring (복잡하다, 용량이 크다)

# 문자열 슬라이싱
print(str[0:4]) # 시작 인덱스 : 끝 인덱스-1
print(str[:4])
print(str[19:]) # 시작 인덱스부터 끝까지
print(str[:])
print(str[19:-7])

# 문자열 데이터 바인딩(보간)
print("I eat %d apple"% 3) # %d는 10진수를 의미
print("I eat %d apple, I sell %d apple" %(3,2))
print("%0.4f" % 3.141592) # 소숫점 4자리
print("%10.4f" % 3.141592) # 전체 10자릿수 10자리, 소숫점 4자리
print("%010.4f" % 3.141592) # 빈 공간을 0으로 채우고 10자리

# 문자열 길이
a = "hobby"
print(len(a))

# 특정 문자의 갯수 찾기
print(a.count("b"))
print(a.count("c")) # 특정 문자를 찾을 수 없기 때문에 0으로 출력

# 특정 문자의 위치 
print(a.find("y"))
print(a.find("b", a.find("b") + 1)) # 두 번째 b위치
print(a.find("c")) # 특정 문자를 찾을 수 없기 때문에 -1으로 출력

# 구분자 넣기
a = ","
print(a.join("abcd")) # a,b,c,d

# 양쪽 공백 없애기
a = " HI "
print(a.strip()) # JS에서는 trim() 함수

# 문자열 쪼개기 => 배열
a = "Life is too short"
print(a.split()) # ['Life', 'is', 'too', 'short'] => 오직 공백만 배열/리스트로 분리함.

a = "Life, is, too, short"
print(a.split()) # ['Life,', 'is,', 'too,', 'short']

a = "Life/is/too/short"
print(a.split()) # ['Life/is/too/short']

# 문자열 바꾸기
a = "Life is too short"
print(a.replace("Life", "Your leg"))  # replace("원래 문자", "바꾸고 싶은 문자")