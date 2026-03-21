# 블로그 프로젝트

## 구조
```
blog-project/
├── blog-backend/   → 스프링 부트 서버 (포트 8080)
└── blog-frontend/  → 리액트 클라이언트 (포트 3000)
```

## 시작 방법

### 1. MySQL 데이터베이스 생성
```sql
CREATE DATABASE blog;
```

### 2. 백엔드 설정
- `blog-backend/.env` 파일에 값 채우기
```
DB_USERNAME=root
DB_PASSWORD=본인비밀번호
JWT_SECRET_KEY=blogsecretkey1234567890blogsecretkey1234567890
GOOGLE_CLIENT_ID=구글클라이언트ID
GOOGLE_CLIENT_SECRET=구글클라이언트시크릿
```
- IntelliJ에서 .env 환경변수 연결
- `blog-backend` 실행

### 3. 프론트엔드 시작
```bash
cd blog-frontend
npm install
npm start
```

## API 목록
| Method | URL | 설명 | 인증 |
|--------|-----|------|------|
| GET | /api/posts | 전체 목록 | 불필요 |
| GET | /api/posts/{id} | 단건 조회 | 불필요 |
| GET | /api/posts/search?keyword= | 검색 | 불필요 |
| POST | /api/posts | 글쓰기 | 필요 |
| PUT | /api/posts/{id} | 수정 | 필요 |
| DELETE | /api/posts/{id} | 삭제 | 필요 |
| POST | /api/posts/{id}/comments | 댓글 작성 | 필요 |
| DELETE | /api/posts/comments/{id} | 댓글 삭제 | 필요 |
| GET | /api/auth/me | 내 정보 | 필요 |
| GET | /oauth2/authorization/google | 구글 로그인 | 불필요 |
