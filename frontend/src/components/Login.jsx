import React, { useState } from 'react';
import { useDispatch } from 'react-redux'; 
import { useLoginMutation } from '../features/auth/authApi';
import { setCredentials } from '../features/auth/authSlice';
import { apiSlice } from '../app/apiSlice';

export const Login = ({ setCurrentView }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [login, { isLoading, error }] = useLoginMutation();
  const dispatch = useDispatch();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const serverResponse = await login({ email, password }).unwrap();
      
      const rolAsignado = serverResponse.roles && serverResponse.roles.length > 0 
        ? serverResponse.roles[0] 
        : 'CONSUMIDOR';

      dispatch(setCredentials({ user: { email: serverResponse.email, rol: rolAsignado } }));
      
      // ── CRUCIAL: Resetea el estado de toda la API para volver a consultar los productos con la nueva sesión
      dispatch(apiSlice.util.resetApiState()); 
      
      setCurrentView('catalogo');
    } catch (err) {
      console.error('Error al loguearse:', err);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Iniciar Sesión</h2>
      <input type="email" placeholder="Email" onChange={e => setEmail(e.target.value)} required />
      <input type="password" placeholder="Contraseña" onChange={e => setPassword(e.target.value)} required />
      <button type="submit" disabled={isLoading}>Ingresar</button>
      {error && <p style={{color: 'red'}}>Error: {error.data?.error || 'Credenciales inválidas'}</p>}
    </form>
  );
};