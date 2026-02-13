package com.study.Ex02Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

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
//    : 기본적인 스프링부트 앱 개발환경과 설정을 다 해준다.

// @ComponentScan : @Component가 붙은 클래스를 찾아서 Bean으로 등록한다.
// @EnableAutoConfiguration : 스프링 프레임워크의 기본적인 기능을 활성화하는 어노테이션
// @SpringBootConfiguration : @Configuration이 붙은 클래스를 찾아서 사용자 설정 클래스
//                             로 등록한다. 주로 부가적인 기능을 추가할 때 사용.


// Bean : 스프링 프레임워크에서 관리하는 자바 클래스 객체를 의미한다. 싱글톤(Singleton)이며,
//          같은 이름의 Bean을 중복해서 생성하는 것은 불가. 첫글자는 영소문자로 시작한다. 예)studentNo

// 빈을 만드는 방법
// 1. @Configuration + @Bean :
//          Config클래스 안에서 사용되고, 주로 외부 라이브러리를 사용시 사용한다.
// 2. @Component + @Autowired :
//          주로 개발자가 직접 만든 클래스를 Bean으로 등록하기 위해 사용한다.

@Configuration
class MyConfig {
    // 외부 라이브러리(JAVA표준) 빈 등록한다.
    @Bean
    public java.util.Random random() { // java.util를 import안할거면 저렇게 붙여서 씀.
        return new java.util.Random();
    }
//    public Random random(){ // java.util.Random을 import한 상태.
//        return new Random();
//    }
}

@Component
class Student {
    String name = "홍길동";
    @Autowired // 빈을 주입받는다(DI)
    java.util.Random random;

}


@SpringBootApplication
public class Ex02BeanApplication {

    public static void main(String[] args) {
        System.out.println("스프링 애플리케이션 시작!");

        // ApplicationContext : 앱 정보를 가지고 있는 객체. = 스프링 컨테이너
        ApplicationContext context =
                SpringApplication.run(Ex02BeanApplication.class, args);

        // 스프링 컨테이너(Bean 보관소)에 등록된 빈 목록을 출력해보자.
        // myConfig, student, random
        String[] beanNames =context.getBeanDefinitionNames();

        Arrays.sort(beanNames);
        for(String beanName : beanNames){
            if(beanName.equals("random")){
                System.out.println(beanName);
            }
        }
        // random, student 빈을 가져오기
        java.util.Random r = context.getBean(java.util.Random.class);
        System.out.println(r.nextInt(10));
        Student student = context.getBean(Student.class);
        System.out.println(student.name);
        System.out.println(student.random.nextInt(10));
    }
}























