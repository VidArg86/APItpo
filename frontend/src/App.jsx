// Ruta: src/App.jsx
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import ProductList from './components/ProductList';
import Login from './components/Login';
import Register from './components/Register';
import ProductDetail from './components/ProductDetail'; // 1. Importamos el nuevo componente

function App() {
  return (
    <Router>
      <Navbar />
      <div style={{ padding: '20px' }}>
        <Routes>
          <Route path="/register" element={<Register />} />
          <Route path="/" element={<ProductList />} />
          <Route path="/login" element={<Login />} />
          {/* 2. Añadimos la ruta dinámica con el parámetro :id */}
          <Route path="/producto/:id" element={<ProductDetail />} />
          {/* Más adelante agregaremos las rutas del carrito y panel de admin */}
        </Routes>
      </div>
    </Router>
  );
}

export default App;