import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";
import { CartProvider } from "./hooks/useContext/CartContext"; 

import Catalog from "./pages/Catalog";
import Login from "./pages/Login";
import Register from "./pages/Register";
import ProductDetail from "./pages/ProductDetail"; 

function App() {
  return (
    <CartProvider> 
      <Router>
        <Navbar />
        <Routes>
          {/* Vista temporal para Inicio */}
          <Route path="/" element={<div style={{ padding: '4rem', textAlign: 'center' }}><h2>Vista Inicio (En construcción)</h2></div>} /> 
          
          {/* Ahora el Catálogo está en su propia ruta */}
          <Route path="/productos" element={<Catalog />} /> 
          
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/producto/:id" element={<ProductDetail />} />
        </Routes>
      </Router>
    </CartProvider>
  );
}

export default App;