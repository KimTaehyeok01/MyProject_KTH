import { Link, useNavigate } from 'react-router-dom';

function Navbar({ user, logout }) {
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  const handleGoogleLogin = () => {
    window.location.href = 'http://localhost:8080/oauth2/authorization/google';
  };

  return (
    <nav style={styles.nav}>
      <Link to="/" style={styles.logo}>📝 블로그</Link>

      <div style={styles.right}>
        {user ? (
          <>
            <img src={user.picture} alt="프로필" style={styles.avatar} />
            <span style={styles.name}>{user.name}</span>
            <Link to="/write" style={styles.btn}>글쓰기</Link>
            <button onClick={handleLogout} style={styles.logoutBtn}>로그아웃</button>
          </>
        ) : (
          <button onClick={handleGoogleLogin} style={styles.loginBtn}>
            구글 로그인
          </button>
        )}
      </div>
    </nav>
  );
}

const styles = {
  nav: {
    display: 'flex', justifyContent: 'space-between', alignItems: 'center',
    padding: '12px 24px', background: '#fff', borderBottom: '1px solid #eee',
    position: 'sticky', top: 0, zIndex: 100,
  },
  logo: { fontSize: '20px', fontWeight: 'bold', textDecoration: 'none', color: '#333' },
  right: { display: 'flex', alignItems: 'center', gap: '12px' },
  avatar: { width: '32px', height: '32px', borderRadius: '50%' },
  name: { fontSize: '14px', color: '#555' },
  btn: {
    padding: '6px 14px', background: '#4CAF50', color: '#fff',
    borderRadius: '6px', textDecoration: 'none', fontSize: '14px',
  },
  logoutBtn: {
    padding: '6px 14px', background: '#f44336', color: '#fff',
    border: 'none', borderRadius: '6px', cursor: 'pointer', fontSize: '14px',
  },
  loginBtn: {
    padding: '8px 16px', background: '#4285F4', color: '#fff',
    border: 'none', borderRadius: '6px', cursor: 'pointer', fontSize: '14px',
  },
};

export default Navbar;
