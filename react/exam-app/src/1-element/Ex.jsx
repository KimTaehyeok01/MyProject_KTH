// 연습문제1: 동적 표현식 렌더링
// 목표: props로 받은 이름을 화면에 출력하는 간단한 컴포넌트 연습
// 요구사항
// 1. Namecard 함수형 컴포넌트를 만들고, user 객체의 정보(이름,나이)를 JSX에 넣어서
//   표현한다.
// 2. props는 사용안함.

const user = {
  name: "김태혁",
  age: 26,
};

export const UserInfo = () => {
  return (
    <h1>
      <span>{user.name}</span>
      <span>{user.age}</span>
    </h1>
  );
};

//연습문제2: props를 이용해 이름과 나이 출력하기
// 목표: props로 전달받은 이름과 나이를 사용자 정의 컴퍼넌트에 출력한다.
// 요구사항
// 1. Greeting 함수형 컴퍼넌트를 만들고, props로 전달받은 name과 age를
//   출력한다.
// 2. 출력양식 : 안녕하세요, 홍길동님! 당신의 나이는 25살입니다.

export const Greeting = (props) => {
  return (
    <>
      <p>
        안녕하세요, {props.name}님! 당신의 나이는 {props.age}살입니다.
      </p>
    </>
  );
};

//연습문제3: 다음의 객체배열의 데이터를 출력하시오.
//목표: props로 전달받은 객체 배열의 데이터를 조작할 수 있다.
//요구사항
// 1. 상품이름 - 가격(원)으로 출력한다.
// 출력 예) 1. 노트북 - 8000원
//          2. 스마트폰 - 4000원

const lectronicDevices = [
  { name: "노트북", price: 8000 },
  { name: "스마트폰", price: 4000 },
];

export const Digital = () => {
  return (
    <>
      <ul>
        {lectronicDevices.map((item, index) => {
          return (
            <li key={index}>
              {index + 1}. {item.name} - {item.price}원
            </li>
          );
        })}
      </ul>
    </>
  );
};
