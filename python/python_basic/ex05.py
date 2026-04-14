# 주요 데이터 타입 - 데이터를 담는 그릇

# 변수 Variable
# 숫자 number
# 정수형 : int
from cgitb import reset


age = 30
year = 2026
print(type(age), type(year)) # <class 'int'> <class 'int'>
# 실수형
pi = 3.14
print(type(pi))
# 복소수형 : complex
c1 = 3 + 4j # 실수부 + 허수부부
print(type(c1))
print(c1.real) # real : 실수부
print(c1.imag) # imag : 허수부
# 문자열형
String = "hello python"
print(type(String))

# 리스트 List = 배열/리스트
myList = [1, 2, "hello", 3.14, True]
print(type(myList))
print(myList[2])
myList.append("kth")
print(myList) # [1, 2, 'hello', 3.14, True, 'kth']

# 튜플 tuple = 길이가 고정된 리스트(함수 반환값 리턴용)
#            = 변경 불가 
myTuple = (10, 20, "apple", False)
print(type(myTuple)) # <class 'tuple'>
# myTuple.append("new") # 에러 -> 변경불가한 것이라 값을 추가할 수 없음.
print(myTuple)

# 딕셔너리 Dictionary = Key:Value(Js 객체, Java Map, 클래스)
dict_person = {"name" : "hong", "age" : "26"}
print(type(dict_person)) # <class 'dict'>
print(dict_person["name"])
print(dict_person.keys())
print(dict_person.values())

# 세트 Set - 중복되지 않는 집합 리스트
mySet = {1, 2, 3, 2, 1}
print(mySet) # {1, 2, 3} -> 중복된 값을 허용하지 않음.

# 논리 Boolean
is_True = True

# 값 없음 NoneType = 값 없음을 알려줌(void, null)
# 변수를 초기화할 때, 미리 알고 있는 값이 없으면, 타입을 모를 때
result = None
print(type(result)) # <class 'NoneType'>

if result == None:
    print("결과 값 없음")

# 타입 힌트(인레이 힌트)
#  Pylance(파이썬 언어 서버)가 코드 분석해서 타입 정보를 옆에 보여주는 기능입니다.
name : str = "홍길동"
age : int = "20"
height : float = "185.5"
is_good : bool = True

# 콜렉션 타입은 임포트해야 됨.
from typing import List, Dict, Tuple, Set, Any
scores : List[int] = [70, 80, 90]

# Any : 어떤 타입이 들어와도 되는 타입(모든 타입입)
#       : 자바의 다형성(Object)랑 비슷함
student : Dict[str, Any] = {"국어" : 80, "영어" : 90.5}

point : tuple[int, int] = (10, 20)

fruits : Set[str] = {"사과", "배", "사과", "수박"}