import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../utils/api';

function WriteEditPage({ user }) {
  const { id } = useParams(); // id 있으면 수정, 없으면 새 글
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  // 수정 모드면 기존 데이터 불러오기
  useEffect(() => {
    if (id) {
      api.get(`/api/posts/${id}`).then((res) => {
        setTitle(res.data.title);
        setContent(res.data.content);
      });
    }
  }, [id]);

  // 로그인 안 됐으면 홈으로
  useEffect(() => {
    if (!user) navigate('/');
  }, [user]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!title.trim() || !content.trim()) {
      alert('제목과 내용을 입력하세요.');
      return;
    }

    try {
      if (id) {
        await api.put(`/api/posts/${id}`, { title, content });
        navigate(`/posts/${id}`);
      } else {
        const res = await api.post('/api/posts', { title, content });
        navigate(`/posts/${res.data.id}`);
      }
    } catch (e) {
      alert('오류가 발생했습니다.');
    }
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.heading}>{id ? '글 수정' : '새 글 쓰기'}</h2>
      <form onSubmit={handleSubmit}>
        <input
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="제목"
          style={styles.titleInput}
        />
        <textarea
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="내용을 입력하세요..."
          style={styles.contentInput}
        />
        <div style={styles.btnGroup}>
          <button type="button" onClick={() => navigate(-1)} style={styles.cancelBtn}>취소</button>
          <button type="submit" style={styles.submitBtn}>{id ? '수정' : '등록'}</button>
        </div>
      </form>
    </div>
  );
}

const styles = {
  container: { maxWidth: '800px', margin: '0 auto', padding: '24px' },
  heading: { fontSize: '24px', fontWeight: 'bold', marginBottom: '20px' },
  titleInput: {
    width: '100%', padding: '12px', fontSize: '18px',
    border: '1px solid #ddd', borderRadius: '6px', marginBottom: '12px',
    boxSizing: 'border-box',
  },
  contentInput: {
    width: '100%', padding: '12px', fontSize: '15px',
    border: '1px solid #ddd', borderRadius: '6px',
    minHeight: '400px', resize: 'vertical', boxSizing: 'border-box',
  },
  btnGroup: { display: 'flex', justifyContent: 'flex-end', gap: '8px', marginTop: '16px' },
  cancelBtn: { padding: '10px 20px', background: '#eee', border: 'none', borderRadius: '6px', cursor: 'pointer' },
  submitBtn: { padding: '10px 20px', background: '#4CAF50', color: '#fff', border: 'none', borderRadius: '6px', cursor: 'pointer' },
};

export default WriteEditPage;
