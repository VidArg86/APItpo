import { Link, useNavigate } from 'react-router-dom';

const Navbar = () => {
  const navigate = useNavigate();
  const token = localStorage.getItem('token'); // Verificamos si el usuario está logueado

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  return (
    <nav style={{ display: 'flex', gap: '20px', padding: '20px', background: '#333', color: 'white' }}>
      <Link to="/" style={{ color: 'white', textDecoration: 'none' }}>Catálogo</Link>
      {token ? (
        <button onClick={handleLogout} style={{ background: 'red', color: 'white', border: 'none', cursor: 'pointer' }}>Salir</button>
      ) : (
        <Link to="/login" style={{ color: 'white', textDecoration: 'none' }}>Iniciar Sesión</Link>
      )}
    </nav>
  );
};

export default Navbar;