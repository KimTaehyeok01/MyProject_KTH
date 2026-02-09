// Ex.jsx
// 1. 문제 1: 단일 Props 전달하기
//   목표: 단일 `props`를 활용하여 간단한 문구를 출력하는 컴포넌트를 작성하세요.
// 요구사항:
// - `Greeting`이라는 자식 컴포넌트를 만드세요.
// - `name`이라는 `props`를 받아 "환영합니다, [이름]님!"이라는 문구를 렌더링합니다.
// - 부모 컴포넌트에서 여러 사람의 이름을 넘겨 출력합니다.
// 출력예:
//    환영합니다, 홍길동님!
//    환영합니다, 김민수님!
//    환영합니다, 이영희님!

const Greeting = (props) => {
  return <h1>환영합니다. {props.name}님!</h1>;
};
export const Ex1 = () => {
  return (
    <div>
      <Greeting name="홍길동" />
      <Greeting name="김민수" />
      <Greeting name="이영희" />
    </div>
  );
};
// 2. 문제 2: 여러 Props 전달하기
// 목표: 여러 개의 `props`를 활용하여 사용자의 정보를 출력하는 컴포넌트를 작성하세요.
// 요구사항:
// - `UserCard`라는 자식 컴포넌트를 작성하세요.
// - `name`, `age`, `job`을 `props`로 받아 사용자 정보를 표시합니다.
// - 부모 컴포넌트에서 두 명의 사용자 정보를 전달해 렌더링합니다.

const UserCard = ({ name, age, job }) => {
  return (
    <>
      <h3>이름 : {name}</h3>
      <h3>나이 : {age}</h3>
      <h3>직업 : {job}</h3>
    </>
  );
};

export const Ex2 = () => {
  const user1 = { name: "전우치", age: 487, job: "도사" };
  const user2 = { name: "임꺽정", age: 390, job: "도적" };

  return (
    <>
      <UserCard {...user1} />
      <UserCard {...user2} />
    </>
  );
};

// 3. 문제 3: 배열 Props 전달하기
// 목표: 배열 데이터를 `props`로 전달하여 리스트 형태로 출력하는 컴포넌트를 작성하세요.
// 요구사항:
// - `ItemList`라는 자식 컴포넌트를 작성하세요.
// - `items`라는 배열을 `props`로 받아 `<li>` 태그로 각 항목을 렌더링합니다.
// - 부모 컴포넌트에서 두 개의 다른 배열을 전달해 두 개의 목록을 출력합니다.
// 출력예)
//  ItemList1
//  사과
//  바나나
//  딸기
//  ItemList2
//  축구
//  농구
//  야구

const ItemList = ({ title, items }) => {
  return (
    <div>
      <h3>{title}</h3>
      <ul>
        {items.map((item, index) => (
          <li key={index}>{item}</li>
        ))}
      </ul>
    </div>
  );
};

export const Ex3 = () => {
  return (
    <>
      <ItemList title="과일종류" items={["사과", "바나나", "딸기"]} />
      <ItemList title="체육종목" items={["축구", "농구", "야구"]} />
    </>
  );
};
