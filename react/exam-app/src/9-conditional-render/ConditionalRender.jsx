// 조건부 렌더링
// 특정조건에 따라 컴퍼넌트가 엘리먼트를 화면에 렌더링하는 기법.
// 자바스크립트의 조건문(예: if-else문), 삼항연산자, 논리연산자 && ||을 활용하여
//   동적으로 UI를 제어할 수 있음.

import React, { useState } from "react";

function Greeting({ isLoggedIn }) {
  // 1. if문 사용
  if (isLoggedIn == "true") {
    return <h3>환영합니다!</h3>;
  } else {
    return <h3>로그인이 필요합니다.</h3>;
  }
}

export function Conditional1(props) {
  return <Greeting isLoggedIn={props.isLoggedIn} />;
}

const styles = {
  wrapper: {
    padding: "16px",
    display: "flex",
    flexDirection: "row",
    borderBottom: "1px solid gray",
  },
  greeting: {
    marginRight: 8,
    color: "green",
  },
};

function Toolbar(props) {
  const { isLoggedIn, onClickLogin, onClickLogout, userName } = props;

  return (
    <div style={styles.wrapper}>
      {/* 2. 논리연산자 && */}
      {isLoggedIn && <span style={styles.greeting}>환영합니다.</span>}

      {/* 3. 삼항연산자 */}
      {isLoggedIn ? (
        <button onClick={onClickLogout}>로그아웃</button>
      ) : (
        <button onClick={onClickLogin}>로그인</button>
      )}
      {/* 4. 논리연산자 || - 기본값 설정 */}
      <span style={{ marginRight: 8 }}>{userName || "방문자"}</span>
    </div>
  );
}

export function LandingPage() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const handleClickLogin = () => {
    setIsLoggedIn(true);
  };
  const handleClickLogout = () => {
    setIsLoggedIn(false);
  };

  return (
    <div>
      <Toolbar
        isLoggedIn={isLoggedIn}
        onClickLogin={handleClickLogin}
        onClickLogout={handleClickLogout}
      />
      <div style={{ padding: 16 }}>렌딩 페이지입니다.</div>
    </div>
  );
}
