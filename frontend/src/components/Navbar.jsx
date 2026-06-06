import React, { useState, useEffect, useRef } from 'react';
import { NavLink, Link, useNavigate } from 'react-router-dom';
import logo from '../assets/laEsquinaLogo.png';
import { useCart } from '../hooks/useContext/CartContext';
import '../styles/navbar.css';

const Navbar = () => {
  const { cartItems } = useCart();
  const navigate = useNavigate();

  const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem('token'));
  const [menuAbierto, setMenuAbierto] = useState(false);
  const menuRef = useRef(null);

  // Sincroniza el estado si el token cambia en otra pestaña
  useEffect(() => {
    const checkAuth = () => setIsLoggedIn(!!localStorage.getItem('token'));
    window.addEventListener('storage', checkAuth);
    return () => window.removeEventListener('storage', checkAuth);
  }, []);

  // Cierra el dropdown si se hace click afuera
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
    localStorage.removeItem('token');
    setIsLoggedIn(false);
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

        {/* Carrito con badge de cantidad */}
        <Link to="/cart" className="nav-icon nav-cart" title="Carrito">
          🛒
          {totalItems > 0 && (
            <span className="cart-badge">{totalItems}</span>
          )}
        </Link>

        {/* Cuenta: dropdown si está logueado, link a login si no */}
        {isLoggedIn ? (
          <div className="nav-user-menu" ref={menuRef}>
            <button
              className="nav-icon nav-user-btn"
              onClick={() => setMenuAbierto(!menuAbierto)}
              title="Mi Cuenta"
            >
              👤
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
                <button className="dropdown-item dropdown-logout" onClick={handleLogout}>
                  Cerrar sesión
                </button>
              </div>
            )}
          </div>
        ) : (
          <Link to="/login" className="nav-icon" title="Iniciar sesión">
            👤
          </Link>
        )}

      </div>
    </nav>
  );
};

export default Navbar;