import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../utils/api';

function HomePage() {
  const [posts, setPosts] = useState([]);
  const [keyword, setKeyword] = useState('');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);

  const fetchPosts = async (kw = '', p = 0) => {
    setLoading(true);
    try {
      const url = kw
        ? `/api/posts/search?keyword=${kw}&page=${p}&size=10`
        : `/api/posts?page=${p}&size=10`;
      const res = await api.get(url);
      setPosts(res.data.content);
      setTotalPages(res.data.totalPages);
    } catch (e) {
      console.error(e);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  const handleSearch = (e) => {
    e.preventDefault();
    setPage(0);
    fetchPosts(keyword, 0);
  };

  const handlePage = (p) => {
    setPage(p);
    fetchPosts(keyword, p);
  };

  return (
    <div style={styles.container}>
      {/* 검색 */}
      <form onSubmit={handleSearch} style={styles.searchForm}>
        <input
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          placeholder="검색어를 입력하세요"
          style={styles.searchInput}
        />
        <button type="submit" style={styles.searchBtn}>검색</button>
      </form>

      {/* 글 목록 */}
      {loading ? (
        <p>로딩중...</p>
      ) : posts.length === 0 ? (
        <p style={styles.empty}>게시글이 없습니다.</p>
      ) : (
        posts.map((post) => (
          <Link to={`/posts/${post.id}`} key={post.id} style={styles.postCard}>
            <h2 style={styles.title}>{post.title}</h2>
            <p style={styles.content}>{post.content.substring(0, 100)}...</p>
            <div style={styles.meta}>
              <img src={post.authorPicture} alt="" style={styles.avatar} />
              <span>{post.authorName}</span>
              <span style={styles.date}>
                {new Date(post.createdAt).toLocaleDateString()}
              </span>
              <span>댓글 {post.comments.length}</span>
            </div>
          </Link>
        ))
      )}

      {/* 페이징 */}
      <div style={styles.pagination}>
        {Array.from({ length: totalPages }, (_, i) => (
          <button
            key={i}
            onClick={() => handlePage(i)}
            style={{ ...styles.pageBtn, fontWeight: page === i ? 'bold' : 'normal' }}
          >
            {i + 1}
          </button>
        ))}
      </div>
    </div>
  );
}

const styles = {
  container: { maxWidth: '800px', margin: '0 auto', padding: '24px' },
  searchForm: { display: 'flex', gap: '8px', marginBottom: '24px' },
  searchInput: {
    flex: 1, padding: '10px', border: '1px solid #ddd',
    borderRadius: '6px', fontSize: '14px',
  },
  searchBtn: {
    padding: '10px 20px', background: '#4285F4', color: '#fff',
    border: 'none', borderRadius: '6px', cursor: 'pointer',
  },
  postCard: {
    display: 'block', padding: '20px', marginBottom: '16px',
    border: '1px solid #eee', borderRadius: '8px', textDecoration: 'none',
    color: '#333', transition: 'box-shadow 0.2s',
  },
  title: { fontSize: '20px', fontWeight: 'bold', marginBottom: '8px' },
  content: { fontSize: '14px', color: '#666', marginBottom: '12px' },
  meta: { display: 'flex', alignItems: 'center', gap: '8px', fontSize: '13px', color: '#999' },
  avatar: { width: '24px', height: '24px', borderRadius: '50%' },
  date: { marginLeft: 'auto' },
  empty: { textAlign: 'center', color: '#999', marginTop: '60px' },
  pagination: { display: 'flex', justifyContent: 'center', gap: '8px', marginTop: '24px' },
  pageBtn: {
    padding: '6px 12px', border: '1px solid #ddd',
    borderRadius: '4px', cursor: 'pointer', background: '#fff',
  },
};

export default HomePage;
