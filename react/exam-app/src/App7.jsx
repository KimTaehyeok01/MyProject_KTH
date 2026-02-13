// 리액트 8이후로 생략가능. React함수 직접 사용시에만 필요.
import React from "react";
import * as E from "./7-hooks/Hooks";

function App() {
  return (
    <>
      {/* <E.CounterMemo /> */}
      {/* <E.ConterCallback /> */}
      {/* <E.ConuterCallback2 /> */}
      {/* <E.CounterRef /> */}
      <E.CounterRefInput />
    </>
  );
}
export default App;
