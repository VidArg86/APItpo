// Ruta: src/components/ProductDetail.jsx
//
// Muestra el detalle de un producto específico.
// El :id de la URL lo leemos con useParams y lo usamos para hacer el fetch.
// También tiene el botón de agregar al carrito con feedback visual
// (cambia a "✓ Agregado" por 2 segundos para confirmarle al usuario).
 
import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { useCart } from '../hooks/useContext/CartContext';
// Importaciones de Redux
import { useSelector, useDispatch } from 'react-redux';
import { toggleFavorito } from '../store/favoritosSlice';
 
const ProductDetail = () => {
  const { id } = useParams();
  const [producto, setProducto] = useState(null);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState('');
  const [agregado, setAgregado] = useState(false);
  const { addToCart } = useCart();

  // Configuración de Redux
  const dispatch = useDispatch();
  // Buscamos en el store si este producto ID ya es favorito
  const esFavorito = useSelector((state) =>
    state.favoritos.items.some((item) => item.id === Number(id))
  );
 
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
  }, [id]);
 
  const handleAgregarAlCarrito = () => {
    addToCart(producto);
    setAgregado(true);
    setTimeout(() => setAgregado(false), 2000);
  };
 
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
      <Link to="/" style={{ textDecoration: 'none', color: '#aa3bff', fontWeight: 'bold' }}>
        ← Volver al catálogo
      </Link>
 
      {/* Título y Botón de Favorito alineados */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginTop: '20px' }}>
        <h2 style={{ margin: 0 }}>{producto.nombre}</h2>
        <button
          onClick={() => dispatch(toggleFavorito(producto))}
          style={{
            background: 'none',
            border: 'none',
            fontSize: '28px',
            cursor: 'pointer',
            padding: '5px',
            transition: 'transform 0.2s'
          }}
          title={esFavorito ? "Quitar de favoritos" : "Agregar a favoritos"}
        >
          {esFavorito ? '❤️' : '🤍'}
        </button>
      </div>

      <p style={{ fontSize: '18px', color: '#555', margin: '15px 0' }}>{producto.descripcion}</p>
      <p style={{ fontSize: '24px', fontWeight: 'bold' }}>${producto.precio}</p>
 
      {producto.stock !== undefined && (
        <p>Stock disponible: {producto.stock} unidades</p>
      )}
 
      <button
        onClick={handleAgregarAlCarrito}
        style={{
          marginTop: '20px',
          padding: '10px 20px',
          background: agregado ? '#4CAF50' : '#aa3bff',
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