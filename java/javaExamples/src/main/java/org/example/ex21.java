package org.example;
// 접근제한자
// : 클래스, 함수, 변수, 앞에, 위치하여 접근을 제한할 때 사용
// c언어(1세대) : 접근제한자가 없어서 모든 곳에서 접근 가능
//             : 변수의 변경을 감지하기 어렵다. 디버깅/유지/보수 어려움.
// public : 같은 폴더(패키지)에서, 다른 폴더의 클래스에서 접근 가능.
// protected : 같은 폴더 + 상속관계 클래스에서 접근 가능
// default : 같은 폴더
// private : 같은 클래스 안에서 접근 가능(캡슐화, 은닉)
//         : 다른 클래스에서 Getter/Setter함수를 통해서만 접근가능
//         : 변수나 함수를 최대한 노출하지 않는 것(값 관리-접근제어)

class Hong {
    // 클래스 밑에 바로 선언된 변수 : 멤버변수(필드)
    String name = "홍길동"; // default : 같은 폴더 + 자기클래스에선 접근 가능
    public int age = 20; // public : 다른 폴더 + 상속관계 + 같은 폴더 + 자기클래스
    protected int korScore = 80; // protected : 같은 폴더 + 상속관계 + 자기클래스
    private int engScore = 90; // private : 자기 클래스에서만 접근 가능

    // Getter/Setter 함수 제공
    public int getEngSocre() {
        return this.engScore;
    }

    public void setEngSocre(int engScore) { // 지역변수(매개변수)
        this.engScore = engScore;
    }

}

public class ex21 {
    public static void main(String[] args) {
        Hong hong = new Hong();
        System.out.println(hong.name); // default(같은폴더)
        System.out.println(hong.age); // public
        System.out.println(hong.korScore); // protected(같은 폴더)
//        System.out.println(hong.engScore); // private(같은 클래스에서만)
        hong.setEngSocre(100);
        System.out.println(hong.getEngSocre());;
    }
}
