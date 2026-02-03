// .jsx : JavaScript XML의 약자
//      : 주로 React(리액트) 라이브러리에서 UI를 정의할 때
//         사용하는 자바스크립트의 확장 문법

// 리액트 엘리먼트(React Element)란?
// 리액트 엘리먼트는 리액트 앱의 UI를 구성하는 가장 작은 단위입니다.
// 엘리먼트는 화면에 표시될 내용을 설명하는 자바스크립트 객체로,
// 컴포넌트를 생성하고 렌더링하는 데 사용됩니다.

// 리액트 엘리먼트는 **불변(immutable)**이며 한 번 생성되면
//  그 내용을 변경할 수 없습니다.
//  대신 새로운 엘리먼트를 생성하여 화면의 변화를 반영합니다.

// 리액트 엘리먼트의 특징
// 1. 불변성: 엘리먼트는 한 번 생성되면 변경할 수 없습니다.
// 2. 가볍고 빠름: 가상 DOM을 사용하여 변경 사항을 효율적으로 관리합니다.
// 3. UI의 구성 단위: 컴포넌트 내에서 리액트 엘리먼트는 HTML 태그처럼 사용됩니다.
import React from "react";

const element1 = <h1 className="greeting">안녕하세요! e1</h1>;
//빌드 도구(Babel 등)에 의해 최종적으로 변환된 모습
const element2 = React.createElement("h1", null, "안녕하세요! e2");
//React.createElement(type, props, [children])

// JSX에서 style적용한 예제
export const element3 = <h1 style={{ color: "blue" }}>엘리먼트3</h1>;

// JSX에서 목록(리스트)
const fruits = ["사과", "바나나", "포도"];
export const element4 = (
  <ul>
    {fruits.map((item, index) => {
      // JSX안에서 {JS값 표현식} 형식으로 값을 표현한다.
      // 리액트는 리스트에 유일무이한 key를 줘야 한다.
      return <li key={index}>{item}</li>;
    })}
  </ul>
);

// 영미권 개발자들은 이벤트처리 함수에 handle(on)이란 명칭을 주로 한다.
function handleClick() {
  alert("버튼이 클릭되었습니다.");
}

// 리액트는 엘리먼트를 반환할 때, 최상위 태그 하나만 반환해야함.
// <></> 이것을 리액트 프레그먼트라고 함.
export const element5 = (
  <>
    <button onClick={handleClick}>버튼</button>
  </>
);

// props로 컴퍼넌트에 값(코드) 전달하기
// 함수형 컴퍼넌트 반환
// return 뒤에 엘리먼트 뒤에 반환시에 소괄호가 있어야 되는가?
// 1. 소괄호가 있는 경우 : 함수형 컴퍼넌트로 리턴하는 경우, 여러 요소로 return하는 경우(최상위 태그 요소로 만들어서)
// 2. 소괄호가 없는 경우 : return뒤에 바로 JSX나 값이 올 경우
export function Hello(props) {
  return <h1>안녕하세요. {props.name}님</h1>;
}

//export default element1; // 대표하는 모듈(컴퍼넌트,엘리먼트) 하나만 내보냄.
export { element1, element2 }; //여러개 내보낼 때는 { } 중괄호
