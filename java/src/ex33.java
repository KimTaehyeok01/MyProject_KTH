// 클래스(객체) 타입의 배열 제어하기
class Snack{
    String name = "새우깡";
    int price = 1000;

    public Snack(String name, int price){
        this.name = name;
        this.price = price;
    }
}

public class ex33 {
    public static void main(String[] args) {
        // 정수형 1차 배열
        int [] nums = new int[5]; // 5개의 인덱스가 다 0으로 초기화됨..

        // 클래스형 1차 배열 : 클래스도 하나의 타입으로 생각한다.
        Snack[] snacks = new Snack[4]; // 5개의 인덱스가 다 null로 초기화됨.

        snacks[0] = new Snack("짱구", 2000);
        snacks[1] = new Snack("포카칩", 2000);
        snacks[2] = new Snack("빼빼로", 2000);
        snacks[3] = new Snack("허니버터칩", 2000);

        for(Snack snack : snacks){
            System.out.println(snack.name);
            System.out.println(snack.price);
        }
    }

}
