import React, { useState } from 'react';
import { Navbar } from './components/Navbar';
import { Catalogo } from './components/Catalogo';
import { Login } from './components/Login';
import { CarritoDetalle } from './components/CarritoDetalle';

function App() {
  // Manejo de pantallas simple por estado
  const [currentView, setCurrentView] = useState('catalogo');

  const renderView = () => {
    switch (currentView) {
      case 'login':
        return <Login />;
      case 'carrito':
        return <CarritoDetalle />;
      case 'catalogo':
      default:
        return <Catalogo />;
    }
  };

  return (
    <div>
      <Navbar setCurrentView={setCurrentView} />
      <main style={{ padding: '20px' }}>
        {renderView()}
      </main>
    </div>
  );
}

export default App;