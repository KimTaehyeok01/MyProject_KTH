import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    ArrayList<String> car = new ArrayList<>();

    car.add("genesis");
    car.add("kia");
    car.add("hydai");
    car.add("bentz");
    car.set(2, "ssanyeong");
    car.remove(1);

    for(String cars : car){
    System.err.println(cars);
    }
  }
}