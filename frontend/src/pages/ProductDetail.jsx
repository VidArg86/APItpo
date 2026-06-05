// Ruta: src/components/ProductDetail.jsx
//
// Muestra el detalle de un producto específico.
// El :id de la URL lo leemos con useParams y lo usamos para hacer el fetch.
// También tiene el botón de agregar al carrito con feedback visual
// (cambia a "✓ Agregado" por 2 segundos para confirmarle al usuario).
 
import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { useCart } from '../hooks/useContext/CartContext';
 
const ProductDetail = () => {
  // useParams nos da el :id que está en la URL (ej: /producto/5 → id = "5")
  const { id } = useParams();
 
  const [producto, setProducto] = useState(null);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState('');
 
  // Estado para el feedback visual del botón ("Agregar" / "✓ Agregado")
  const [agregado, setAgregado] = useState(false);
 
  // Traemos addToCart del contexto del carrito
  const { addToCart } = useCart();
 
  // Cada vez que cambia el id en la URL, volvemos a buscar el producto
  useEffect(() => {
    const fetchProductoDetalle = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await fetch(`http://localhost:8080/api/productos/${id}`, {
          headers: token ? { 'Authorization': `Bearer ${token}` } : {}
        });
 
        if (response.ok) {
          const data = await response.json();
          setProducto(data);
        } else {
          setError('No se pudo cargar el detalle del producto.');
        }
      } catch (err) {
        console.error('Error fetching producto detalle:', err);
        setError('Error al conectar con el servidor.');
      } finally {
        setCargando(false);
      }
    };
 
    if (id) fetchProductoDetalle();
  }, [id]); // Se vuelve a ejecutar si el id cambia
 
  const handleAgregarAlCarrito = () => {
    addToCart(producto);
 
    // Mostramos "✓ Agregado" por 2 segundos y después volvemos al texto original
    setAgregado(true);
    setTimeout(() => setAgregado(false), 2000);
  };
 
  // Pantallas de carga, error o producto no encontrado
  if (cargando) return <h2>Cargando detalle del producto...</h2>;
  if (error) return <p style={{ color: 'red', textAlign: 'center' }}>{error}</p>;
  if (!producto) return <p style={{ textAlign: 'center' }}>Producto no encontrado.</p>;
 
  return (
    <div style={{
      maxWidth: '600px',
      margin: '20px auto',
      padding: '20px',
      border: '1px solid #ccc',
      borderRadius: '8px',
      textAlign: 'left'
    }}>
      {/* Flecha para volver al catálogo */}
      <Link to="/" style={{ textDecoration: 'none', color: '#aa3bff', fontWeight: 'bold' }}>
        ← Volver al catálogo
      </Link>
 
      <h2 style={{ marginTop: '20px' }}>{producto.nombre}</h2>
      <p style={{ fontSize: '18px', color: '#555', margin: '15px 0' }}>{producto.descripcion}</p>
      <p style={{ fontSize: '24px', fontWeight: 'bold' }}>${producto.precio}</p>
 
      {/* Mostramos el stock solo si el backend lo manda */}
      {producto.stock !== undefined && (
        <p>Stock disponible: {producto.stock} unidades</p>
      )}
 
      {/* Botón con feedback visual: cambia color y texto al hacer click */}
      <button
        onClick={handleAgregarAlCarrito}
        style={{
          marginTop: '20px',
          padding: '10px 20px',
          background: agregado ? '#4CAF50' : '#aa3bff', // Verde si ya lo agregó
          color: 'white',
          border: 'none',
          borderRadius: '5px',
          cursor: 'pointer',
          width: '100%',
          fontSize: '16px',
          transition: 'background 0.3s'
        }}
      >
        {agregado ? '✓ Agregado al carrito' : 'Agregar al Carrito'}
      </button>
    </div>
  );
};
 
export default ProductDetail;