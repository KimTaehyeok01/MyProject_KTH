//import React from "react";
// React.js 8이상에서는 import 할 필요가 없음
class Greeting extends React.Component {
  render() {
    return <h1 className="greeting">Hello World!</h1>;
  }
}

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(React.createElement(Greeting));
