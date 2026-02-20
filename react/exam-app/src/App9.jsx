// 리액트 8이후로 생략가능. React함수 직접 사용시에만 필요.
import React from "react";
import * as E from "./9-conditional-render/Ex";

function App() {
  return (
    <>
      {/* <E.Conditional1 isLoggedIn="false" />
      <E.Conditional1 isLoggedIn="true" /> */}
      {/* <E.LandingPage /> */}
      <E.UserProfile judgement="true" />
      <E.UserProfile judgement="false" />
      <E.Notification count="5" />
      <E.Notification count="0" />
      <E.Advertisement isPremium={true} />
      <E.Advertisement adBox={false} />
    </>
  );
}
export default App;
