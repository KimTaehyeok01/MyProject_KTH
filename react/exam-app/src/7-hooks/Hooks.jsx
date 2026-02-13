// 리액트 훅(React Hooks)은 함수형 컴퍼넌트에서도
// 클래스 컴퍼는트의 기능(오버라이드 함수)들을 사용하도록 고안된 기능이다.
// useState, useEffect, useContext 등 다양한 훅이 제공된다.

import React, {
  useMemo,
  useCallback,
  useRef,
  useState,
  useEffect,
} from "react";

// useState, useEffect 훅으로만 간단한 웹앱을 만들 수 있다.

// useMemo 훅
// 특정 연산의 결과를 메모리제이션(저장)하여
// 불필요한 반복적 계산을 하지 않게 해줌.
// 컴포넌트가 렌더링 될 때마다 매번 계산하는 대신
// 의존성 배열에 명시된 상태 값이 변경될 때만 해당 연산을 다시 수행한다.
// 용도 :
//       1. 비용이 큰 연산을 최적화 할 때(통신, 큰 데이터 연산, ...)
//       2. 렌더링 성능을 개선할 때

// 사용자가 입력값의 2배를 계산하는 함수
export function CounterMemo() {
  // count가 바뀌면, useMemo가 호출됨.
  const [count, setCount] = useState(0);
  // inputValue가 바뀌면, useMemo는 호출되지 않음.
  const [inputValue, setInputValue] = useState("");

  // count 상태변수가 변경되지 않는 한
  // doubleValue기 재연산되지 않게 한다.
  const doubleValue = useMemo(() => {
    console.log("두 배 계산중...");
    return count * 2;
  }, [count]);

  return (
    <div>
      <h1>useMemo</h1>
      <h2>입력한 숫자 : {count}</h2>
      <h2>두 배 결과 : {doubleValue}</h2>
      <input
        type="number"
        value={count}
        onChange={(e) => {
          setCount(e.target.value);
        }}
      />
      <p>inputValue는 바뀌어도, useMemo는 호출 안됨.</p>
      <input
        type="text"
        value={inputValue}
        onChange={(e) => {
          setInputValue(e.target.value);
        }}
      />
    </div>
  );
}

// useCallback 훅
//      : Memoization된 콜백함수를 반환하여
//      : 함수가 불필요하게 새로 생성되는 것을 방지하는 훅이다.
//      : useMemo는 값을, useCallback은 함수를 반환한다.
// 용도 :
//       1. 컴퍼넌트가 렌더링될 때마다 동일한 함수를 다시 생성하는 것을 방지.
//       2. 자식 컴퍼넌트에 함수를 props로 전달할 때, 불필요한 재렌더링을 방지.

export const ConterCallback = () => {
  const [count, setCount] = useState(0);
  const [inputValue, setInputValue] = useState("");

  const doubleValue = useMemo(() => {
    console.log("두 배 계산중...");
    return count * 2;
  }, [count]);

  // JS의 이벤트함수를 함수형 변수로 바인딩했을 때,
  // 이벤트객체 e를 가져올 수 있다.
  const handleChange = useCallback((e) => {
    console.log("useCallback Memoization");
    setCount(e.target.value);
  }, []); // 의존성배열(Dependecy Array)이 빈 배열로 한다.
  //         count를 넣으면, count가 변경될 때마다 handleChange함수가 새로 생성된다.
  //         그래서 성능 개선을 위해 빈 배열이 좋다.

  return (
    <>
      <h1>useCallback</h1>
      <h2>입력한 숫자 : {count}</h2>
      <h2>두 배 결과 : {doubleValue}</h2>
      <input type="number" value={count} onChange={handleChange} />
    </>
  );
};

//useMemo() : 리액트 훅. 값을 메모리에 저장.
//React.memo() : 리액트 함수. 리액트 컴퍼넌트를 메모리에 저장하는 용도.
const Child = React.memo(({ func }) => {
  //1.부모의 상태변경시 재렌더링 안됨. useCallback + React.memo
  console.log("자식 컴퍼넌트 렌더링");
  return <button onClick={func}>자식 버튼</button>;
});

const ChildNormal = (func) => {
  //1.부모의 상태변경시 재렌더링 됨.
  console.log("자식 컴퍼넌트2 렌더링 ");
  return <div></div>;
};

export function ConuterCallback2() {
  //부모 컴퍼넌트가 재렌더링되는 상황
  //1. props가 변경
  //2. 상태변수 변경
  const [count, setCount] = useState(0);
  const [other, setOther] = useState(0);

  console.log("Update() 재렌더링");

  const handleClick = useCallback(() => {
    console.log("useCallback() 함수 실행!");
    setCount((prev) => {
      return prev + 1;
    });
  }, []);

  return (
    <div>
      <p>
        Count: {count} || Other :{other}
      </p>
      {/* 핵심 : 부모의 상태가 바뀌어도 자식이 재렌더링 안됨. */}
      <button
        onClick={() => {
          setOther((prev) => prev + 1);
        }}
      >
        부모만 렌더링 유발
      </button>
      <Child func={handleClick} />
      <ChildNormal />
    </div>
  );
}

// useRef 훅
// 개념 : useRef는 리액트에서 변경 가능한 참조 객체를 제공하는 훅이다.
//      : useRef로 생성한 객체는 컴포넌트가 리렌더링되더라도 값이 유지됨.
// 사용용도 :
//     1. DOM 요소에 접근하기 위해(예: 포커스, 스크롤 제어)
//     2. 상태값과 다르게 리렌더링 없이 값 유지가 필요한 경우
//         예) 이전 값 저장, 타이머 등
//     3. 성능 최적화에 유리함. 값이 변하더라도 불필요한 리렌더링을 방지함.
export function CounterRef() {
  const [count, setCount] = useState(0);
  let ClickCount = 0; // 일반 변수는 리렌더링 되면 초기화되어 버린다.
  const countRef = useRef(0);
  // countRef는 컴포넌트가 리렌더링될 때, 값이 초기화되지 않는다.
  // countRef는 값이 변경되더라도, 리렌더링을 발생시키지 않는다.
  // count 상태변수는 값이 변경되면, 즉시 화면을 갱신한다.

  const handleClick = () => {
    ClickCount++;
    countRef.current += 1;
    setCount((prev) => prev + 1);
  };

  console.log("리렌더링됨1.", count);
  console.log("리렌더링됨2.", ClickCount);
  console.log("리렌더링됨3.", countRef);

  return (
    <>
      <h2>Counter : {count}</h2>
      <h2>버튼 클릭 횟수 : {countRef.current}</h2>
      <button onClick={handleClick}>버튼</button>
    </>
  );
}

export function CounterRefInput() {
  const [count, setCount] = useState(0);
  const clickCountRef = useRef(0);
  const inputRef = useRef(null);

  const handleClick = () => {
    setCount((prev) => prev + 1);
  };

  return (
    <>
      <h2>Counter : {count}</h2>
      <h2>버튼 클릭 횟수 : {clickCountRef.current}</h2>
      <input type="text" ref={inputRef} placeholder="텍스트를 입력하세요." />
      <button onClick={handleClick}>증가 밎 입력창 포커스</button>
    </>
  );
}
