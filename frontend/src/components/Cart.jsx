// Ruta: src/components/Cart.jsx
//
// Esta es la página del carrito.
// Muestra todos los productos que el usuario agregó,
// permite cambiar cantidades, eliminar items y ver el total.
// Desde acá también puede ir al checkout o volver al catálogo.
 
import { Link } from 'react-router-dom';
import { useCart } from '../hooks/useContext/CartContext';
 
const Cart = () => {
  // Traemos todo lo que necesitamos del contexto del carrito
  const { cartItems, removeFromCart, updateQuantity, total } = useCart();
 
  return (
    <div style={{ padding: '2rem', maxWidth: '900px', margin: '0 auto' }}>
      <h1>Carrito de Compras</h1>
 
      {/* Solo mostramos cuántos productos hay si el carrito no está vacío */}
      {cartItems.length > 0 && (
        <p>Tenés {cartItems.length} producto(s) en el carrito</p>
      )}
 
      {/* Si el carrito está vacío mostramos un mensaje, si no mostramos la lista */}
      {cartItems.length === 0 ? (
        <p>Tu carrito está vacío</p>
      ) : (
        <>
          <div style={{ marginBottom: '2rem' }}>
            {cartItems.map(item => (
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
                {/* Info del producto */}
                <div>
                  <h3 style={{ margin: '0 0 0.5rem 0' }}>{item.nombre}</h3>
                  <p style={{ margin: '0', color: '#666' }}>${item.precio} c/u</p>
                  {/* El subtotal se calcula precio x cantidad */}
                  <p style={{ margin: '0.5rem 0', color: '#aa3bff', fontWeight: 'bold' }}>
                    Subtotal: ${(item.precio * item.quantity).toLocaleString('es-AR')}
                  </p>
                </div>
 
                {/* Botones para aumentar o disminuir la cantidad */}
                {/* Si la cantidad llega a 0, updateQuantity llama a removeFromCart automáticamente */}
                <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                  <button
                    onClick={() => updateQuantity(item.id, item.quantity - 1)}
                    style={{ padding: '4px 10px', cursor: 'pointer', borderRadius: '4px', border: '1px solid #ccc' }}
                  >
                    −
                  </button>
                  <span>{item.quantity}</span>
                  <button
                    onClick={() => updateQuantity(item.id, item.quantity + 1)}
                    style={{ padding: '4px 10px', cursor: 'pointer', borderRadius: '4px', border: '1px solid #ccc' }}
                  >
                    +
                  </button>
                </div>
 
                {/* Botón para eliminar el producto sin importar la cantidad */}
                <button
                  onClick={() => removeFromCart(item.id)}
                  style={{
                    padding: '0.5rem',
                    background: 'none',
                    border: '1px solid #ff4444',
                    color: '#ff4444',
                    borderRadius: '4px',
                    cursor: 'pointer'
                  }}
                >
                  Eliminar
                </button>
              </div>
            ))}
          </div>
 
          {/* Total general del carrito */}
          <div style={{
            display: 'flex',
            justifyContent: 'flex-end',
            padding: '1rem',
            backgroundColor: '#f9f9f9',
            borderRadius: '4px',
            marginBottom: '1rem',
            fontSize: '1.2rem',
            fontWeight: 'bold'
          }}>
            Total: ${total.toLocaleString('es-AR')}
          </div>
        </>
      )}
 
      {/* Botones de navegación */}
      <div style={{ display: 'flex', gap: '1rem' }}>
        <Link
          to="/"
          style={{
            backgroundColor: '#333',
            color: 'white',
            padding: '0.5rem 1rem',
            borderRadius: '4px',
            cursor: 'pointer',
            textDecoration: 'none'
          }}
        >
          Seguir comprando
        </Link>
 
        {/* El botón de pagar solo aparece si hay algo en el carrito */}
        {cartItems.length > 0 && (
          <Link
            to="/checkout"
            style={{
              backgroundColor: '#4CAF50',
              color: 'white',
              padding: '0.5rem 1rem',
              borderRadius: '4px',
              cursor: 'pointer',
              textDecoration: 'none'
            }}
          >
            Pagar
          </Link>
        )}
      </div>
    </div>
  );
};
 
export default Cart;