spring.application.name=Ex10RealDB

# 오라클 CE버전 - 8080포트가 기본이라서 포트 충돌이 남. 포트 회피.
server.port=8090

        # thymeleaf
spring.thymeleaf.cache=false

        #JPA : Java Persistence API라고 하며
#하위층에 하이버네이트 구현 스펙의 구현체 중의
#하나이며, 실제적인 실무 최신 표준기술이다.
        #JPA함수를 통해 SQL 문을 자동으로 생성해 준다.
#SQL 중심의 DB기술이 아닌 객체 중심의 프로그래밍
#을 가능하게 해준다. ORM 기술을 이용한다.
#DB 테이블과 엔티티 클래스와 1:1 매핑이 가능하다.

        #레거시 : JDBC, MyBatis 기술은 직접 SQL문을 작성.
#SQL은 문자열이므로, 문법체크나 오류 검색이
#사실상 어려움.

        # JPA 설정
# none: 기본값. 데이터베이스 스키마에 아무런 변화를 주지 않습니다.
        # update: 엔티티 클래스의 변경 사항을 감지하여 데이터베이스 스키마를 업데이트합니다. (기존 데이터/스키마 유지)
        # create: 애플리케이션 시작 시 기존 테이블을 삭제하고  엔티티 클래스로 새로 생성합니다. (기존 데이터 삭제됨)
        # create-drop: 애플리케이션 시작 시 테이블을 생성하고, 앱 종료 시 생성했던 테이블을 모두 삭제합니다.
# validate: 엔티티 클래스 구조와 데이터베이스 테이블 구조가 일치하는지만 확인합니다.
        #     다를 경우 예외를 발생시키며 실행되지 않습니다.
spring.jpa.hibernate.ddl-auto=validate
# DDL 자동 생성 기능을 사용할지 여부 (기본적으로 ddl-auto 설정이 우선됨)
spring.jpa.generate-ddl=false
        # 실행되는 SQL 쿼리를 콘솔 로그에 출력
spring.jpa.show-sql=true

        # 사용할 데이터베이스의 방언(Dialect) 설정
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# pretty sql format - 줄바꿈, 코멘트을 달아줌.
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true

        # mysql
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.hikari.jdbc-url=jdbc:mysql://localhost:3306/mydb?characterEncoding=UTF-8&serverTimezone=Asia/Seoul
spring.datasource.username=root
spring.datasource.password=mysql123

# oracle
#spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
#spring.datasource.hikari.jdbc-url=jdbc:oracle:thin:@localhost:1521/xe
#spring.datasource.username=myuser
#spring.datasource.password=mypassword

# logging
logging.level.org.hibernate.type.description.sql.BasicBinder=trace
logging.level.org.hibernate.SQL=debug
# Hibernate 6.1.5 updated in springboot 3.x, 4.x
logging.level.org.hibernate.orm.jdbc.bind=trace