import React from 'react';

const Sidebar = () => {
  return (
    <aside className="sidebar">
      <div>
        <h4>Buscar producto</h4>
        <input type="text" placeholder="Ej.: medialunas, latte..." />
      </div>

      <div>
        <h4>Categorías</h4>
        <select>
          <option>Todas las categorías</option>
          <option>Panificados</option>
          <option>Tortas</option>
          <option>Cafés</option>
        </select>
      </div>

      <div>
        <h4>Rango de precio</h4>
        <div className="price-range">
          <input type="text" placeholder="$0" />
          <span>-</span>
          <input type="text" placeholder="$10.000" />
        </div>
      </div>

      <div>
        <h4>Disponibilidad</h4>
        <label style={{ display: 'flex', gap: '8px', alignItems: 'center' }}>
          <input type="checkbox" defaultChecked />
          <span className="dot"></span> Disponible hoy
        </label>
        <label style={{ display: 'flex', gap: '8px', alignItems: 'center', marginTop: '8px' }}>
          <input type="checkbox" />
          Solo ver disponibles
        </label>
      </div>

      <div>
        <h4>Ordenar por</h4>
        <select>
          <option>Más vendidos</option>
          <option>Menor precio</option>
          <option>Mayor precio</option>
        </select>
      </div>

      <button className="btn-limpiar">↺ Limpiar filtros</button>
    </aside>
  );
};

export default Sidebar;