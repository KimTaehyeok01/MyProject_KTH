// 리액트 8이후로 생략가능. React함수 직접 사용시에만 필요.
import React from "react";

import * as E from "./5-state/State";

function App() {
  return (
    <>
      {/* <E.Counter /> */}
      {/* <E.LikeButton /> */}
      <E.TextMirror />
    </>
  );
}
export default App;
