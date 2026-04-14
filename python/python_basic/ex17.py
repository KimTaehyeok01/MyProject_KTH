# 클래스 상속


class Animal:
    age = 0

    def sleep(self):
        print("잔다.")


class Dog(Animal):  # 상속
    def sleep(self):
        print("강아지가 잔다.")  # 함수 - 오버라이드


# 클래스의 다중 상속이 가능하다.
class Flyable:
    def fly(self):
        print("날 수 있다.")


class Bird(Animal, Flyable):
    pass
