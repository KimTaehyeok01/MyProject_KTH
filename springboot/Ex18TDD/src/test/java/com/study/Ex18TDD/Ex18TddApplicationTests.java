package com.study.Ex18TDD;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// 단위 테스트 : 응용 프로그램에서 테스트 가능한 가장 작은 소프트웨어를 실행하여 예상대로 동작하는지 확인하는 테스트.

// 단위테스트 정의 및 장점  <-->  통합테스트
// 1. 메소드 단위로 기능테스트     전체 앱의 일부 기능
// 2. 가볍고 빠르다.

// @BootstrapWith : ApplicationContext(빈 관리) 초기화 방법 결정
// @ExtendWith : JUint5와 Spring을 연결해주는 어노테이션 역할
@SpringBootTest // @SpringBootTest : 테스트에서 전체 앱 컨텍스트를 로드해주는 핵심 어노테이션
class Ex18TddApplicationTests {
	// 단위 테스트 지원 함수들
	// 1. Assert(단정) 함수
	// 2. AssertJ

	// @Test : 단위 테스트 케이스임을 알려주는 어노테이션
	@Test
	void contextLoads() {
		// contextLoads : Application Context기 정상 로드되었음을 알림.
		System.out.println("테스트 준비 완료!");
	}

}
