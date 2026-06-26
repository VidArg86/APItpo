import { useEffect, useRef, useState } from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import logo from '../assets/laEsquinaLogo.png';
import { useCart } from '../hooks/useContext/CartContext';
import { logout } from '../store/authSlice';
import '../styles/navbar.css';

const Navbar = () => {
  const { cartItems } = useCart();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const isLoggedIn = useSelector((state) => state.auth.isLoggedIn);
  const [menuAbierto, setMenuAbierto] = useState(false);
  const menuRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (e) => {
      if (menuRef.current && !menuRef.current.contains(e.target)) {
        setMenuAbierto(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  const handleLogout = () => {
    dispatch(logout());
    setMenuAbierto(false);
    navigate('/');
  };

  const totalItems = cartItems.reduce((acc, item) => acc + item.quantity, 0);

  return (
    <nav className="navbar">
      <div className="navbar-logo">
        <Link to="/">
          <img src={logo} alt="La Esquina Bakery" />
        </Link>
      </div>

      <ul className="navbar-links">
        <li>
          <NavLink to="/" end className={({ isActive }) => isActive ? 'active-link' : ''}>
            Inicio
          </NavLink>
        </li>
        <li>
          <NavLink to="/productos" className={({ isActive }) => isActive ? 'active-link' : ''}>
            Productos
          </NavLink>
        </li>
        <li>
          <NavLink to="/nosotros" className={({ isActive }) => isActive ? 'active-link' : ''}>
            Nosotros
          </NavLink>
        </li>
        <li>
          <NavLink to="/contacto" className={({ isActive }) => isActive ? 'active-link' : ''}>
            Contacto
          </NavLink>
        </li>
      </ul>

      <div className="navbar-actions">
        <Link to="/cart" className="nav-icon nav-cart" title="Carrito">
          <span aria-hidden="true">🛒</span>
          {totalItems > 0 && (
            <span className="cart-badge">{totalItems}</span>
          )}
        </Link>

        {isLoggedIn ? (
          <div className="nav-user-menu" ref={menuRef}>
            <button
              className="nav-icon nav-user-btn"
              onClick={() => setMenuAbierto(!menuAbierto)}
              title="Mi Cuenta"
            >
              <span aria-hidden="true">👤</span>
              <span className="user-dot" />
            </button>
            {menuAbierto && (
              <div className="user-dropdown">
                <Link to="/perfil" className="dropdown-item" onClick={() => setMenuAbierto(false)}>
                  Mi perfil
                </Link>
                <Link to="/mis-pedidos" className="dropdown-item" onClick={() => setMenuAbierto(false)}>
                  Mis pedidos
                </Link>
                <Link to="/favoritos" className="dropdown-item" onClick={() => setMenuAbierto(false)}>
                  Mis favoritos
                </Link>
                <button className="dropdown-item dropdown-logout" onClick={handleLogout}>
                  Cerrar sesion
                </button>
              </div>
            )}
          </div>
        ) : (
          <Link to="/login" className="nav-icon" title="Iniciar sesion">
            <span aria-hidden="true">👤</span>
          </Link>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
