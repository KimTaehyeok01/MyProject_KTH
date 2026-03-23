import "./App.css";
import { useState } from "react";

const API_BASE = "http://localhost:8080/api/users";

function App() {
  const [result, setResult] = useState("");

  // 회원가입
  function signup() {
    const data = new URLSearchParams();
    data.append("email", "test@naver.com");
    data.append("password", "1234");
    data.append("userRole", "ADMIN");

    fetch(`${API_BASE}/signup`, {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      body: data,
    })
      .then((response) => response.json())
      .then((json) => {
        console.log(json);
        // JSON.stringify : 객체를 문자열로 바꿔주는 함수
        setResult(JSON.stringify(json, null, 2));
      })
      .catch((err) => setResult("Error: " + err.message));
  }

  // 로그인
  function login() {
    // JS객체 + 직렬화(문자열 변환) 지원해주는 객체
    // 그냥 객체로 data를 만들면, object 타입으로 전송됨.
    //        => 직렬화 해주면 됨.
    // JS객체 + 직렬화(문자열변환) 지원해주는 객체
    // 그냥 객체로 data를 만들면, object타입으로 전송됨.
    //        => 직렬화 해주면 됨.
    //JSON.stringify({
    //   email: "test@naver.com",
    //   password: "1234"
    // })
    // URLSearchParams : JS 내장 클래스
    const data = new URLSearchParams();
    data.append("email", "test@naver.com");
    data.append("password", "1234");
    data.append("userRole", "ADMIN");

    fetch(`${API_BASE}/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      body: data,
    })
      .then((response) => response.text())
      .then((text) => {
        console.log(text);
        localStorage.setItem("JWT_TOKEN", text);
        setResult("JWT 토큰 저장 완료:\n" + text);
      })
      .catch((err) => setResult("Error: " + err.message));
  }

  // 마이페이지 (ROLE_USER)
  function mypage() {
    fetch(`${API_BASE}/mypage`, {
      method: "GET",
      headers: {
        JWT_TOKEN: localStorage.getItem("JWT_TOKEN"),
      },
    })
      .then((response) => response.json())
      .then((json) => {
        console.log(json);
        setResult(JSON.stringify(json, null, 2));
      })
      .catch((err) => setResult("Error: " + err.message));
  }

  // 관리자페이지 (ROLE_ADMIN)
  function admin() {
    fetch(`${API_BASE}/admin`, {
      method: "GET",
      headers: {
        JWT_TOKEN: localStorage.getItem("JWT_TOKEN"),
      },
    })
      .then((response) => response.json())
      .then((json) => {
        console.log(json);
        if (Array.isArray(json)) {
          setResult(JSON.stringify(json, null, 2));
        } else {
          setResult(JSON.stringify(json, null, 2));
        }
      })
      .catch((err) => setResult("Error: " + err.message));
  }

  return (
    <div className="container">
      <h3>JWT 토큰 테스트</h3>
      <p>1. Postman으로 api테스트한다.</p>
      <p>2. JS fetch함수로 테스트한다.</p>

      <div className="api-section">
        <div className="api-item">
          <span className="label">회원가입</span>
          <code>POST localhost:8080/api/users/signup</code>
          <button onClick={signup}>회원가입</button>
        </div>

        <div className="api-item">
          <span className="label">로그인</span>
          <code>POST localhost:8080/api/users/login</code>
          <button onClick={login}>로그인</button>
        </div>

        <div className="api-item">
          <span className="label">마이페이지</span>
          <code>GET localhost:8080/api/users/mypage</code>
          <button onClick={mypage}>마이페이지</button>
        </div>

        <div className="api-item">
          <span className="label">관리자 페이지</span>
          <code>GET localhost:8080/api/users/admin</code>
          <button onClick={admin}>관리자페이지</button>
        </div>
      </div>

      {result && (
        <div className="result">
          <h4>응답 결과</h4>
          <pre>{result}</pre>
        </div>
      )}
    </div>
  );
}

export default App;
