// Ruta: src/components/Navbar.jsx
//
// La barra de navegación de la app.
// Muestra los links principales y, si el usuario está logueado,
// le muestra el botón de salir en vez de los links de login/registro.
// También tiene el ícono del carrito con un contador de items en tiempo real.
 
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useCart } from '../hooks/useContext/CartContext';
 
const Navbar = () => {
  const navigate = useNavigate();
 
  // Leemos el token del localStorage para saber si el usuario está logueado.
  // Usamos useState para que el navbar se re-renderice cuando el token cambia.
  const [token, setToken] = useState(localStorage.getItem('token'));
 
  // Traemos los items del carrito para calcular cuántos hay
  const { cartItems } = useCart();
 
  // Sumamos las cantidades de todos los productos del carrito
  // Ejemplo: si hay 2 remeras y 3 pantalones, muestra 5
  const cantidadTotal = cartItems.reduce((acc, item) => acc + item.quantity, 0);
 
  const handleLogout = () => {
    localStorage.removeItem('token');
    setToken(null); // Actualizamos el estado para que el navbar se re-renderice
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
 
      {/* Link al carrito con el contador de items */}
      <Link to="/cart" style={{ color: 'white', textDecoration: 'none' }}>
        🛒 Carrito
        {/* El badge solo aparece si hay al menos un producto en el carrito */}
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
 
      {/* Si hay token mostramos "Salir", si no mostramos "Login" y "Registro" */}
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