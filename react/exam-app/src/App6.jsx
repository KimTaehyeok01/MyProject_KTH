// 리액트 8이후로 생략가능. React함수 직접 사용시에만 필요.
import React from "react";
import * as E from "./6-effect/Ex";

function App() {
  return (
    <>
      {/* <E.LifeCycleClass /> */}
      {/* <E.LifeCycleFunc /> */}
      {/* <E.LifeCycle /> */}
      {/* <E.DataFetchJS /> */}
      {/* <E.DataFetchAxios /> */}
      {/* <E.WindowSizeTracker /> */}
      <E.Timer />
    </>
  );
}
export default App;
