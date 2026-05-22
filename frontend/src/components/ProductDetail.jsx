// Ruta: src/components/ProductDetail.jsx
import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';

const ProductDetail = () => {
  const { id } = useParams(); // Captura el id de la URL dinámica
  const [producto, setProducto] = useState(null);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchProductoDetalle = async () => {
      try {
        const token = localStorage.getItem('token');
        // Llamada al endpoint dinámico de tu backend (ej: /api/productos/5)
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

    fetchProductoDetalle();
  }, [id]); // Se vuelve a ejecutar si el id cambia

  if (cargando) return <h2>Cargando detalle del producto...</h2>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;
  if (!producto) return <p>Producto no encontrado.</p>;

  return (
    <div style={{ maxWidth: '600px', margin: '20px auto', padding: '20px', border: '1px solid #ccc', borderRadius: '8px', textAlign: 'left' }}>
      <Link to="/" style={{ textDecoration: 'none', color: 'var(--accent)' }}>← Volver al catálogo</Link>
      <h2 style={{ marginTop: '20px' }}>{producto.nombre}</h2>
      <p style={{ fontSize: '18px', color: '#555', margin: '15px 0' }}>{producto.descripcion}</p>
      <p style={{ fontSize: '24px', fontWeight: 'bold', color: 'var(--text-h)' }}>${producto.precio}</p>
      
      {/* Podés agregar más detalles si tu backend los provee (stock, categoría, etc.) */}
      {producto.stock !== undefined && <p>Stock disponible: {producto.stock} unidades</p>}

      <button style={{
        marginTop: '20px',
        padding: '10px 20px',
        background: 'var(--accent)',
        color: 'white',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer'
      }}>
        Agregar al Carrito
      </button>
    </div>
  );
};

export default ProductDetail;