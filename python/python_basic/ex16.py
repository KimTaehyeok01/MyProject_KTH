# 클래스 생성자 함수
# 생성자 함수 : 객체가 생성시 자동으로 호출됨


class Car:
    def __init__(self):
        print("생성자 함수")
        pass  # 아무 수행도 하지 않음을 기술함.


car = Car()


class Tesla:
    def __init__(self, brand, color):
        self.brand = brand
        self.color = color

    def info(self):
        print(f"{self.brand} {self.color}")


t_car = Tesla("테슬라", "레드")
t_car.info()
