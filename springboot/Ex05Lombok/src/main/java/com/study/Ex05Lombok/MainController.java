package com.study.Ex05Lombok;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RequiredArgsConstructor // 생성자 주입에 주로 사용.
public class MainController {

   @Autowired
    private Member member; // 기본생성자로 생성된 객체가 주입된다.

   @GetMapping
    @ResponseBody
    public String member(){
       member.setName("Hong");
       System.out.println("member = " + member.getName());
       System.out.println("member = " + member.getAge());
       return "럼복 예제 서버입니다." +member.getAge()+member.getAge();
   }

   @GetMapping("allArgs")
    @ResponseBody
    public String allArgs(){
       Member member = new Member("god", 30, "010-1234-5678", "adsa@g");
       return member.getName()+member.getAge();
   }

   // 생성자 주입
    private final Member member1;
   @Autowired
    public MainController(Member member){ // 기본생성자로 생성된 객체가 주입된다.
       this.member1 = member;
   }

   @GetMapping("/requArgs")
    @ResponseBody
    public String requArgs(){
       member1.setPhone("3456");
       return "requArgs()호출" +member1.getPhone();
   }


//   private Member member4;
//   @Autowired
//   public MainController(Member member){
//       this.member4 = member;
//       System.out.println("member = " + member);
//   }

}
