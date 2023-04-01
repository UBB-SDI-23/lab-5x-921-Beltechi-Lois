import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";
import { PassengerShowAll } from "./components/passengers/PassengerShowAll";
import React from "react";

function App() {
  const [count, setCount] = useState(0);

  return (
    <React.Fragment>
      <PassengerShowAll />

      <div className="App">
        <h1>Vite + React</h1>
        <div className="card">
          <button onClick={() => setCount((count) => count + 1)}>
            count is {count}
          </button>
          <p>
            Edit <code>src/App.tsx</code> and save to test HMR
          </p>
        </div>
        <p className="read-the-docs">
          Click on the Vite and React logos to learn more
        </p>
      </div>
    </React.Fragment>
  );
}

export default App;
