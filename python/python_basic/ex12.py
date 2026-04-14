# 튜플 tuple
#   리스트이면서 변경이 불가한 데이터 타입
# 용도 : 고정된 값을 전달할 때 사영한다. 예) 함수의 반환값 용도

# 빈 튜플
empty_tuple = ()
print(empty_tuple)

# 튜플의 선언
numbers = (1,2,3,4,5)
numbers2 = 10,20,30
print(numbers)
print(numbers2)

# 튜플의 요소가 하나일 때
numbers3 = (5) # 정수 5
numbers4 = (5,) # 튜플

# 함수의 반환값을 사용할 때, 고정된 리스트값으로
def get_user_date():
    return "김철수", 25, "서울"
name, age, address = get_user_date()
print(type(get_user_date()))
print(name, age, address)