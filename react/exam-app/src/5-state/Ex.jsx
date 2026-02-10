// 연습문제 1: 버튼을 클릭할 때마다 색상 변경하기
// 설명: 버튼을 클릭할 때마다 배경 색상이 빨강, 초록, 파랑으로
//    순차적으로 변경되도록 만드세요.
import { useState } from "react";
export const ColorButton = () => {
  const [color, setColor] = useState("red");
  const colors = ["red", "green", "blue"];

  const currentIndex = colors.indexOf(color);

  function handleColorChange() {
    const nextIndex = (currentIndex + 1) % colors.length;
    setColor(colors[nextIndex]);
  }

  return (
    <div className="card" style={{ backgroundColor: color }}>
      <div>
        <button className="red" onClick={handleColorChange}>
          색상변경
        </button>
      </div>
      <p style={{ color: "white" }}>현재 색상: {color}</p>
    </div>
  );
};

// 연습문제 2: 체크박스 상태 관리하기
// 설명: 체크박스를 클릭하면 "ON" 또는 "OFF"라는 텍스트가
//   화면에 표시되도록 만드세요.
// 힌트: onChange, checked 속성을 이용

export const CheckBox = () => {
  const [on, setOn] = useState(false);
  function toggle() {
    setOn(!on);
  }
  return (
    <>
      <br />
      <input type="checkbox" onClick={toggle} />
      <p>{on ? "on" : "off"}</p>
      <br />
    </>
  );
};

// 연습문제 3: 숫자 제한 걸기
// 설명: 숫자를 증가시키되, 숫자가 10 이상이면
//   더 이상 증가하지 않도록 제한하세요.

export const NumberCount = () => {
  const [count, setCount] = useState(0);

  function numCount() {
    setCount(count + 1);
  }

  return (
    <>
      <p>현재 숫자: {count}</p>
      <button onClick={numCount}>증가</button>
      <p>{count >= 10 ? "최대 숫자의 도달했습니다." : ""}</p>
      <br />
      <br />
    </>
  );
};

// 연습문제 4: 버튼을 클릭할 때마다 리스트에 항목 추가하기
// 설명: 버튼을 클릭하면 입력 필드의 값을 리스트에 추가하고,
//   추가된 항목들을 화면에 표시하세요.
// 힌트: [], ["aaa", "bbb", "ccc"]

export const ListAdd = () => {
  const [text, setText] = useState("");
  const [list, setList] = useState([]);

  function textInput(e) {
    setText(e.target.value);
  }
  function listAdd() {
    setList([...list, text]);
    setText("");
  }

  return (
    <>
      <input type="text" value={text} onChange={textInput} />
      <button onClick={listAdd}>항목 추가</button>
      <p>추가된 항목 : {list}</p>
    </>
  );
};
