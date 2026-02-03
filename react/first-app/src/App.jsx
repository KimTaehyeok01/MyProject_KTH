import React from "react";
import "./App.css";

// 실제 앱 함수형 컴퍼넌트
function App() {
  const [isClicked, isSetClicked] = React.useState(false);

  return (
    <button onClick={() => isSetClicked(true)}>
      {isClicked ? "클릭완료" : "클릭하세요"}
    </button>
  );
}

// App 컴퍼넌트를 밖에서 import하도록 해쥬는 코드
export default App;
