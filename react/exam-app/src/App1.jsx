// import { element2 } from "./1-element/Element";
// import { element1, element2, element3, element4 } from "./1-element/Element";

// 와일드카드 임포드 방식
import * as E from "./1-element/Element";
// import {
//   element1,
//   Element2,
//   FoodList,
//   element3,
//   Element4,
// } from "./1-element/Element";

function App() {
  //리액트 엘리먼트
  // 리액트 엘리먼트는 => {element} 중괄호로 렌더링하고,
  // 리액트 컴포넌트(함수형, 클래스형) =>  <Element/>
  return (
    <>
      {E.element1}
      {E.element2}
      {E.element3}
      {E.element4}
      {E.element5}
      <E.Hello />
    </>
  );
}
export default App;
