package com.study.Ex01FirstApp;
// package : 폴더 경로를 다르게 함으로 동일한 클래스 이름을 구분하는 것
// com.study.MyClass : 클래스 이름은 동일해도, 다른 패키지(폴더)에 있으므로
// com.play.MyClass     동일 이름의 클래스를 사용하게 된다.
// 예) 서울 사는 김서방, 인천 사는 김서방

import org.springframework.boot.SpringApplication;
//autoconfigure : 라이브러리들의 dependency(버전 체크)
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ex01FirstAppApplication {

	public static void main(String[] args) {
		// String[] args : 프로그램 구동시 주는 파라미터
		// 에) 한컴오피스 hwp.exe 문서1.hwp 문서2.hwp -> 문서1.hwp 문서2.hwp가 args인 셈
		// 예) java -version

		// SpringApplication.run : 스프링 앱 실행
		// Ex01FirstAppApplication.class : 클래스 정보를 담은 객체
//		SpringApplication.run(Ex01FirstAppApplication.class, args);

		System.out.println("메인함수 실행됨.");

	}

}
