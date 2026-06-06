import React from 'react';
import { Link } from 'react-router-dom';
import { useCart } from '../hooks/useContext/CartContext';

const ProductCard = ({ product }) => {
  const { addToCart } = useCart();

  return (
    <div className="product-card">
      <Link to={`/producto/${product.id}`} className="product-card-link">
        {product.imagen ? (
          <img src={product.imagen} alt={product.nombre} className="product-img" />
        ) : (
          <div className="product-img-placeholder" />
        )}
      </Link>

      <div className="product-info">
        <Link to={`/producto/${product.id}`} className="product-card-link">
          <h3>{product.nombre}</h3>
        </Link>
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