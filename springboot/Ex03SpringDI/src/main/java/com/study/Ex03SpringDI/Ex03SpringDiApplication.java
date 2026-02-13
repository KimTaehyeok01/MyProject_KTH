package com.study.Ex03SpringDI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ex03SpringDiApplication {
	// Bean을 생성하는 2가지 방법
	// 1. @Configuration + @Bean
	// 2. @Component + @Autowired

	// @Autowired
	//   1) 필드 주입 : 가장 일반적인 방법
	//   2) 수정자 주입 : setter함수. 잘 안씀.
	//   3) 생성자 주입 : 가장 추천하는 방법

	public static void main(String[] args) {
		SpringApplication.run(Ex03SpringDiApplication.class, args);

	}

}
