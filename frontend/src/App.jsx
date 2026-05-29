// Ruta: src/App.jsx
//
// App.jsx es el punto de entrada de la aplicación.
// Acá definimos las rutas (qué componente mostrar en cada URL)
// y envolvemos todo con CartProvider para que el carrito
// esté disponible en cualquier parte de la app.
 
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { CartProvider } from './hooks/useContext/CartContext';
import Navbar from './components/Navbar';
import ProductList from './components/ProductList';
import Login from './components/Login';
import Register from './components/Register';
import ProductDetail from './components/ProductDetail';
import Cart from './components/Cart';
import Checkout from './components/Checkout';
 
function App() {
  return (
    // CartProvider envuelve todo para que cartItems, addToCart, etc.
    // estén disponibles en Navbar, ProductList, Cart y Checkout.
    <CartProvider>
      <Router>
        <Navbar />
        <div style={{ padding: '20px' }}>
          <Routes>
            <Route path="/register" element={<Register />} />
            <Route path="/" element={<ProductList />} />
            <Route path="/login" element={<Login />} />
            {/* Ruta dinámica: el :id cambia según el producto */}
            <Route path="/producto/:id" element={<ProductDetail />} />
            {/* Rutas nuevas del carrito */}
            <Route path="/cart" element={<Cart />} />
            <Route path="/checkout" element={<Checkout />} />
          </Routes>
        </div>
      </Router>
    </CartProvider>
  );
}
 
export default App;