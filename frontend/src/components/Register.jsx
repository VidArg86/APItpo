import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';

const Register = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    email: '',
    contraseña: '',
    nombre: '',
    apellido: '',
    dni: '',
    telefono: '',
    direccion: ''
  });
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const response = await fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        // el backend espera "contraseña" como key
        body: JSON.stringify(form)
      });

      if (response.ok) {
        const token = await response.text(); // el back devuelve el JWT como string plano
        localStorage.setItem('token', token);
        navigate('/'); // redirige al catálogo
      } else {
        const msg = await response.text();
        setError(msg || 'Error al registrarse');
      }
    } catch (err) {
      setError('No se pudo conectar con el servidor');
    }
  };

  return (
    <div style={{ maxWidth: '400px', margin: '40px auto' }}>
      <h2>Crear cuenta</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
        <input name="nombre"     placeholder="Nombre"      value={form.nombre}     onChange={handleChange} required />
        <input name="apellido"   placeholder="Apellido"    value={form.apellido}   onChange={handleChange} required />
        <input name="email"      placeholder="Email"       value={form.email}      onChange={handleChange} type="email" required />
        <input name="contraseña" placeholder="Contraseña"  value={form.contraseña} onChange={handleChange} type="password" required />
        <input name="dni"        placeholder="DNI"         value={form.dni}        onChange={handleChange} required />
        <input name="telefono"   placeholder="Teléfono"    value={form.telefono}   onChange={handleChange} required />
        <input name="direccion"  placeholder="Dirección"   value={form.direccion}  onChange={handleChange} required />
        <button type="submit">Registrarse</button>
      </form>
      <p>¿Ya tenés cuenta? <Link to="/login">Iniciá sesión</Link></p>
    </div>
  );
};

export default Register;