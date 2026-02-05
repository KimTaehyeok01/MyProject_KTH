import React from "react";
import Book from "./Book"; // .jsx생략 가능

function Library() {
  return (
    <>
      <Book name="리액트 기초" price={3000} />
      <Book name="Node.js 기초" price={4000} />
      <Book name="스프링 기초" price={5000} />
    </>
  );
}

// 오직 하나만 export할 때 default를 함.
export default Library;
