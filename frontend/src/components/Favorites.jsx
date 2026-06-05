import { Link } from 'react-router-dom';
import { useCart } from '../hooks/useContext/CartContext';
// Redux
import { useSelector, useDispatch } from 'react-redux';
import { toggleFavorito } from '../store/favoritosSlice';

const Favorites = () => {
  const dispatch = useDispatch();
  // Obtenemos los productos favoritos del store global
  const favoritos = useSelector((state) => state.favoritos.items);
  const { addToCart } = useCart();

  return (
    <div style={{ padding: '2rem', maxWidth: '900px', margin: '0 auto' }}>
      <h1>Mis Productos Favoritos</h1>

      {favoritos.length === 0 ? (
        <div style={{ textAlign: 'center', marginTop: '40px' }}>
          <p style={{ fontSize: '18px', color: '#666' }}>Aún no has agregado productos a tus favoritos.</p>
          <Link to="/" style={{
            display: 'inline-block',
            marginTop: '20px',
            backgroundColor: '#aa3bff',
            color: 'white',
            padding: '10px 20px',
            borderRadius: '4px',
            textDecoration: 'none'
          }}>
            Ir al catálogo
          </Link>
        </div>
      ) : (
        <div style={{ marginBottom: '2rem' }}>
          {favoritos.map(item => (
            <div
              key={item.id}
              style={{
                display: 'grid',
                gridTemplateColumns: '1fr auto auto',
                gap: '1rem',
                alignItems: 'center',
                padding: '1rem',
                borderBottom: '1px solid #eee'
              }}
            >
              {/* Info del producto guardado */}
              <div style={{ textAlign: 'left' }}>
                <h3 style={{ margin: '0 0 0.5rem 0' }}>
                  <Link to={`/producto/${item.id}`} style={{ color: '#08060d', textDecoration: 'none' }}>
                    {item.nombre}
                  </Link>
                </h3>
                <p style={{ margin: '0', color: '#666' }}>${item.precio}</p>
              </div>

              {/* Acciones rápido: Añadir al carrito */}
              <button
                onClick={() => addToCart(item)}
                style={{
                  padding: '0.5rem 1rem',
                  backgroundColor: '#4CAF50',
                  color: 'white',
                  border: 'none',
                  borderRadius: '4px',
                  cursor: 'pointer'
                }}
              >
                Añadir al Carrito 🛒
              </button>

              {/* Quitar de favoritos */}
              <button
                onClick={() => dispatch(toggleFavorito(item))}
                style={{
                  padding: '0.5rem',
                  background: 'none',
                  border: '1px solid #ff4444',
                  color: '#ff4444',
                  borderRadius: '4px',
                  cursor: 'pointer'
                }}
              >
                Quitar ❤️
              </button>
            </div>
          ))}
          
          <div style={{ display: 'flex', marginTop: '20px' }}>
            <Link to="/" style={{
              backgroundColor: '#333',
              color: 'white',
              padding: '0.5rem 1rem',
              borderRadius: '4px',
              textDecoration: 'none'
            }}>
              Volver al catálogo
            </Link>
          </div>
        </div>
      )}
    </div>
  );
};

export default Favorites;