# Spring Boot + Supabase (PostgreSQL) JPA 연동 매뉴얼

Spring Boot에서 Supabase PostgreSQL을 JPA로 연동하는 간단한 메모 CRUD 예제입니다.

---

## 목차

1. [사전 준비](#1-사전-준비)
2. [Supabase 프로젝트 생성](#2-supabase-프로젝트-생성)
3. [Supabase 연결 정보 확인](#3-supabase-연결-정보-확인)
4. [프로젝트 구조](#4-프로젝트-구조)
5. [의존성 설정 (build.gradle)](#5-의존성-설정-buildgradle)
6. [Application 설정](#6-application-설정)
7. [소스 코드 작성](#7-소스-코드-작성)
8. [실행 및 확인](#8-실행-및-확인)
9. [트러블슈팅](#9-트러블슈팅)

---

## 1. 사전 준비

- **Java 21** 이상
- **Spring Boot 4.x** (Gradle)
- **Supabase 계정** (무료 플랜 가능)

---

## 2. Supabase 프로젝트 생성

1. [https://supabase.com](https://supabase.com) 에 로그인
2. **New Project** 클릭
3. 프로젝트 이름, 데이터베이스 비밀번호, 리전 설정 후 생성
4. 프로젝트가 생성될 때까지 대기 (약 1~2분)

> **중요**: 데이터베이스 비밀번호는 반드시 기억해 두세요!

---

## 3. Supabase 연결 정보 확인

### 3-1. 연결 방식 선택

Supabase는 두 가지 연결 방식을 제공합니다:

| 방식 | 포트 | IPv4 지원 | 용도 |
|------|------|-----------|------|
| **Direct (직접 연결)** | 5432 | X (IPv6만) | 서버 배포용 |
| **Transaction Pooler** | 6543 | O | **로컬 개발 권장** |

> **무료 플랜에서는 IPv4가 Direct 연결에 제공되지 않습니다.**
> 로컬 개발 환경에서는 반드시 **Transaction Pooler (포트 6543)**를 사용하세요.

### 3-2. 연결 정보 확인 방법

1. Supabase 대시보드 > **Project Settings** > **Database**
2. **Connection string** 섹션 > **JDBC** 탭 선택
3. **Mode: Transaction** 선택
4. 표시되는 JDBC URL을 복사

```
jdbc:postgresql://aws-1-ap-northeast-1.pooler.supabase.com:6543/postgres
                  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                  ↑ 리전에 따라 호스트가 다름
```

### 3-3. 필요한 정보 정리

| 항목 | 예시 | 설명 |
|------|------|------|
| **Host** | `aws-1-ap-northeast-1.pooler.supabase.com` | 풀러 호스트 (리전별 다름) |
| **Port** | `6543` | Transaction Pooler 포트 |
| **Database** | `postgres` | 기본 데이터베이스 |
| **Username** | `postgres.nigbijomfvnwbpwlrcyp` | `postgres.` + 프로젝트 Reference ID |
| **Password** | 프로젝트 생성 시 설정한 비밀번호 | DB 비밀번호 |

> **Username 주의**: Transaction Pooler 사용 시 username은 반드시 `postgres.<project-ref>` 형식이어야 합니다.

---

## 4. 프로젝트 구조

```
src/main/
├── java/com/study/Ex20Supabase/
│   ├── Ex20SupabaseApplication.java    ← 메인 클래스
│   ├── entity/
│   │   └── Memo.java                  ← JPA 엔티티
│   ├── repository/
│   │   └── MemoRepository.java        ← JPA Repository
│   └── controller/
│       └── MemoController.java        ← 웹 컨트롤러
└── resources/
    ├── application.yml                 ← DB 연결 설정
    └── templates/memo/
        ├── list.html                   ← 목록 페이지
        └── form.html                   ← 작성 페이지
```

---

## 5. 의존성 설정 (build.gradle)

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.springframework.boot:spring-boot-starter-webmvc'
    compileOnly 'org.projectlombok:lombok'
    runtimeOnly 'org.postgresql:postgresql'
    annotationProcessor 'org.projectlombok:lombok'
}
```

핵심 의존성:
- `spring-boot-starter-data-jpa` : JPA + Hibernate
- `postgresql` : PostgreSQL JDBC 드라이버

---

## 6. Application 설정

### 6-1. application.yml

```yaml
spring:
  application:
    name: Ex20Supabase

  datasource:
    url: jdbc:postgresql://aws-1-ap-northeast-1.pooler.supabase.com:6543/postgres
    username: postgres.nigbijomfvnwbpwlrcyp
    password: "your-password"          # 반드시 따옴표로 감싸기 (특수문자 안전 처리)
    driver-class-name: org.postgresql.Driver
    hikari:
      connection-timeout: 30000
      maximum-pool-size: 10

  jpa:
    hibernate:
      ddl-auto: update                 # 테이블 자동 생성/수정
    show-sql: true                     # SQL 로그 출력
```

> **비밀번호에 `#`, `!`, `@` 등 특수문자가 포함된 경우 반드시 따옴표(`"`)로 감싸야 합니다.**
> `.properties` 파일에서는 `#`이 주석으로 인식되므로 `.yml` 파일 사용을 권장합니다.

### 6-2. Ex20SupabaseApplication.java

```java
package com.study.Ex20Supabase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ex20SupabaseApplication {

    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv6Addresses", "true");
        SpringApplication.run(Ex20SupabaseApplication.class, args);
    }
}
```

> `java.net.preferIPv6Addresses` 설정은 일부 환경에서 DNS 해석을 도와줍니다.

---

## 7. 소스 코드 작성

### 7-1. Entity (Memo.java)

```java
package com.study.Ex20Supabase.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "memo")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
```

- `@Entity` : JPA 엔티티 선언
- `@GeneratedValue(IDENTITY)` : PostgreSQL의 auto-increment 사용
- `@PrePersist` : 저장 전 자동으로 생성일시 설정
- `ddl-auto: update` 설정으로 Supabase에 `memo` 테이블이 **자동 생성**됨

### 7-2. Repository (MemoRepository.java)

```java
package com.study.Ex20Supabase.repository;

import com.study.Ex20Supabase.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
```

- `JpaRepository<Memo, Long>` 상속만으로 기본 CRUD 메서드 자동 제공
- `findAll()`, `save()`, `deleteById()` 등 별도 구현 불필요

### 7-3. Controller (MemoController.java)

```java
package com.study.Ex20Supabase.controller;

import com.study.Ex20Supabase.entity.Memo;
import com.study.Ex20Supabase.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/memo")
public class MemoController {

    private final MemoRepository memoRepository;

    // 목록
    @GetMapping
    public String list(Model model) {
        model.addAttribute("memos", memoRepository.findAll());
        return "memo/list";
    }

    // 작성 폼
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("memo", new Memo());
        return "memo/form";
    }

    // 저장
    @PostMapping
    public String save(@ModelAttribute Memo memo) {
        memoRepository.save(memo);
        return "redirect:/memo";
    }

    // 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        memoRepository.deleteById(id);
        return "redirect:/memo";
    }
}
```

| URL | 메서드 | 기능 |
|-----|--------|------|
| `/memo` | GET | 메모 목록 |
| `/memo/new` | GET | 새 메모 작성 폼 |
| `/memo` | POST | 메모 저장 |
| `/memo/{id}/delete` | POST | 메모 삭제 |

### 7-4. Thymeleaf 템플릿

#### list.html (목록 페이지)

`src/main/resources/templates/memo/list.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>메모 목록</title>
</head>
<body>
    <h1>메모 목록 (Supabase)</h1>
    <a href="/memo/new">새 메모 작성</a>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>제목</th>
                <th>내용</th>
                <th>작성일</th>
                <th>삭제</th>
            </tr>
        </thead>
        <tbody>
            <tr th:each="memo : ${memos}">
                <td th:text="${memo.id}"></td>
                <td th:text="${memo.title}"></td>
                <td th:text="${memo.content}"></td>
                <td th:text="${#temporals.format(memo.createdAt, 'yyyy-MM-dd HH:mm')}"></td>
                <td>
                    <form th:action="@{/memo/{id}/delete(id=${memo.id})}" method="post">
                        <button type="submit">삭제</button>
                    </form>
                </td>
            </tr>
        </tbody>
    </table>

    <p th:if="${#lists.isEmpty(memos)}">등록된 메모가 없습니다.</p>
</body>
</html>
```

#### form.html (작성 페이지)

`src/main/resources/templates/memo/form.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>메모 작성</title>
</head>
<body>
    <h1>메모 작성</h1>

    <form action="/memo" method="post" th:object="${memo}">
        <label>제목</label>
        <input type="text" th:field="*{title}" required>

        <label>내용</label>
        <textarea th:field="*{content}" required></textarea>

        <button type="submit">저장</button>
        <a href="/memo">취소</a>
    </form>
</body>
</html>
```

---

## 8. 실행 및 확인

### 8-1. 애플리케이션 실행

```bash
./gradlew bootRun
```

### 8-2. 브라우저에서 확인

| URL | 설명 |
|-----|------|
| http://localhost:8080/memo | 메모 목록 페이지 |
| http://localhost:8080/memo/new | 새 메모 작성 페이지 |

### 8-3. Supabase 대시보드에서 데이터 확인

1. Supabase 대시보드 > **Table Editor** 이동
2. `memo` 테이블이 자동 생성된 것을 확인
3. Spring Boot에서 입력한 데이터가 Supabase에 저장된 것을 확인

---

## 9. 트러블슈팅

### UnknownHostException (호스트를 찾을 수 없음)

```
java.net.UnknownHostException: db.xxx.supabase.co
```

**원인**: Direct 연결(5432)은 IPv6만 지원하며, 로컬 네트워크가 IPv6를 지원하지 않음

**해결**: Transaction Pooler (포트 6543) 사용

```yaml
# 변경 전 (Direct - IPv6만 지원)
url: jdbc:postgresql://db.xxx.supabase.co:5432/postgres

# 변경 후 (Pooler - IPv4 지원)
url: jdbc:postgresql://aws-1-ap-northeast-1.pooler.supabase.com:6543/postgres
username: postgres.your-project-ref    # username 형식도 변경 필요!
```

### Password Authentication Failed (비밀번호 인증 실패)

```
FATAL: password authentication failed for user "postgres"
```

**원인 1**: 비밀번호가 틀림 → Supabase 대시보드에서 비밀번호 재설정

**원인 2**: `.properties` 파일에서 `#` 문자가 주석으로 인식됨

```properties
# 문제: #이 주석으로 잘림
spring.datasource.password=abc123!@#

# 해결: .yml 파일 사용 + 따옴표
password: "abc123!@#"
```

### Dialect 오류

```
Unable to determine Dialect without JDBC metadata
```

**원인**: DB 연결 실패 시 Hibernate가 Dialect를 자동 감지하지 못함

**해결**: 위의 연결 문제를 먼저 해결하면 자동으로 해결됨. 임시로 명시 가능:

```yaml
spring:
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
```

---

## 연결 방식 요약

```
Spring Boot + Supabase PostgreSQL 연결
       ↓
Supavisor 풀러 사용 (포트 6543)
       ↓
Free 플랜 가능 / IPv4 지원 / 로컬 개발 OK
```
