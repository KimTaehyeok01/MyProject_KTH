// 싱글톤
//      : 프로그램 안에서 유일한 클래스 객체
//      : new를 통해서 객체를 여러 번 생성하는 구조이기 때문에
//        같은 클래스의 객체가 여러개 있다면, 데이터의 유지/보수가 어렵다.
//      : 스프링 프레임워크에서느 싱글톤을 자동으로 유지/관리 해준다.
//      : Bean(Spring안에서 객체)이 죽으면, 부활시켜주고, 중복 안되게 해줌.
//      : 유일한 객체(싱글톤)이 필요한 이유
//        - 유일한 정보를 저장/관리하기 위해

class FishBun{ // 붕어빵
    int bunNum = 10;
}

class FishBunSingleton{ // 유일무이한 붕어빵 (싱글톤)
    int bunNum = 30;
    private static FishBunSingleton singleton; // = null; 둘이 똑같음. 객체의 초기값은 null이다.
    // getInstall 메소드를 통해서 싱글톤 객체를 얻어온다.

    static FishBunSingleton getInstance(){ // jdk 개발자가 주로 사용한 함수
        // Getter/Setter 함수 이름 짓는 법 : get변수이름
        if(singleton == null){
            singleton = new FishBunSingleton();
        }
        return singleton;
    }

}

public class ex24 {
    public static void main(String[] args) {
        FishBunSingleton sBun1 = FishBunSingleton.getInstance();
        FishBunSingleton sBun2 = FishBunSingleton.getInstance();
        System.out.println(sBun1); // 주소값이 동일하다.
        System.out.println(sBun2); // 같은 값이다.
        sBun1.bunNum = 40;
        System.out.println(sBun1.bunNum);
        System.out.println(sBun2.bunNum);
        // 로그인, 회원정보 : 계정이름, 프로필이미지
        // 로그인, 메인화면, 회원정보 , 마이페이지, QnA,FaQ

        FishBun bun1 = new FishBun();
        FishBun bun2 = new FishBun();

        System.out.println(bun1); // FishBun@2f4d3709
        System.out.println(bun2); // FishBun@4e50df2e

        bun1.bunNum = 20;
        System.out.println(bun1.bunNum);
        System.out.println(bun2.bunNum);
        // 일반 객체는 일관된 객체를 저장하기 어려움
        // non-static(dynamic) 객체는 heap영역에 저장되고,
        // GC(Garbage Collector)가 자동으로 메무리에서 회수된다.
    }
}
