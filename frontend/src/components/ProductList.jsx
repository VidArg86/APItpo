// Ruta: src/components/ProductList.jsx
//
// Muestra el catálogo completo de productos.
// Hace un fetch a la API cuando carga la pantalla y guarda los productos en estado.
// Cada producto tiene un botón "Agregar al Carrito" que usa el contexto del carrito.
 
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useCart } from '../hooks/useContext/CartContext';
// Importaciones de Redux
import { useSelector, useDispatch } from 'react-redux';
import { toggleFavorito } from '../store/favoritosSlice';

const ProductList = () => {
  const [productos, setProductos] = useState([]);
  const [cargando, setCargando] = useState(true);

  const { addToCart } = useCart();
  
  // Configuración de Redux para la lista
  const dispatch = useDispatch();
  const favoritos = useSelector((state) => state.favoritos.items);

  useEffect(() => {
    const fetchProductos = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/productos');
        if (response.ok) {
          const data = await response.json();
          setProductos(data);
        }
      } catch (err) {
        console.error('Error fetching productos:', err);
      } finally {
        setCargando(false);
      }
    };
    fetchProductos();
  }, []);

  if (cargando) return <h2>Cargando catálogo...</h2>;

  return (
    <div style={{ maxWidth: '1000px', margin: '0 auto', padding: '20px' }}>
      <h1>Catálogo de Productos</h1>
      <div style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fill, minmax(250px, 1fr))',
        gap: '20px',
        marginTop: '20px'
      }}>
        {productos.map((producto) => {
          // Verificamos si este producto en particular está en la lista de favoritos
          const esFavorito = favoritos.some((item) => item.id === producto.id);

          return (
            <div key={producto.id} style={{
              border: '1px solid #e5e4e7',
              borderRadius: '8px',
              padding: '16px',
              textAlign: 'left',
              position: 'relative',
              display: 'flex',
              flexDirection: 'column',
              justifyContent: 'space-between'
            }}>
              {/* Botón de favorito flotante en la tarjeta */}
              <button
                onClick={() => dispatch(toggleFavorito(producto))}
                style={{
                  position: 'absolute',
                  top: '10px',
                  right: '10px',
                  background: 'none',
                  border: 'none',
                  fontSize: '22px',
                  cursor: 'pointer',
                  zIndex: 2
                }}
              >
                {esFavorito ? '❤️' : '🤍'}
              </button>

              <div>
                <h3 style={{ margin: '0 0 10px 0', paddingRight: '30px' }}>{producto.nombre}</h3>
                <p style={{ color: '#6b6375', fontSize: '14px', margin: '0 0 10px 0' }}>{producto.descripcion}</p>
                <p style={{ fontWeight: 'bold', fontSize: '18px', margin: '0 0 15px 0' }}>${producto.precio}</p>
              </div>

              <div style={{ display: 'flex', gap: '10px', marginTop: 'auto' }}>
                <Link to={`/producto/${producto.id}`} style={{
                  flex: 1,
                  textAlign: 'center',
                  padding: '8px',
                  background: '#f4f3ec',
                  color: '#08060d',
                  textDecoration: 'none',
                  borderRadius: '4px',
                  fontSize: '14px'
                }}>
                  Ver detalle
                </Link>
                <button onClick={() => addToCart(producto)} style={{
                  flex: 1,
                  padding: '8px',
                  background: '#aa3bff',
                  color: 'white',
                  border: 'none',
                  borderRadius: '4px',
                  cursor: 'pointer',
                  fontSize: '14px'
                }}>
                  Añadir 🛒
                </button>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default ProductList;