// 리액트 8이후로 생략가능. React함수 직접 사용시에만 필요.
import React from "react";
import * as E from "./9-conditional-render/ConditionalRender";

function App() {
  return (
    <>
      {/* <E.Conditional1 isLoggedIn="false" />
      <E.Conditional1 isLoggedIn="true" /> */}
      <E.LandingPage />
    </>
  );
}
export default App;
