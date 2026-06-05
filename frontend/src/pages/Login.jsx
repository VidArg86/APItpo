import { useState } from 'react';
import { Link } from 'react-router-dom';
 
const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
 
  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, "contraseña": password })
      });
 
      if (response.ok) {
        const token = await response.text();
        localStorage.setItem('token', token);
        window.location.href = '/'; // fuerza recarga para que Navbar re-lea el token
      } else {
        alert('Credenciales incorrectas');
      }
    } catch (error) {
      console.error('Error al conectar con el backend:', error);
    }
  };
 
  return (
    <div>
      <h2>Iniciar Sesión</h2>
      <form onSubmit={handleLogin} style={{ display: 'flex', flexDirection: 'column', width: '300px', gap: '10px' }}>
        <input
          type="email"
          placeholder="Mail"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Contraseña"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">Ingresar</button>
      </form>
      <p>¿No tenés cuenta? <Link to="/register">Registrate</Link></p>
    </div>
  );
};
 
export default Login;