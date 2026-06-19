import React from 'react';
import { useGetProductosQuery } from '../features/productos/productosApi';
import { useAddProductoAlCarritoMutation } from '../features/carrito/carritoApi';
import { useSelector } from 'react-redux';
import { selectCurrentUser } from '../features/auth/authSlice';

export const Catalogo = () => {
  const { data: productos, isLoading, error } = useGetProductosQuery();
  const [agregarAlCarrito] = useAddProductoAlCarritoMutation();
  const usuario = useSelector(selectCurrentUser);

  const carritoId = 1; 

  const handleAgregar = async (productoId) => {
    try {
      await agregarAlCarrito({ carritoId, productoId, cantidad: 1 }).unwrap();
      alert('Producto agregado al carrito');
    } catch (err) {
      alert(`Error: ${err.data}`);
    }
  };

  // ── SECCIÓN MODIFICADA: Render del Spinner de carga ──
  if (isLoading) {
    return (
      <div className="spinner-container">
        <div className="spinner"></div>
        <p className="spinner-text">Cargando las delicias de la panadería...</p>
      </div>
    );
  }

  if (error) return <p style={{ textAlign: 'center', color: '#d9534f' }}>Hubo un error al cargar el catálogo.</p>;

  return (
    <div>
      <h2>Catálogo de Productos</h2>
      <div style={{ display: 'flex', gap: '20px', flexWrap: 'wrap' }}>
        {productos?.map(prod => (
          <div key={prod.id} style={{ border: '1px solid #ccc', padding: '10px', borderRadius: '5px', minWidth: '200px' }}>
            <h3>{prod.nombre}</h3>
            <p>{prod.descripcion}</p>
            <p>Precio: ${prod.precio}</p>
            <p>Stock disponible: {prod.stock}</p>
            
            {/* Si no está logueado, usuario es null. Se oculta el botón de forma segura sin romper la vista */}
            {(usuario?.rol === 'CONSUMIDOR' || usuario?.rol === 'ROLE_CONSUMIDOR') && (
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