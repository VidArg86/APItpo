// Ruta: src/components/Checkout.jsx
//
// Esta es la página de confirmación de compra.
// Muestra el resumen de lo que el usuario está por pagar
// y tiene el botón final para confirmar.
// Por ahora el confirmar solo vacía el carrito y vuelve al inicio.
// Más adelante acá se conectará con la API para crear la orden real.
 
import { Link, useNavigate } from 'react-router-dom';
import { useCart } from '../hooks/useContext/CartContext';
 
const Checkout = () => {
  // Necesitamos los items y el total para mostrar el resumen,
  // y clearCart para vaciar el carrito al confirmar.
  const { cartItems, total, clearCart } = useCart();
  const navigate = useNavigate();
 
  const handleConfirmar = () => {
    // TODO: acá más adelante hacemos el fetch a la API para crear la orden
    alert('¡Compra confirmada!');
    clearCart();       // Vaciamos el carrito
    navigate('/');     // Mandamos al usuario al catálogo
  };
 
  return (
    <div style={{ padding: '2rem', maxWidth: '700px', margin: '0 auto' }}>
      <h1>Confirmar Compra</h1>
 
      {/* Si llegaron acá sin productos en el carrito, mostramos aviso */}
      {cartItems.length === 0 ? (
        <p>No hay productos en el carrito.</p>
      ) : (
        <>
          <div style={{ marginBottom: '1.5rem' }}>
            <h2>Resumen del pedido</h2>
 
            {/* Lista de productos con nombre, cantidad y subtotal */}
            {cartItems.map(item => (
              <div
                key={item.id}
                style={{
                  display: 'flex',
                  justifyContent: 'space-between',
                  padding: '0.5rem 0',
                  borderBottom: '1px solid #eee'
                }}
              >
                <span>{item.nombre} x{item.quantity}</span>
                <span style={{ fontWeight: 'bold' }}>
                  ${(item.precio * item.quantity).toLocaleString('es-AR')}
                </span>
              </div>
            ))}
 
            {/* Total final */}
            <div style={{
              display: 'flex',
              justifyContent: 'space-between',
              padding: '1rem 0',
              fontSize: '1.2rem',
              fontWeight: 'bold'
            }}>
              <span>Total:</span>
              <span>${total.toLocaleString('es-AR')}</span>
            </div>
          </div>
        </>
      )}
 
      {/* Botones: volver al carrito o confirmar la compra */}
      <div style={{ display: 'flex', gap: '1rem' }}>
        <Link
          to="/cart"
          style={{
            backgroundColor: '#333',
            color: 'white',
            padding: '0.5rem 1rem',
            borderRadius: '4px',
            textDecoration: 'none'
          }}
        >
          Volver al carrito
        </Link>
 
        {/* Solo mostramos el botón confirmar si hay productos */}
        {cartItems.length > 0 && (
          <button
            onClick={handleConfirmar}
            style={{
              backgroundColor: '#4CAF50',
              color: 'white',
              padding: '0.5rem 1rem',
              border: 'none',
              borderRadius: '4px',
              cursor: 'pointer'
            }}
          >
            Confirmar compra
          </button>
        )}
      </div>
    </div>
  );
};
 
export default Checkout;