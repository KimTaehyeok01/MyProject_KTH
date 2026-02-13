import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";
// 계산기 프로그램 만들기

function App() {
  const [inputNumber1, setInputNumber1] = useState(0);
  const [inputNumber2, setInputNumber2] = useState(0);
  const [input, setInput] = useState(0);

  const handleAdd = () => {
    setInput(inputNumber1 + inputNumber2);
  };

  const handlesubStr = () => {
    setInput(inputNumber1 - inputNumber2);
  };

  const handleMa = () => {
    setInput(inputNumber1 * inputNumber2);
  };

  const handleMu = () => {
    setInput(inputNumber1 / inputNumber2);
  };

  const handleDelete = () => {
    setInputNumber1("");
    setInputNumber2("");
    setInput("");
  };

  return (
    <div className="card">
      <h1 className="card-title">React App</h1>
      <p className="clac-title">계산기 프로그램을 작성해보자</p>

      <div className="input-number1">
        <p className="num1">숫자 1</p>
        <input
          type="number"
          id="input-num1"
          value={inputNumber1 || ""}
          onChange={(e) => setInputNumber1(Number(e.target.value))}
        />
      </div>

      <div className="input-number2">
        <p className="num2">숫자 2</p>
        <input
          type="number"
          id="input-num2"
          value={inputNumber2 || ""}
          onChange={(e) => setInputNumber2(Number(e.target.value))}
        />
      </div>

      <div className="input-result">
        <p className="num-result">연산 결과 </p>
        <input
          type="number"
          id="input-num-result"
          value={input || ""}
          disabled
        />
      </div>

      <div className="btn-clac">
        <button type="button" className="btn" onClick={handleAdd}>
          덧셈
        </button>
        <button type="button" className="btn" onClick={handlesubStr}>
          뺄셈
        </button>
        <button type="button" className="btn" onClick={handleMa}>
          곱셈
        </button>
        <button type="button" className="btn" onClick={handleMu}>
          나눗셈
        </button>

        <button type="button" className="delete" onClick={handleDelete}>
          지우기
        </button>
      </div>
    </div>
  );
}

export default App;
