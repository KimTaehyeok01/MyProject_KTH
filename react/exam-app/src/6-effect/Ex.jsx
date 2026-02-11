import React, { Component, useState, useEffect } from "react";
// 서버와 JS(React)에서 통신해보자.
// 1. JS : fetch()함수
// 2. Axios : axios()함수
// 설치 : npm i(nstall) axios

//연습문제1: API 호출 및 데이터 로드
// 목표: 컴포넌트가 마운트될 때 API 호출을 통해 데이터를 가져와
//    화면에 표시하세요.
// 요구사항:
// 1. URL: https://jsonplaceholder.typicode.com/posts
// 2. 상위 10개의 포스트 테이터만 출력하세요.
// 3. useEffect를 사용하여 컴포넌트가 마운트될 때 데이터를 로드하세요.
// 4. 데이터를 로드한 후 상태에 저장하고 화면에 출력하세요.
// 힌트: fetch 또는 axios 모듈 사용 가능합니다.
// [
//   {
//     userId: 1,
//     id: 1,
//     title:
//       "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
//     body: "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto",
//   },
// ];

// JS의 fetch함수
// Axios의 axios함수
export function DataFetchJS() {
  const [data, setData] = useState([]);

  // async/await 구문 : JS에서 비동기적인 처리를 할 때 사용하는 예약어
  // promise/then 구문 : 구조화된 호출과 응답을 위해 처리하는 예약어
  const fetchData = async () => {
    try {
      const response = await fetch(
        "https://jsonplaceholder.typicode.com/posts",
      );

      // response : HTTP통신의 결과값(HTTP헤더 + 바디(data))
      // json() : JSON형태의 문자열을 json KV객체로 변환해주는 함수
      const result = await response.json();
      console.log(result.slice(0, 10));

      if (!response.ok) {
        throw new Error("네트워크 응답이 올바르지 않습니다.");
      }

      setData(result.slice(0, 10)); // 배열 0번째부터 10개만 가져오기
    } catch (error) {
      console.log("데이터 가져오기 실패", error);
    }
  };

  useEffect(() => {
    console.log("DidMount");
    fetchData(); // 마운트시에 한번만 호출
  }, []);

  return (
    <>
      <h1>통신으로 가져온 데이터</h1>
      <ul>
        {data.map((post) => (
          <li key={post.id}>{post.title}</li>
        ))}
      </ul>
    </>
  );
}
