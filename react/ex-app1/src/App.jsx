import "./App.css";
import { useState } from "react";
function App() {
  // return <E.ScreenButton />;
  const [Color, setColor] = useState("red");

  return (
    <div className="card" style={{ backgroundColor: Color }}>
      <button className="red" onClick={() => setColor("red")}>
        빨강
      </button>
      <button className="blue" onClick={() => setColor("#1eb9e4")}>
        파랑
      </button>
      <button className="black" onClick={() => setColor("black")}>
        검정
      </button>
    </div>
  );
}

export default App;
