import React from 'react';
import { useCart } from "../hooks/useContext/CartContext";
const ProductCard = ({ product }) => {
  const { addToCart } = useCart();

  return (
    <div className="product-card">
      {/* Si tienes imágenes reales en la BD, cambias este div por un <img> */}
      <div className="product-img-placeholder"></div>
      
      <div className="product-info">
        <h3>{product.nombre}</h3>
        <span className="price">${product.precio.toLocaleString('es-AR')}</span>
        
        <div className="availability">
          <span className="dot"></span> Disponible hoy
        </div>

        <button 
          className="btn-add" 
          onClick={() => addToCart(product)}
          title="Agregar al carrito"
        >
          +
        </button>
      </div>
    </div>
  );
};

export default ProductCard;