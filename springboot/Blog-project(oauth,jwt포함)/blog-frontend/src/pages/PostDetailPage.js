import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../utils/api';

function PostDetailPage({ user }) {
  const { id } = useParams();
  const navigate = useNavigate();
  const [post, setPost] = useState(null);
  const [comment, setComment] = useState('');

  useEffect(() => {
    api.get(`/api/posts/${id}`)
      .then((res) => setPost(res.data))
      .catch(() => navigate('/'));
  }, [id]);

  const handleDelete = async () => {
    if (!window.confirm('삭제하시겠습니까?')) return;
    try {
      await api.delete(`/api/posts/${id}`);
      navigate('/');
    } catch (e) {
      alert('삭제 권한이 없습니다.');
    }
  };

  const handleCommentSubmit = async (e) => {
    e.preventDefault();
    if (!comment.trim()) return;
    try {
      const res = await api.post(`/api/posts/${id}/comments`, { content: comment });
      setPost({ ...post, comments: [...post.comments, res.data] });
      setComment('');
    } catch (e) {
      alert('로그인이 필요합니다.');
    }
  };

  const handleCommentDelete = async (commentId) => {
    try {
      await api.delete(`/api/posts/comments/${commentId}`);
      setPost({ ...post, comments: post.comments.filter((c) => c.id !== commentId) });
    } catch (e) {
      alert('삭제 권한이 없습니다.');
    }
  };

  if (!post) return <p style={{ textAlign: 'center', marginTop: '60px' }}>로딩중...</p>;

  return (
    <div style={styles.container}>
      {/* 글 헤더 */}
      <h1 style={styles.title}>{post.title}</h1>
      <div style={styles.meta}>
        <img src={post.authorPicture} alt="" style={styles.avatar} />
        <span>{post.authorName}</span>
        <span style={styles.date}>{new Date(post.createdAt).toLocaleDateString()}</span>

        {/* 본인 글이면 수정/삭제 버튼 */}
        {user && user.name === post.authorName && (
          <div style={styles.actions}>
            <button onClick={() => navigate(`/edit/${id}`)} style={styles.editBtn}>수정</button>
            <button onClick={handleDelete} style={styles.deleteBtn}>삭제</button>
          </div>
        )}
      </div>

      {/* 글 내용 */}
      <div style={styles.content}>{post.content}</div>

      {/* 댓글 */}
      <div style={styles.commentSection}>
        <h3>댓글 {post.comments.length}개</h3>

        {/* 댓글 목록 */}
        {post.comments.map((c) => (
          <div key={c.id} style={styles.comment}>
            <img src={c.authorPicture} alt="" style={styles.avatar} />
            <div style={styles.commentBody}>
              <span style={styles.commentAuthor}>{c.authorName}</span>
              <p style={styles.commentContent}>{c.content}</p>
            </div>
            {user && user.name === c.authorName && (
              <button onClick={() => handleCommentDelete(c.id)} style={styles.deleteBtn}>삭제</button>
            )}
          </div>
        ))}

        {/* 댓글 작성 */}
        {user ? (
          <form onSubmit={handleCommentSubmit} style={styles.commentForm}>
            <input
              value={comment}
              onChange={(e) => setComment(e.target.value)}
              placeholder="댓글을 입력하세요"
              style={styles.commentInput}
            />
            <button type="submit" style={styles.submitBtn}>등록</button>
          </form>
        ) : (
          <p style={styles.loginMsg}>댓글을 작성하려면 로그인하세요.</p>
        )}
      </div>
    </div>
  );
}

const styles = {
  container: { maxWidth: '800px', margin: '0 auto', padding: '24px' },
  title: { fontSize: '28px', fontWeight: 'bold', marginBottom: '16px' },
  meta: { display: 'flex', alignItems: 'center', gap: '8px', marginBottom: '24px', color: '#999', fontSize: '14px' },
  avatar: { width: '28px', height: '28px', borderRadius: '50%' },
  date: { marginLeft: 'auto' },
  actions: { display: 'flex', gap: '8px' },
  editBtn: { padding: '4px 10px', background: '#2196F3', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer' },
  deleteBtn: { padding: '4px 10px', background: '#f44336', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer' },
  content: { fontSize: '16px', lineHeight: '1.8', padding: '24px 0', borderTop: '1px solid #eee', borderBottom: '1px solid #eee', whiteSpace: 'pre-wrap' },
  commentSection: { marginTop: '32px' },
  comment: { display: 'flex', alignItems: 'flex-start', gap: '12px', padding: '12px 0', borderBottom: '1px solid #f5f5f5' },
  commentBody: { flex: 1 },
  commentAuthor: { fontWeight: 'bold', fontSize: '14px' },
  commentContent: { fontSize: '14px', color: '#444', marginTop: '4px' },
  commentForm: { display: 'flex', gap: '8px', marginTop: '16px' },
  commentInput: { flex: 1, padding: '10px', border: '1px solid #ddd', borderRadius: '6px', fontSize: '14px' },
  submitBtn: { padding: '10px 20px', background: '#4CAF50', color: '#fff', border: 'none', borderRadius: '6px', cursor: 'pointer' },
  loginMsg: { color: '#999', fontSize: '14px', marginTop: '16px' },
};

export default PostDetailPage;
