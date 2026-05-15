import { useEffect, useState } from 'react';

const ProductList = () => {
  const [productos, setProductos] = useState([]);
  const [cargando, setCargando] = useState(true);

  // Se ejecuta una sola vez al cargar la pantalla
  useEffect(() => {
    const fetchProductos = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await fetch('http://localhost:8080/api/productos', {
          headers: {
            // Enviamos el token para que tu JwtFilter de Spring Boot lo acepte
            'Authorization': `Bearer ${token}`
          }
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
  }, []); // Array vacío para que no haga un bucle infinito

  if (cargando) return <h2>Cargando productos...</h2>;

  return (
    <div>
      <h2>Catálogo de Productos</h2>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '20px' }}>
        {productos.length > 0 ? productos.map(prod => (
          <div key={prod.id} style={{ border: '1px solid #ccc', padding: '15px', borderRadius: '8px' }}>
            {/* Si tenés lógica de imágenes, la ponemos acá */}
            <h3>{prod.nombre}</h3>
            <p>{prod.descripcion}</p>
            <p style={{ fontWeight: 'bold' }}>${prod.precio}</p>
            <button>Agregar al Carrito</button>
          </div>
        )) : <p>No hay productos disponibles.</p>}
      </div>
    </div>
  );
};

export default ProductList;