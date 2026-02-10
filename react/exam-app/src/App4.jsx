// 리액트 8이후로 생략가능. React함수 직접 사용시에만 필요.
import React from "react";

import * as E from "./4-props/Ex";

function App() {
  // return <E.Props1 />;
  // return <E.Props2 />;
  // return <E.Props3 />;
  // return <E.Props4 />;
  // return <E.Props5 />;
  return (
    <>
      {/* <E.Ex1 />
      <E.Ex2 />
      <E.Ex3 /> */}
      <E.Ex4 />
      <E.Ex5 />
    </>
  );
}
export default App;
