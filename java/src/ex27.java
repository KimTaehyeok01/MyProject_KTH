class Desk1{
    String color = "갈색";

    Desk1(String color){
        this.color = color;
        System.out.println("바뀐 색깔은 " + this.color + "입니다.");
    }

    void work(){
        System.out.println("일한다.");
    }
}

public class ex27 {
    public static void main(String[] args) {
        Desk1 desk1 = new Desk1("흰색");
        desk1.work();
    }

}
