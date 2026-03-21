import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import HomePage from './pages/HomePage';
import PostDetailPage from './pages/PostDetailPage';
import WriteEditPage from './pages/WriteEditPage';
import OAuth2CallbackPage from './pages/OAuth2CallbackPage';
import { useAuth } from './hooks/useAuth';

function App() {
  const { user, setUser, loading, logout } = useAuth();

  if (loading) return <p style={{ textAlign: 'center', marginTop: '60px' }}>로딩중...</p>;

  return (
    <BrowserRouter>
      <Navbar user={user} logout={logout} />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/posts/:id" element={<PostDetailPage user={user} />} />
        <Route path="/write" element={<WriteEditPage user={user} />} />
        <Route path="/edit/:id" element={<WriteEditPage user={user} />} />
        <Route path="/oauth2/callback" element={<OAuth2CallbackPage setUser={setUser} />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
