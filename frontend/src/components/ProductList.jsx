// Ruta: src/components/ProductList.jsx (Fragmento del return)
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom'; // 1. IMPORTANTE: Importar Link

// ... todo el resto de tu código useEffect y estados queda igual ...

return (
  <div>
    <h2>Catálogo de Productos</h2>
    <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '20px' }}>
      {productos.length > 0 ? productos.map(prod => (
        <div key={prod.id} style={{ border: '1px solid #ccc', padding: '15px', borderRadius: '8px', display: 'flex', flexDirection: 'column', justifyContent: 'between' }}>
          
          {/* 2. Convertimos el nombre en un Link dinámico usando template literals */}
          <Link to={`/producto/${prod.id}`} style={{ textDecoration: 'none', color: 'inherit' }}>
            <h3 style={{ cursor: 'pointer', transition: 'color 0.2s' }} className="product-title-link">
              {prod.nombre}
            </h3>
          </Link>

          <p>{prod.descripcion}</p>
          <p style={{ fontWeight: 'bold', marginTop: 'auto' }}>${prod.precio}</p>
          
          {/* Opcional: Podés meter un enlace de "Ver más" de forma explícita */}
          <Link to={`/producto/${prod.id}`} style={{ margin: '10px 0', fontSize: '14px', color: 'var(--accent)' }}>
            Ver detalles
          </Link>

          <button>Agregar al Carrito</button>
        </div>
      )) : <p>No hay productos disponibles.</p>}
    </div>
  </div>
);