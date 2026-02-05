import React from "react";
// Ex.jsx
// 연습문제1: 간단한 인사말 출력하기
// 목표: props로 받은 이름을 화면에 출력하는 간단한 컴포넌트 연습
// 요구사항
// 1. Hello 컴포넌트를 만들고, name이라는 props로 받은 값을 화면에
// "안녕하세요, [name]님!"이라고 출력하세요.

export const Hello = (props) => {
  return <h1>안녕하세요, {props.name}님!</h1>;
};

// 연습문제2: 간단한 계산기
// 목표: 두 개의 숫자(props)를 받아 합을 계산하여
// 화면에 출력하는 컴포넌트
// 요구사항
// 1. num1과 num2라는 props를 받아 두 값을 더한 결과를
// 출력하는 Sum 컴포넌트를 만들어 보세요.

export const calc = (props) => {
  let sum = 0;
  return (
    <>
      <h2>
        {props.num1} + {props.num2} = {(sum = props.num1 + props.num2)}입니다.
      </h2>
    </>
  );
};

// 연습문제3: 간단한 자동판매기
// 목표: 받은 금액에 따라 자동으로 음료를 결정하고
// 출력하는 컴포넌트 연습
// 요구사항
// 1. DrinkMachine 컴포넌트를 만들고, price라는
// props로 받은 금액에 따라 아래와 같이 출력하세요.
// 2. 1000원: "콜라"
// 3. 2000원: "사이다"
// 4. 그 외 금액: "물"

// 일반적인 답
// export const DrinkMachine = (props) => {
//   if (props.price === 1000) return <div>콜라</div>;
//   else if (props.price === 2000) return <div>사이다</div>;
//   else return <div>물</div>;
// };

// 즉시발동함수로 작성한 코드
export const DrinkMachine = (props) => {
  return (
    <h2>
      {(function () {
        if (props.price === 1000) return <div>콜라</div>;
        else if (props.price === 2000) return <div>사이다</div>;
        else return <div>물</div>;
      })()}
    </h2>
  );
};

// 연습문제4: 조건부 인사말 출력하기
// 목표: 시간에 따라 다른 인사말을 출력하는 컴포넌트를 작성합니다.
// 요구사항
// Greeting 컴포넌트를 만들고, hour라는 props로 받은 시간에 따라 아래와 같이 인사말을 출력하세요.
// 오전 (5시~11시): "좋은 아침입니다!"
// 오후 (12시~17시): "좋은 오후입니다!"
// 저녁/밤 (18시~4시): "좋은 저녁입니다!"

// 즉시발동함수로 작성한 코드
export const Greeting = (props) => {
  return (
    <h2>
      {(function () {
        if (props.hour >= 5 && props.hour <= 11) {
          return <div>좋은 아침입니다!</div>;
        } else if (props.hour >= 12 && props.hour <= 17) {
          return <div>좋은 오후입니다!</div>;
        } else if (
          (props.hour >= 18 && props.hour <= 24) ||
          (props.hour >= 1 && props.hour <= 4)
        ) {
          return <div>좋은 저녁입니다!</div>;
        } else return <div>규격 외 값입니다.</div>;
      })()}
    </h2>
  );
};
