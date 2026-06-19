import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Provider } from "react-redux";

// Make sure there are NO curly braces around 'store' here!
import store from "./store/store";

// ... rest of your App.js code


import Navbar from "./components/Navbar";
import { CartProvider } from "./hooks/useContext/CartContext";

import Catalog from "./pages/Catalog";
import Login from "./pages/Login";
import Register from "./pages/Register";
import ProductDetail from "./pages/ProductDetail";
import Cart from "./components/Cart";
import Checkout from "./pages/Checkout";
import CartRedux from "./components/CartRedux";

function App() {
    return (
        // 3. Wrap your entire app inside the Redux Provider and pass the store to it
        <Provider store={store}>
            <CartProvider>
                <Router>
                    <Navbar />
                    <Routes>
                        <Route path="/" element={<div style={{ padding: '4rem', textAlign: 'center' }}><h2>Vista Inicio (En construcción)</h2></div>} />
                        <Route path="/productos" element={<Catalog />} />
                        <Route path="/login" element={<Login />} />
                        <Route path="/register" element={<Register />} />
                        <Route path="/producto/:id" element={<ProductDetail />} />
                        <Route path="/cart" element={<Cart />} />
                        <Route path="/checkout" element={<Checkout />} />
                        <Route path="/cart-redux" element={<CartRedux />} />
                    </Routes>
                </Router>
            </CartProvider>
        </Provider>
    );
}

export default App;