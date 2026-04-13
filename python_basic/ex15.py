# 파이썬 클래스
# 클래스 Class
# 객체 지향(사물, 물건 지향) 프로그래밍 :
# 클래스 : 변수 + 함수
#         (속성)  (행동)

# 선언
class Dog:
    age = 10 # 속성
    def eat(self):  # self : 클래스 객체 자신을 말함 -> 자바의 this랑 비슷함.
        print("사료를 먹는다.")

# 클래스로부터 객체 생성
dog = Dog()

print(dog.age)
dog.eat()

# 주의!
print(dog.eat) # 함수 객체를 출력하는 것것
print(dog.eat()) # 함수 호출 (리턴타입)