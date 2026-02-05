import React from "react";

const Book = (props) => {
  return (
    <>
      <h3>책의 이름은 {props.name}</h3>
      <h3>책의 가격은 {props.price}</h3>
    </>
  );
};

export default Book;
