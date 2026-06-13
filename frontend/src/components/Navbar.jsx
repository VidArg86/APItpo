import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { selectCurrentUser, logOut } from '../features/auth/authSlice';

export const Navbar = ({ setCurrentView }) => {
  const usuario = useSelector(selectCurrentUser);
  const dispatch = useDispatch();

  const handleLogOut = () => {
    dispatch(logOut());
    setCurrentView('catalogo');
  };

  return (
    <nav style={{ display: 'flex', justifyContent: 'space-between', padding: '10px 20px', backgroundColor: '#222', color: 'white', alignItems: 'center' }}>
      <div style={{ display: 'flex', gap: '15px' }}>
        <button onClick={() => setCurrentView('catalogo')} style={{ background: 'none', color: 'white', border: 'none', cursor: 'pointer' }}>Catálogo</button>
        
        {/* Solo los consumidores acceden al carrito */}
        {usuario?.rol === 'CONSUMIDOR' && (
          <button onClick={() => setCurrentView('carrito')} style={{ background: 'none', color: 'white', border: 'none', cursor: 'pointer' }}>Mi Carrito</button>
        )}
      </div>

      <div>
        {usuario ? (
          <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
            <span>Hola, <strong>{usuario.email}</strong> ({usuario.rol})</span>
            <button onClick={handleLogOut} style={{ backgroundColor: '#d9534f', color: 'white', border: 'none', padding: '5px 10px', cursor: 'pointer' }}>Salir</button>
          </div>
        ) : (
          <button onClick={() => setCurrentView('login')} style={{ backgroundColor: '#0275d8', color: 'white', border: 'none', padding: '5px 10px', cursor: 'pointer' }}>Iniciar Sesión</button>
        )}
      </div>
    </nav>
  );
};