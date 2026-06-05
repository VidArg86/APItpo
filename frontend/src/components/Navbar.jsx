import React from 'react';
// Importamos NavLink además de Link
import { NavLink, Link } from 'react-router-dom';
import logo from '../assets/laEsquinaLogo.png'; 
import '../styles/navbar.css';

const Navbar = () => {
  return (
    <nav className="navbar">
      <div className="navbar-logo">
        <Link to="/">
          <img src={logo} alt="La Esquina Bakery" />
        </Link>
      </div>

      <ul className="navbar-links">
        <li>
          {/* El prop 'end' asegura que Inicio solo se marque si la ruta es exactamente "/" */}
          <NavLink to="/" end className={({ isActive }) => isActive ? "active-link" : ""}>
            Inicio
          </NavLink>
        </li>
        <li>
          <NavLink to="/productos" className={({ isActive }) => isActive ? "active-link" : ""}>
            Productos
          </NavLink>
        </li>
        <li>
          <NavLink to="/nosotros" className={({ isActive }) => isActive ? "active-link" : ""}>
            Nosotros
          </NavLink>
        </li>
        <li>
          <NavLink to="/contacto" className={({ isActive }) => isActive ? "active-link" : ""}>
            Contacto
          </NavLink>
        </li>
      </ul>

      <div className="navbar-actions">
        <Link to="/login" className="nav-icon" title="Mi Cuenta">
           👤
        </Link>
        <button className="nav-icon" title="Carrito">
           🛒
        </button>
      </div>
    </nav>
  );
};

export default Navbar;