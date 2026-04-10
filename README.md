<div align="center">

# 👨‍💻 Kim Taehyeok's Dev Portfolio

<img src="https://readme-typing-svg.demolab.com?font=Fira+Code&size=22&pause=1000&color=4F94F7&center=true&vCenter=true&width=600&lines=Full-Stack+Developer+in+Progress;Java+%7C+Spring+Boot+%7C+React+%7C+Python;Learning+by+Building+Real+Projects+%F0%9F%9A%80" alt="Typing SVG" />

<br/>

![GitHub last commit](https://img.shields.io/github/last-commit/KimTaehyeok01/MyProject_KTH?style=flat-square&color=4F94F7)
![GitHub repo size](https://img.shields.io/github/repo-size/KimTaehyeok01/MyProject_KTH?style=flat-square&color=4FC3F7)
![GitHub stars](https://img.shields.io/github/stars/KimTaehyeok01/MyProject_KTH?style=flat-square&color=FFD54F)

</div>

---

## 🗂️ 목차

- [🌟 주요 프로젝트](#-주요-프로젝트)
- [📚 학습 기록](#-학습-기록)
- [🛠️ 기술 스택](#️-기술-스택)
- [📁 폴더 구조](#-폴더-구조)

---

## 🌟 주요 프로젝트

### 🔐 블로그 플랫폼 (Full-Stack)
> `springboot/Blog-project(oauth,jwt포함)`

Spring Boot + React로 개발한 **풀스택 블로그 서비스**

| 항목 | 내용 |
|------|------|
| 백엔드 | Spring Boot (포트 8080) |
| 프론트엔드 | React (포트 3000) |
| 인증 | JWT 토큰 + Google OAuth2 소셜 로그인 |
| DB | MySQL |

**주요 기능**
- ✍️ 게시글 CRUD (작성 / 조회 / 수정 / 삭제)
- 💬 댓글 기능
- 🔍 키워드 검색
- 🔒 JWT 기반 인증 & 구글 OAuth2 로그인
- 🛡️ Spring Security 적용

---

### 📋 공지사항 게시판 (Full-Stack)
> `react/게시판`

Spring Boot 백엔드 + React 프론트엔드로 구성된 **공지사항 게시판**

```
게시판/
├── notice-board-java    → Spring Boot REST API
└── notice-board-react   → React 프론트엔드
```

---

### 🌤️ 날씨 분석 대시보드 (Python)
> `파이썬-날씨분석`

**Open-Meteo API**와 **Flask**를 활용한 실시간 날씨 분석 웹 서비스

- 📍 전국 10개 주요 도시 실시간 날씨 제공 (서울, 부산, 제주 등)
- 📈 최근 7일간 기온 / 강수량 / 습도 / 풍속 통계 및 시각화
- ⚡ `ThreadPoolExecutor`를 활용한 병렬 API 요청으로 성능 최적화
- 🌐 Flask 서버 → REST API → 프론트엔드 차트 렌더링

---

### 🎬 Netflix 클론 (Frontend)
> `Netflix`

넷플릭스 사이트를 재해석한 **포트폴리오 프로젝트**

- 🎨 HTML / CSS(SCSS) / JavaScript, jQuery
- 📱 반응형 웹 디자인 (`@mixin` 활용)
- 🖥️ FullPage.js 풀페이지 스크롤 구현
- 🎠 Swiper.js / Carousel 라이브러리 활용

---

### 🖥️ 간단한 로직 웹 앱 모음
> `간단한 로직 웹사이트`

JavaScript만으로 구현한 다양한 **미니 웹 애플리케이션**

| 앱 이름 | 설명 |
|---------|------|
| 🏫 학생 성적 관리 시스템 | 학생 정보 CRUD + 평균 점수 계산 |
| ☕ 커피 메뉴 관리 | 메뉴 추가/삭제/수정 |
| 🛒 서점 재고 관리 시스템 | 도서 재고 CRUD |
| 🎮 아이템 관리 | 게임식 UI의 아이템 관리 |
| 🔢 키오스크 | 주문 시스템 UI 구현 |
| 📝 생활 리스트 | 할 일 / 장보기 목록 관리 |
| 🔍 넷플릭스 검색창 | 실시간 검색 필터 구현 |

---

## 📚 학습 기록

### ☕ Java
> `java` — 기초부터 심화까지 **60개 이상**의 연습 예제

- 자료형, 제어문, 반복문, 배열, 클래스/객체
- 상속, 인터페이스, 예외처리
- 컬렉션 프레임워크, 제네릭
- 소켓 통신 (클라이언트 / 서버 구조)
- 마트 / 스토어 시스템 실습 예제

---

### 🌱 Spring Boot
> `springboot` — 단계별 학습 **20개** 프로젝트 + 실전 프로젝트

| 번호 | 주제 |
|------|------|
| Ex01 ~ Ex05 | Spring 기초, Bean, DI, Autowired, Lombok |
| Ex06 ~ Ex09 | 정적 웹, Thymeleaf, DevTools, Model |
| Ex10 ~ Ex13 | 실제 DB 연동, CRUD, 로그인/회원가입 |
| Ex14 ~ Ex15 | REST API, 게시판 |
| Ex16 ~ Ex17 | Spring Security, JWT 인증 |
| Ex18 ~ Ex20 | TDD, JPA 연관관계 매핑, Supabase 연동 |
| 실전 | Blog, 블로그(OAuth+JWT), 도서관 사이트, 메모, 수강신청 |

---

### ⚛️ React
> `react` — 컴포넌트 기초부터 게시판 구현까지

- `first-app` → React 기본 구조
- `basic` → 컴포넌트, Props, State, Hooks
- `ex-app1`, `ex-app2`, `exam-app` → 심화 실습
- `게시판` → Spring Boot REST API 연동 실전 프로젝트

---

### 🌐 HTML / CSS / JavaScript
> `html` / `css` / `javascript` — 각각 **30~80개 이상**의 연습 예제

**HTML** : 시맨틱 태그, 폼, 테이블, 미디어 태그, 회원가입 페이지  
**CSS** : 선택자, 박스모델, Flexbox, Grid, 반응형, 애니메이션, 메가박스 / 강아지 블로그 클론  
**JavaScript** : DOM 조작, 이벤트, 비동기, 배열 메서드, 객체, ES6+

---

### 🐍 Python
> `python_basic` / `파이썬-날씨분석`

- Python 기초 문법 (자료형, 제어문, 함수, 클래스)
- Flask 웹 서버
- Open-Meteo REST API 연동
- 멀티스레딩 (`ThreadPoolExecutor`)

---

### 🗄️ SQL
> `sql` — 챕터 1~8 체계적 학습 + 세계무역데이터 실습

- SELECT, WHERE, GROUP BY, JOIN, 서브쿼리
- DDL/DML, 제약조건, 인덱스
- 세계무역 실데이터를 활용한 분석 쿼리 실습

---

## 🛠️ 기술 스택

<div align="center">

### Backend
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-007396?style=for-the-badge&logo=hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

### Frontend
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![SCSS](https://img.shields.io/badge/SCSS-CC6699?style=for-the-badge&logo=sass&logoColor=white)

### Python & Data
![Python](https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white)
![Flask](https://img.shields.io/badge/Flask-000000?style=for-the-badge&logo=flask&logoColor=white)

### DevOps & Tools
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![OAuth2](https://img.shields.io/badge/OAuth2-EB5424?style=for-the-badge&logo=auth0&logoColor=white)

</div>

---

## 📁 폴더 구조

```
MyProject_KTH/
│
├── 📂 springboot/              # Spring Boot 학습 & 실전 프로젝트
│   ├── Ex01 ~ Ex20/            # 단계별 학습 예제
│   ├── Blog/                   # 블로그 (기본)
│   ├── Blog-project(oauth,jwt포함)/  # 블로그 (OAuth2 + JWT 풀스택)
│   ├── BlogEx6/
│   ├── CourseRegistration/     # 수강신청 시스템
│   ├── LibrarySite/            # 도서관 사이트
│   └── PostItMemo/             # 포스트잇 메모 앱
│
├── 📂 react/                   # React 학습 & 실전 프로젝트
│   ├── first-app/
│   ├── basic/
│   ├── ex-app1/, ex-app2/, exam-app/
│   └── 게시판/                 # Spring Boot 연동 게시판
│
├── 📂 java/                    # Java 기초 ~ 심화 연습 (60+ 예제)
├── 📂 javascript/              # JavaScript 연습 (80+ 예제)
├── 📂 html/                    # HTML 연습 (30+ 예제)
├── 📂 css/                     # CSS/SCSS 연습 (70+ 예제)
├── 📂 sql/                     # SQL 학습 (ch01~08 + 실데이터)
├── 📂 python_basic/            # Python 기초 연습
│
├── 📂 Netflix/                 # 넷플릭스 클론 프로젝트
├── 📂 파이썬-날씨분석/          # Flask + Open-Meteo 날씨 대시보드
└── 📂 간단한 로직 웹사이트/     # JavaScript 미니 앱 모음
```

---

<div align="center">

### 💡 "꾸준히 배우고, 직접 만들어보며 성장합니다."

[![GitHub](https://img.shields.io/badge/GitHub-KimTaehyeok01-181717?style=for-the-badge&logo=github)](https://github.com/KimTaehyeok01)

</div>
