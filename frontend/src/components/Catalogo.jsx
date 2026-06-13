import React from 'react';
import { useGetProductosQuery } from '../features/productos/productosApi';
import { useAddProductoAlCarritoMutation } from '../features/carrito/carritoApi';
import { useSelector } from 'react-redux';
import { selectCurrentUser } from '../features/auth/authSlice';

export const Catalogo = () => {
  const { data: productos, isLoading, error } = useGetProductosQuery();
  const [agregarAlCarrito] = useAddProductoAlCarritoMutation();
  const usuario = useSelector(selectCurrentUser);

  // Hardcodeamos un ID de carrito para el ejemplo (en tu app real vendría del usuario logueado)
  const carritoId = 1; 

  const handleAgregar = async (productoId) => {
    try {
      await agregarAlCarrito({ carritoId, productoId, cantidad: 1 }).unwrap();
      alert('Producto agregado al carrito');
    } catch (err) {
      alert(`Error: ${err.data}`); // Captura el InsufficientStockException de tu backend
    }
  };

  if (isLoading) return <p>Cargando productos...</p>;
  if (error) return <p>Hubo un error al cargar el catálogo.</p>;

  return (
    <div>
      <h2>Catálogo de Productos</h2>
      <div style={{ display: 'flex', gap: '20px' }}>
        {productos?.map(prod => (
          <div key={prod.id} style={{ border: '1px solid #ccc', padding: '10px' }}>
            <h3>{prod.nombre}</h3>
            <p>{prod.descripcion}</p>
            <p>Precio: ${prod.precio}</p>
            <p>Stock disponible: {prod.stock}</p>
            
            {/* Solo permitimos comprar si es un CONSUMIDOR */}
            {usuario?.rol === 'CONSUMIDOR' && (
              <button onClick={() => handleAgregar(prod.id)} disabled={prod.stock === 0}>
                {prod.stock === 0 ? 'Sin Stock' : 'Agregar al Carrito'}
              </button>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};