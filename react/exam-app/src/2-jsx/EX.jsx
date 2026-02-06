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
        if (props.price === 1000)
          return (
            <div>
              <span>음료의 이름은 </span>콜라
            </div>
          );
        else if (props.price === 2000)
          return (
            <div>
              <span>음료의 이름은 </span>사이다
            </div>
          );
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

// 문제: ProductCard 컴포넌트를 만드세요.
//
// props로 받을 데이터:
// - name: 상품명 (string)
// - price: 가격 (number)
// - stock: 재고 수량 (number)
//
// 요구사항:
// 1. 상품명을 <h2> 태그로 표시
// 2. 가격을 <p> 태그로 표시하고 뒤에 "원"을 붙이기
// 3. 재고 상태 표시:
//    - stock이 0이면 "품절"
//    - stock이 5개 이하면 "재고 부족 (남은 수량: {stock}개)"
//    - stock이 5개 초과면 "구매 가능"
export const ProductCard = (props) => {
  return (
    <>
      {(function () {
        if (props.stock === 0) {
          return (
            <h3>
              {props.name}의 가격은 {props.price}원 입니다. 남은 재고는{" "}
              {props.stock}개 입니다. 품절되었습니다.
            </h3>
          );
        } else if (props.stock <= 5) {
          return (
            <h3>
              {props.name}의 가격은 {props.price}원 입니다. 남은 재고는{" "}
              {props.stock}개 입니다. 재고 부족입니다.
            </h3>
          );
        } else {
          return (
            <h3>
              {props.name}의 가격은 {props.price}원 입니다. 남은 재고는{" "}
              {props.stock}개 입니다. 구매 가능하십니다.
            </h3>
          );
        }
      })()}
    </>
  );
};

// 문제: UserProfile 컴포넌트를 만드세요.
//
// props로 받을 데이터:
// - username: 사용자 이름 (string)
// - age: 나이 (number)
// - isOnline: 온라인 상태 (boolean)
// - role: 역할 (string: "admin", "user", "guest")
//
// 요구사항:
// 1. 사용자 이름을 <h2> 태그로 표시
// 2. 나이 표시 (예: "나이: 25세")
// 3. 온라인 상태 표시:
//    - isOnline이 true면 "온라인"
//    - isOnline이 false면 "오프라인"
// 4. 역할에 따른 뱃지 표시:
//    - "admin" → "관리자"
//    - "user" → "일반 회원"
//    - "guest" → "게스트"
//

export const UserProfile = (props) => {
  const roleMap = {
    admin: "관리자",
    user: "일반 회원",
    guest: "게스트",
  };

  return (
    <>
      <h2>{props.username}</h2>
      <p>나이: {props.age}세</p>
      <p>{props.isOnline ? "온라인" : "오프라인"}</p>
      <p>{roleMap[props.role]}</p>
    </>
  );
};
