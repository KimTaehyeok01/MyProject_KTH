// 리액트 8이후로 생략가능. React함수 직접 사용시에만 필요.
import React from "react";
import * as E from "./5-state/Ex";

function App() {
  return (
    <>
      {/* <E.Counter /> */}
      {/* <E.LikeButton /> */}
      {/* <E.TextMirror /> */}
      {/* <E.UserForm /> */}
      <E.ColorButton />
      <E.CheckBox />
      <E.NumberCount />
      <E.ListAdd />
    </>
  );
}
export default App;
