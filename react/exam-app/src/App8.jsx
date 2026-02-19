// 리액트 8이후로 생략가능. React함수 직접 사용시에만 필요.
import React from "react";
import * as E from "./8-event/Ex";

function App() {
  return (
    <>
      <E.Event1 />
      <E.Event2 />
      <E.Event3 />
    </>
  );
}
export default App;
