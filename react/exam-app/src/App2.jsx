// import * as E from "./2-jsx/JSX";
import Library from "./2-jsx/library";
import * as E from "./2-jsx/EX.jsx";

function App() {
  //리액트 엘리먼트
  //return element2;
  // 리액트 엘리먼트는 => { element1 } 중괄호로 렌더링하고,
  // 리액트 컴퍼넌트(함수형,클래스형) => <Element1 /> 태그형식으로 렌더링
  // return E.E1;
  // return E.E2;
  // return E.E3;
  // return E.E4;
  // return E.E5;
  // return E.E6;
  // 리액트 엘리먼트는 바로 리턴 가능
  // 단 JSX 태그안에서는 {element} 형식으로 반환.
  // return <Library />;

  return (
    <>
      <E.Hello name="홍길동" />
      <E.calc num1={10} num2={20} />
      <E.DrinkMachine price={2000} />
      <E.Greeting hour={21} />
    </>
  );
}
export default App;
