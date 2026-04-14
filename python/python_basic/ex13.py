# 딕셔너리 Dictionary(사전) 영한사전 집(키) -> House(깂)
# Key - Value로 이루어진 데이터 구조 -> 자바의 Map과 자바스크립트의 Object와 비슷하다.

# 빈 딕셔너리
empty_dict = {}
print(empty_dict)

person = {
    "name" : "홍길동",
    "age" : 30,
    "city" : "서울"
}

print(person)

print(person["name"])
print(person.get("name"))
print(person.get("address")) # None
print(person.get("address", "주소값 없음"))

print(person.keys())
print(type(person.keys())) # type : dict_keys라는 객체 타입
print(list(person.keys())) # ['name', 'age', 'city']
print(list(person.values())) # ['홍길동', 30, '서울']

# 요소 삭제
del person["age"]
print(person)

# 전체 삭제
person.clear()
print(person)