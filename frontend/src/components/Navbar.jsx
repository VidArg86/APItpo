import { useState } from 'react'; // FIX: agregar useState
import { Link, useNavigate } from 'react-router-dom';

const Navbar = () => {
  const navigate = useNavigate();
  // FIX: sin useState el navbar no se actualiza al hacer login/logout
  const [token, setToken] = useState(localStorage.getItem('token'));

  const handleLogout = () => {
    localStorage.removeItem('token');
    setToken(null); // FIX: actualizar estado para re-renderizar
    navigate('/login');
  };

  return (
  <nav style={{ display: 'flex', gap: '20px', padding: '20px', background: '#333', color: 'white' }}>
    <Link to="/" style={{ color: 'white', textDecoration: 'none' }}>Catálogo</Link>
    {token ? (
      <button onClick={handleLogout} style={{ background: 'red', color: 'white', border: 'none', cursor: 'pointer' }}>Salir</button>
    ) : (
      <>
        <Link to="/login" style={{ color: 'white', textDecoration: 'none' }}>Iniciar Sesión</Link>
        <Link to="/register" style={{ color: 'white', textDecoration: 'none' }}>Registrarse</Link>
      </>
    )}
  </nav>
);
};

export default Navbar;