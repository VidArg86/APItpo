import { Link, useNavigate } from 'react-router-dom';
import { useCart } from '../hooks/useContext/CartContext';
import { toggleFavorito } from '../store/favoritosSlice';
import { useDispatch, useSelector } from "react-redux";
import faheart from '../assets/heart-solid-full.svg';
import faheartn from '../assets/heart-regular-full.svg'

const ProductCard = ({ product }) => {
    const { addToCart } = useCart();
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const isLoggedIn = !!localStorage.getItem('token');

    // FIX 1: Use 'product.id' instead of just 'id'
    const esFavorito = useSelector((state) =>
        isLoggedIn && state.favoritos.items.some((item) => item.id === Number(product.id))
    );

    // Helper function to handle the favorite click
    const handleToggleFavorite = (e) => {
        e.preventDefault(); // Prevents the Link from triggering (if it bubbles)
        e.stopPropagation(); // Stops the click event from reaching parent elements

        if (!isLoggedIn) {
            navigate('/login');
            return;
        }

        // FIX 2: Use 'product' instead of 'producto'
        dispatch(toggleFavorito(product));
    };

    return (
        <div className="product-card">

            {/* Container added to hold both the image and the button.
        'position: relative' allows the absolute button to map to this box.
      */}
            <div className="product-image-container" style={{ position: 'relative' }}>
                <Link to={`/producto/${product.id}`} className="product-card-link">
                    {product.imagen ? (
                        <img src={product.imagen} alt={product.nombre} className="product-img" />
                    ) : (
                        <div className="product-img-placeholder" style={{ height: '200px', backgroundColor: '#f0f0f0' }}>
                            {/* Placeholder styling fallback */}
                        </div>
                    )}
                </Link>

                {/* FIX 3: Extracted button outside the Link and positioned it absolutely */}
                <button  type="reset"
                    onClick={handleToggleFavorite}
                    style={{
                        position: 'absolute',
                        top: '5px',
                        right: '5px',
                        background: 'none',
                        border: 'none',
                        cursor: 'pointer',
                        padding: '5px',
                        zIndex: 30,
                        transition: 'transform 0.2s',
                         // Helps visibility over images
                    }}
                    title={esFavorito ? "Quitar de favoritos" : "Agregar a favoritos"}
                >
                {esFavorito ? (
                    <img src={faheart} alt="Favorito" style={{ width: '24px', height: '24px' }} />
                ) : (
                    <img src={faheartn} alt="No favorito" style={{ width: '24px', height: '24px', fill:"pink" }} />
                )}
                </button >
            </div>

            <div className="product-info">
                <Link to={`/producto/${product.id}`} className="product-card-link">
                    <h3>{product.nombre}</h3>
                </Link>
                <span className="price">${product.precio.toLocaleString('es-AR')}</span>

                <div className="availability">
                    <span className="dot" style={{ color: 'green' }}></span> Disponible hoy
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
