//Ex.jsx
//연습문제1: 마우스 오버와 마우스 아웃 이벤트
// 목표: onMouseEnter와 onMouseLeave 이벤트를 사용하여
//  마우스가 특정 영역에 들어오거나 나갈 때 상태를 변경하는 방법을 학습합니다.
// 요구사항: 다음 조건에 맞는 컴포넌트를 작성하세요.
// 마우스가 박스 위에 올라가면 DIV 배경색이 변경됩니다.
// 마우스가 박스를 벗어나면 원래 배경색으로 돌아옵니다.
// 상태로 관리되는 메시지를 화면에 출력하세요.
//    (예: "마우스가 들어왔습니다", "마우스가 나갔습니다")

import { useRef, useState } from "react";

export function Event1() {
  const [message, setMessage] = useState("박스에 마우스를 올려보세요.");
  const [bgColor, setBgColor] = useState("blue");

  const handleEnter = () => {
    setMessage("마우스가 들어왔습니다");
    setBgColor("skyblue");
  };

  const handleLeave = () => {
    setMessage("마우스가 나갔습니다");
    setBgColor("blue");
  };

  const style = {
    marginTop: "20px",
    color: "white",
    width: "400px",
    height: "180px",
    lineHeight: "180px",
    backgroundColor: bgColor,
    margin: "80px auto",
    borderRadius: "5px",
    fontWeight: "bold",
    cursor: "pointer",
    textAlign: "center",
  };

  return (
    <div style={style} onMouseEnter={handleEnter} onMouseLeave={handleLeave}>
      {message}
    </div>
  );
}

//연습문제2: 폼 제출 이벤트 처리하기
// 목표: onSubmit 이벤트를 통해 폼 제출을 처리하고 기본 동작을 방지하는 방법
//요구사항 - 다음 조건에 맞는 컴포넌트를 작성하세요.
// 1.사용자 이름과 나이를 입력하는 폼을 만드세요.
// 2.폼이 제출되면 입력값을 콘솔에 출력하고 입력 필드를 비웁니다.
// 3.기본 폼 제출 동작을 방지하세요 (e.preventDefault() 사용).

export const Event2 = () => {
  const [age, setAge] = useState("");
  const [name, setName] = useState("");
  const style = { textAlign: "center", marginTop: "50px" };

  const handleClick = (e) => {
    console.log(`이름은 ${name}, 나이는 ${age}`);
    alert(`이름은 ${name} 나이는 ${age}`);
    e.preventDefault();
  };

  return (
    <div style={style}>
      <hr />
      <h1>폼 제출 예제</h1>
      <form action="https://myserver.com">
        <input
          style={{ width: "200px", height: "30px" }}
          type="text"
          value={name}
          placeholder="이름을 입력하시오."
          onChange={(e) => setName(e.target.value)}
        />
        <br />
        <input
          style={{ marginTop: "10px", width: "200px", height: "30px" }}
          type="text"
          value={age}
          placeholder="나이를 입력하시오."
          onChange={(e) => setAge(e.target.value)}
        />
        <br />
        <button
          style={{
            margin: "20px",
            width: "200px",
            height: "30px",
            borderRadius: "5px",
            border: "none",
            border: "1px solid black",
            backgroundColor: "skyblue",
            cursor: "pointer",
          }}
          type="submit"
          onClick={handleClick}
        >
          클릭
        </button>
      </form>
    </div>
  );
};

// 연습문제 3: 입력 필드에서 글자 수 제한하기
// 목표: 입력 필드의 입력값을 상태로 관리하고 글자 수 제한하는 방법을 학습합니다.
// 요구사항: 다음 조건에 맞는 컴포넌트를 작성하세요.
// 1.사용자가 텍스트를 입력할 수 있는 입력 필드가 있습니다.
// 2.입력값은 최대 10자까지만 허용됩니다.
// 3.입력값의 길이에 따라 남은 글자 수를 화면에 표시하세요.

export const Event3 = () => {
  const [input, setInput] = useState("");
  const style = { textAlign: "center", marginTop: "50px" };
  const MaxLength = 10;

  const handleChange = (e) => {
    if (e.target.value.length <= MaxLength) {
      setInput(e.target.value);
    }
  };

  return (
    <div style={style}>
      <hr />
      <h1>남은 글자수 세기 예제</h1>
      <form action="https://myserver.com">
        <input
          style={{ width: "200px", height: "30px" }}
          type="text"
          value={input}
          onChange={handleChange}
          placeholder="최대 10자 입력 가능"
        />
      </form>
      <h2>남은 글자 수 : {MaxLength - input.length}</h2>
    </div>
  );
};
