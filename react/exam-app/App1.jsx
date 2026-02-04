//import { element2 } from "./1-element/Element";
import { element1, element2 } from "./1-element/Element";
//와일드카드 임포트 방식
import * as E from "./1-element/Ex.jsx";
// import { Hello as H1 } from "./1-element/Element";
//export default인 경우
// import HelloH1 from "./1-element/Element";

function App() {
  //리액트 엘리먼트
  //return element2;
  // 리액트 엘리먼트는 => { element1 } 중괄호로 렌더링하고,
  // 리액트 컴퍼넌트(함수형,클래스형) => <Element1 /> 태그형식으로 렌더링
  return (
    <>
      {/* {E.element1}
      {element1}
      {element2}
      {E.element3}
      {E.element4}
      {E.element5} */}
      {/* <E.Hello name="홍길동" />
      <E.ConfirmDialog /> */}
      <E.Namecard />
      <E.Greeting name="김태혁" age="26" />
      <E.DigitalDevices />
      <E.ProductList products={[]} />
    </>
  );
}
export default App;
