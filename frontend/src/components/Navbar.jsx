import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { selectCurrentUser, logOut } from '../features/auth/authSlice';
import { useLogoutServerMutation } from '../features/auth/authApi';
import { apiSlice } from '../app/apiSlice';

export const Navbar = ({ setCurrentView }) => {
  const usuario = useSelector(selectCurrentUser);
  const dispatch = useDispatch();
  const [logoutServer] = useLogoutServerMutation();

  const handleLogOut = async () => {
  try {
    await logoutServer().unwrap(); 
  } catch (err) {
    console.error("Error al cerrar sesión:", err);
  } finally {
    dispatch(logOut());
    
    // ── CRUCIAL: Limpia la caché al salir para remover permisos del usuario anterior
    dispatch(apiSlice.util.resetApiState()); 
    
    setCurrentView('catalogo');
  }
};

  return (
    <nav style={{ display: 'flex', justifyContent: 'space-between', padding: '10px 20px', backgroundColor: '#222', color: 'white', alignItems: 'center' }}>
      <div style={{ display: 'flex', gap: '15px' }}>
        <button onClick={() => setCurrentView('catalogo')} style={{ background: 'none', color: 'white', border: 'none', cursor: 'pointer' }}>Catálogo</button>
        {usuario?.rol === 'ROLE_CONSUMIDOR' || usuario?.rol === 'CONSUMIDOR' ? (
          <button onClick={() => setCurrentView('carrito')} style={{ background: 'none', color: 'white', border: 'none', cursor: 'pointer' }}>Mi Carrito</button>
        ) : null}
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