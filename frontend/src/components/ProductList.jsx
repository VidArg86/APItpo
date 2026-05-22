import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const ProductList = () => {
  const [productos, setProductos] = useState([]);
  const [cargando, setCargando] = useState(true);

  // Se ejecuta una sola vez al cargar la pantalla
  useEffect(() => {
    const fetchProductos = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await fetch('http://localhost:8080/api/productos', {
          headers: token
            ? { 'Authorization': `Bearer ${token}` } 
            : {}
        });
        
        if (response.ok) {
          const data = await response.json();
          setProductos(data);
        }
      } catch (error) {
        console.error('Error fetching productos:', error);
      } finally {
        setCargando(false);
      }
    };

    fetchProductos();
  }, []); // Array vacío para evitar bucles infinitos

  if (cargando) return <h2>Cargando productos...</h2>;

  return (
    <div>
      <h2>Catálogo de Productos</h2>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '20px' }}>
        {productos.length > 0 ? (
          productos.map(prod => (
            <div key={prod.id} style={{ border: '1px solid #ccc', padding: '15px', borderRadius: '8px', display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
              
              {/* Enlace dinámico en el título del producto */}
              <Link to={`/producto/${prod.id}`} style={{ textDecoration: 'none', color: 'inherit' }}>
                <h3 style={{ cursor: 'pointer', margin: '0 0 10px 0' }}>
                  {prod.nombre}
                </h3>
              </Link>

              <p>{prod.descripcion}</p>
              <p style={{ fontWeight: 'bold', marginTop: 'auto', paddingTop: '10px' }}>${prod.precio}</p>
              
              {/* Enlace explícito de "Ver detalles" */}
              <Link to={`/producto/${prod.id}`} style={{ margin: '10px 0', fontSize: '14px', color: '#aa3bff', textDecoration: 'none', fontWeight: 'bold' }}>
                Ver detalles →
              </Link>

              <button style={{ cursor: 'pointer' }}>Agregar al Carrito</button>
            </div>
          ))
        ) : (
          <p>No hay productos disponibles.</p>
        )}
      </div>
    </div>
  );
};

export default ProductList;