import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useLoginMutation } from '../features/auth/authApi';
import { setCredentials } from '../features/auth/authSlice';

export const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [login, { isLoading, error }] = useLoginMutation();
  const dispatch = useDispatch();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = await login({ email, password }).unwrap();
      
      // Simulación: En un caso real, decodificás el token para sacar el rol
      const mockUser = { email, rol: 'CONSUMIDOR' }; 
      
      dispatch(setCredentials({ token, user: mockUser }));
      alert('¡Login exitoso!');
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
      {error && <p style={{color: 'red'}}>Error: {error.data || 'Credenciales inválidas'}</p>}
    </form>
  );
};