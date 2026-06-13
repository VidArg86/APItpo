import React from 'react';
import { 
  useGetCarritoByIdQuery, 
  useVaciarCarritoMutation, 
  useCheckoutCarritoMutation 
} from '../features/carrito/carritoApi';

export const CarritoDetalle = () => {
  // Hardcodeamos el ID del carrito para este ejemplo práctico
  const carritoId = 1; 

  // Traemos la info del carrito en tiempo real desde el backend
  const { data: carrito, isLoading, error } = useGetCarritoByIdQuery(carritoId);
  const [vaciarCarrito, { isLoading: isVaciarLoading }] = useVaciarCarritoMutation();
  const [checkoutCarrito, { isLoading: isCheckoutLoading }] = useCheckoutCarritoMutation();

  const handleVaciar = async () => {
    try {
      await vaciarCarrito(carritoId).unwrap();
      alert('Carrito vaciado con éxito');
    } catch (err) {
      alert('Error al vaciar el carrito');
    }
  };

  const handleCheckout = async () => {
    try {
      // El backend devuelve un String plano con el total abonado si todo sale bien
      const mensajeExito = await checkoutCarrito(carritoId).unwrap(); 
      alert(mensajeExito); 
    } catch (err) {
      // Captura excepciones personalizadas como InsufficientStockException
      alert(`Error en el checkout: ${err.data || 'Stock insuficiente'}`); 
    }
  };

  if (isLoading) return <p>Cargando el carrito...</p>;
  if (error) return <p>Error al obtener los datos del carrito (¿Existe el ID?).</p>;

  return (
    <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
      <h2>Tu Carrito de Compras (ID: {carrito?.id})</h2>
      
      {carrito?.productos && carrito.productos.length === 0 ? (
        <p>El carrito está completamente vacío.</p>
      ) : (
        <>
          <ul style={{ listStyleType: 'none', padding: 0 }}>
            {carrito?.productos?.map((item) => (
              <li key={item.id} style={{ padding: '10px 0', borderBottom: '1px solid #eee' }}>
                <strong>{item.producto.nombre}</strong> — {item.cantidad} u. x ${item.producto.precio}
                <span style={{ float: 'right' }}>Subtotal: ${item.cantidad * item.producto.precio}</span>
              </li>
            ))}
          </ul>
          
          <h3 style={{ textAlign: 'right', marginTop: '20px' }}>
            Total General: ${carrito?.precioTotal}
          </h3>

          <div style={{ display: 'flex', gap: '10px', justifyContent: 'flex-end', marginTop: '20px' }}>
            <button 
              onClick={handleVaciar} 
              disabled={isVaciarLoading || isCheckoutLoading}
              style={{ backgroundColor: '#d9534f', color: 'white', padding: '10px' }}
            >
              Vaciar Carrito
            </button>
            <button 
              onClick={handleCheckout} 
              disabled={isCheckoutLoading || isVaciarLoading}
              style={{ backgroundColor: '#5cb85c', color: 'white', padding: '10px' }}
            >
              Finalizar Compra (Checkout)
            </button>
          </div>
        </>
      )}
    </div>
  );
};