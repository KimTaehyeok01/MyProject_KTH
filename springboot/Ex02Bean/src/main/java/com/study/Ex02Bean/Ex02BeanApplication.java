package com.study.Ex02Bean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Bean : 스프링에서 관리하는 자바 클래스 객체를 의미함.
//   1. 기본적으로 싱글톤을 지원함.
//   2. Dead되면, 자동 복구됨.
//   3. 자동 주입(DI - Dependency Injection)
//       = 제어의 역전(IOC- Inverse of Control)
//      개발자가 직접 객체를 생성(new)하지 않고,
//      F/W이 생성(관리)해주는 것을 사용하는 것.(객체 관리로부터 자유!)
//      개발자가 A -> B-> C  스프링 C -> B -> A

// Annotation : 자바 코드에 붙이는 메타데이터로서 컴파일러(스프링)에게
//              정보를 제공하는 역할을 하는 심볼
// SpringBootApplication : 3가지 어노테이션이 붙어있는 어노테이션

@SpringBootApplication
public class Ex02BeanApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ex02BeanApplication.class, args);
    }


}
