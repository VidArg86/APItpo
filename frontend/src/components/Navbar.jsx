// Ruta: src/components/Navbar.jsx
//
// La barra de navegación de la app.
// Muestra los links principales y, si el usuario está logueado,
// le muestra el botón de salir en vez de los links de login/registro.
// También tiene el ícono del carrito con un contador de items en tiempo real.
 
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useCart } from '../hooks/useContext/CartContext';
// Importamos useSelector de Redux
import { useSelector } from 'react-redux';
 
const Navbar = () => {
  const navigate = useNavigate();
  const [token, setToken] = useState(localStorage.getItem('token'));
  const { cartItems } = useCart();
 
  // Obtenemos la cantidad de items favoritos
  const favoritos = useSelector((state) => state.favoritos.items);
  const cantidadFavoritos = favoritos.length;

  const cantidadTotal = cartItems.reduce((acc, item) => acc + item.quantity, 0);
 
  const handleLogout = () => {
    localStorage.removeItem('token');
    setToken(null);
    navigate('/login');
  };
 
  return (
    <nav style={{
      display: 'flex',
      gap: '20px',
      padding: '20px',
      background: '#333',
      color: 'white',
      alignItems: 'center'
    }}>
      <Link to="/" style={{ color: 'white', textDecoration: 'none' }}>Catálogo</Link>
 
        {/* Ahora es un Link interactivo clickeable hacia la lista de favoritos */}
      <Link to="/favoritos" style={{ color: 'white', textDecoration: 'none', display: 'flex', alignItems: 'center' }}>
        ❤️ Favoritos ({cantidadFavoritos})
      </Link>

      <Link to="/cart" style={{ color: 'white', textDecoration: 'none' }}>
        🛒 Carrito
        {cantidadTotal > 0 && (
          <span style={{
            backgroundColor: '#aa3bff',
            color: 'white',
            borderRadius: '50%',
            padding: '1px 7px',
            fontSize: '12px',
            marginLeft: '6px',
            fontWeight: 'bold'
          }}>
            {cantidadTotal}
          </span>
        )}
      </Link>
 
      {token ? (
        <button
          onClick={handleLogout}
          style={{ background: 'red', color: 'white', border: 'none', cursor: 'pointer', borderRadius: '4px', padding: '5px 10px' }}
        >
          Salir
        </button>
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