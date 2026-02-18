import { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const [posts, setPosts] = useState([]);
  const [newPost, setNewPost] = useState({
    title: "",
    content: "",
    writer: "",
  });

  // 상세보기 및 수정을 위한 상태
  const [selectedPost, setSelectedPost] = useState(null);
  const [isEdit, setIsEdit] = useState(false);

  // 1. 목록 불러오기 (Read)
  const fetchPosts = () => {
    axios
      .get("http://localhost:8080/api/posts")
      .then((res) => setPosts(res.data))
      .catch((err) => console.error("데이터 로드 실패:", err));
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  // 2. 글 저장 (Create)
  const handleSave = () => {
    if (!newPost.title || !newPost.writer) {
      alert("제목과 작성자를 입력해주세요!");
      return;
    }
    axios
      .post("http://localhost:8080/api/posts", newPost)
      .then(() => {
        alert("글이 등록되었습니다!");
        setNewPost({ title: "", content: "", writer: "" });
        fetchPosts();
      })
      .catch((err) => console.error("저장 실패:", err));
  };

  // 3. 상세 보기 & 조회수 증가 (Read + Update)
  const handleDetail = (id) => {
    axios
      .get(`http://localhost:8080/api/posts/${id}`)
      .then((res) => {
        setSelectedPost(res.data);
        setIsEdit(false); // 상세창 열 때 수정 모드 초기화
        fetchPosts(); // 목록의 조회수 갱신
      })
      .catch((err) => console.error("상세보기 실패:", err));
  };

  // 4. 글 수정 (Update)
  const handleUpdate = (id) => {
    axios
      .put(`http://localhost:8080/api/posts/${id}`, selectedPost)
      .then(() => {
        alert("수정되었습니다!");
        setIsEdit(false);
        setSelectedPost(null);
        fetchPosts();
      })
      .catch((err) => console.error("수정 실패:", err));
  };

  // 5. 글 삭제 (Delete)
  const handleDelete = (e, id) => {
    e.stopPropagation(); // 행 클릭 이벤트(상세보기) 전파 방지
    if (window.confirm("정말 삭제하시겠습니까?")) {
      axios
        .delete(`http://localhost:8080/api/posts/${id}`)
        .then(() => {
          alert("삭제되었습니다.");
          fetchPosts();
        })
        .catch((err) => console.error("삭제 실패:", err));
    }
  };

  return (
    <div className="board-container">
      <h2>전공자 태혁의 풀스택 게시판</h2>

      {/* 폼: 글쓰기 영역 */}
      <div
        className="write-form"
        style={{
          marginBottom: "30px",
          border: "1px solid #ccc",
          padding: "15px",
        }}
      >
        <h4>새 글 쓰기</h4>
        <input
          type="text"
          placeholder="제목"
          value={newPost.title}
          onChange={(e) => setNewPost({ ...newPost, title: e.target.value })}
        />
        <input
          type="text"
          placeholder="작성자"
          value={newPost.writer}
          onChange={(e) => setNewPost({ ...newPost, writer: e.target.value })}
        />
        <textarea
          placeholder="내용"
          value={newPost.content}
          onChange={(e) => setNewPost({ ...newPost, content: e.target.value })}
        />
        <button className="write-btn" onClick={handleSave}>
          저장하기
        </button>
      </div>

      <hr />

      {/* 테이블: 목록 영역 */}
      <table className="board-table">
        <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>조회수</th>
            <th>관리</th>
          </tr>
        </thead>
        <tbody>
          {posts.length > 0 ? (
            posts.map((post) => (
              <tr
                key={post.id}
                onClick={() => handleDetail(post.id)}
                style={{ cursor: "pointer" }}
              >
                <td>{post.id}</td>
                <td>{post.title}</td>
                <td>{post.writer}</td>
                <td>{post.viewCnt}</td>
                <td>
                  <button
                    onClick={(e) => handleDelete(e, post.id)}
                    style={{
                      backgroundColor: "#ff4d4d",
                      color: "white",
                      border: "none",
                      padding: "5px 10px",
                      borderRadius: "4px",
                    }}
                  >
                    삭제
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="5">데이터가 없습니다.</td>
            </tr>
          )}
        </tbody>
      </table>

      {/* 모달: 상세 보기 및 수정 영역 */}
      {selectedPost && (
        <div
          className="modal"
          style={{
            position: "fixed",
            top: 0,
            left: 0,
            width: "100%",
            height: "100%",
            backgroundColor: "rgba(0,0,0,0.5)",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <div
            style={{
              backgroundColor: "white",
              padding: "30px",
              borderRadius: "8px",
              width: "400px",
              textAlign: "left",
            }}
          >
            {isEdit ? (
              <>
                <h3>글 수정하기</h3>
                <input
                  style={{ width: "100%", marginBottom: "10px" }}
                  value={selectedPost.title}
                  onChange={(e) =>
                    setSelectedPost({ ...selectedPost, title: e.target.value })
                  }
                />
                <textarea
                  style={{
                    width: "100%",
                    height: "100px",
                    marginBottom: "10px",
                  }}
                  value={selectedPost.content}
                  onChange={(e) =>
                    setSelectedPost({
                      ...selectedPost,
                      content: e.target.value,
                    })
                  }
                />
                <button onClick={() => handleUpdate(selectedPost.id)}>
                  수정완료
                </button>
                <button onClick={() => setIsEdit(false)}>취소</button>
              </>
            ) : (
              <>
                <h3>{selectedPost.title}</h3>
                <p>
                  <strong>작성자:</strong> {selectedPost.writer}
                </p>
                <p>
                  <strong>내용:</strong> {selectedPost.content}
                </p>
                <p>
                  <strong>조회수:</strong> {selectedPost.viewCnt}
                </p>
                <button onClick={() => setIsEdit(true)}>수정하기</button>
                <button onClick={() => setSelectedPost(null)}>닫기</button>
              </>
            )}
          </div>
        </div>
      )}
    </div>
  );
}

export default App;
