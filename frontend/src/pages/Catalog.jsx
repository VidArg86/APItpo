import React, { useEffect, useState } from 'react';
import Sidebar from '../components/Sidebar';
import ProductCard from '../components/ProductCard';

// Importaciones actualizadas según tu VS Code (png y svg)
import bannerImage from '../assets/bannerCatProduct.png';
import iconTodos from '../assets/todos.svg';
import iconPanificados from '../assets/panificados.svg'; 
import iconTortas from '../assets/tortas.svg';
import iconCafes from '../assets/cafes.svg';
import iconBebidas from '../assets/bebidas.svg';
import iconCombos from '../assets/combos.svg';
import iconPromos from '../assets/promos.svg';

// Iconos del footer de beneficios
import enviosIcon from '../assets/envios.png';
import productosIcon from '../assets/productoss.png';
import pedidosIcon from '../assets/pedidoss.png';
import retiroIcon from '../assets/retiro.png';

import '../styles/catalog.css';

const Catalog = () => {
  const [productos, setProductos] = useState([]);
  const [cargando, setCargando] = useState(true);

  useEffect(() => {
    const fetchProductos = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await fetch('http://localhost:8080/api/productos', {
          headers: token ? { 'Authorization': `Bearer ${token}` } : {}
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
  }, []);

  return (
    <div style={{ padding: '2rem', maxWidth: '1400px', margin: '0 auto' }}>
      
      {/* Banner Principal */}
      <div className="catalog-banner">
        <img src={bannerImage} alt="Banner Catálogo" />
      </div>

      {/* Navegación de Categorías */}
      <div className="category-pills">
        <button className="pill active">
          <img src={iconTodos} alt="Todos" /> Todos
        </button>
        <button className="pill">
          <img src={iconPanificados} alt="Panificados" /> Panificados
        </button>
        <button className="pill">
          <img src={iconTortas} alt="Tortas" /> Tortas
        </button>
        <button className="pill">
          <img src={iconCafes} alt="Cafés" /> Cafés
        </button>
        <button className="pill">
          <img src={iconBebidas} alt="Bebidas" /> Bebidas
        </button>
        {/* Agregamos los que faltaban */}
        <button className="pill">
          <img src={iconCombos} alt="Combos" /> Combos
        </button>
        <button className="pill">
          <img src={iconPromos} alt="Promos" /> Promos
        </button>
      </div>

      {/* Contenido Principal */}
      <div className="catalog-layout">
        <Sidebar />
        
        <div className="product-grid">
          {cargando ? (
            <p>Cargando productos...</p>
          ) : productos.length > 0 ? (
            productos.map(prod => (
              <ProductCard key={prod.id} product={prod} />
            ))
          ) : (
            <p style={{ gridColumn: "1 / -1", textAlign: "center", marginTop: "2rem" }}>
              No hay productos disponibles por el momento.
            </p>
          )}
        </div>
      </div>

      {/* Footer de Beneficios */}
      <div className="features-section">
        <div className="feature-item">
          <img src={enviosIcon} alt="Envíos en el día" className="feature-icon" />
          <div className="feature-text">
            <h4>Envíos en el día</h4>
            <p>Recibí tu pedido rápido y fresco.</p>
          </div>
        </div>
        <div className="feature-divider"></div>
        
        <div className="feature-item">
          <img src={productosIcon} alt="Productos frescos" className="feature-icon" />
          <div className="feature-text">
            <h4>Productos frescos</h4>
            <p>Elaboramos todos los días.</p>
          </div>
        </div>
        <div className="feature-divider"></div>

        <div className="feature-item">
          <img src={pedidosIcon} alt="Pedidos personalizados" className="feature-icon" />
          <div className="feature-text">
            <h4>Pedidos personalizados</h4>
            <p>Tortas y box a tu medida.</p>
          </div>
        </div>
        <div className="feature-divider"></div>

        <div className="feature-item">
          <img src={retiroIcon} alt="Retiro en tienda" className="feature-icon" />
          <div className="feature-text">
            <h4>Retiro en tienda</h4>
            <p>Pasá a buscar tu pedido.</p>
          </div>
        </div>
      </div>

    </div>
  );
};

export default Catalog;