# 리스트 list :
#   여러 타입의 값을 순서대로 저장하고 관리하는 데이터 구조

# 빈 리스트 : 
empty_list : list = []
print(empty_list)

numbers = [1,2,3,4,5]
minxid_list = ["apple", 123, 3.14, True]
nested_list = [[1,2], (1,2), {"first" : 1, "second" : 2}]

print(numbers[0]) # 1
print(numbers[:3]) # [1, 2, 3]
print(nested_list) # [[1, 2], (1, 2), {'first': 1, 'second': 2}]
print(numbers[::2]) # [1, 3, 5] 두 칸씩 인덱스 올라감
print(numbers[::-1]) # [5, 4, 3, 2, 1] 역순으로 출력됨 (reversed랑 같음)

# 요소 변경
my_list = ["a", "b", "c"]
my_list[1] = "hello"
print(my_list) # ['a', 'hello', 'c']

# my_list[3] = "d"
# print(my_list) # list assignment index out of range 오류
my_list.append("d")
print(my_list) 

my_list.insert(0, "z")
print(my_list)

# 리스트와 합치기
other_list = ["x", "y"]
my_list.extend(other_list)
print(my_list)

# 요소 삭제
# del은 인덱스를, remove는 요소를 기입한다.
my_list2 = ["a", "b", "c", "d", "e"]
del my_list2[2]
print(my_list2)

my_list2.remove("b")
print(my_list2)

# 모든 요소를 삭제
my_list2.clear()
print(my_list2)
print(len(my_list2))

# 정렬 sort
scores = [85, 92, 78, 92, 65]
scores.sort()
print(scores)
scores.reverse() # [65, 78, 85, 92, 92]
print(scores) # [92, 92, 85, 78, 65]

print(scores.count(92))
print(scores.index(78))