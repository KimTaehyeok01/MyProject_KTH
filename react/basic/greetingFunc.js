// 생성자함수이므로, 첫 글자는 대문자로 한다.
function Greeting() {
  return <h1 className="greeting">Hello World!</h1>;
}

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(Greeting));
