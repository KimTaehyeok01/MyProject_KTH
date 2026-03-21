import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

// 구글 로그인 성공 후 스프링이 이 페이지로 토큰을 넘겨줌
function OAuth2CallbackPage({ setUser }) {
  const navigate = useNavigate();

  useEffect(() => {
    // URL에서 토큰 꺼내기
    const params = new URLSearchParams(window.location.search);
    const token = params.get('token');

    if (token) {
      localStorage.setItem('token', token);
      window.location.href = '/'; // 홈으로 이동 (새로고침해서 유저정보 불러오기)
    } else {
      navigate('/');
    }
  }, []);

  return <p style={{ textAlign: 'center', marginTop: '60px' }}>로그인 처리중...</p>;
}

export default OAuth2CallbackPage;
