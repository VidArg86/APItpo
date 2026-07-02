import React from 'react';

const Sidebar = ({
  search,
  onSearchChange,
  categorias,
  categoriaId,
  onCategoriaChange,
  minPrice,
  maxPrice,
  onMinPriceChange,
  onMaxPriceChange,
  soloDisponibles,
  onSoloDisponiblesChange,
  sortBy,
  onSortByChange,
  onClear,
}) => {
  return (
    <aside className="sidebar">
      <div>
        <h4>Buscar producto</h4>
        <input
          type="text"
          placeholder="Ej.: medialunas, latte..."
          value={search}
          onChange={(e) => onSearchChange(e.target.value)}
        />
      </div>

      <div>
        <h4>Categorías</h4>
        <select value={categoriaId} onChange={(e) => onCategoriaChange(e.target.value)}>
          <option value="">Todas las categorías</option>
          {categorias.map((cat) => (
            <option key={cat.id} value={cat.id}>{cat.nombre}</option>
          ))}
        </select>
      </div>

      <div>
        <h4>Rango de precio</h4>
        <div className="price-range">
          <input
            type="number"
            min="0"
            placeholder="$0"
            value={minPrice}
            onChange={(e) => onMinPriceChange(e.target.value)}
          />
          <span>-</span>
          <input
            type="number"
            min="0"
            placeholder="$10.000"
            value={maxPrice}
            onChange={(e) => onMaxPriceChange(e.target.value)}
          />
        </div>
      </div>

      <div>
        <h4>Disponibilidad</h4>
        <label style={{ display: 'flex', gap: '8px', alignItems: 'center' }}>
          <input
            type="checkbox"
            checked={soloDisponibles}
            onChange={(e) => onSoloDisponiblesChange(e.target.checked)}
          />
          Solo ver disponibles
        </label>
      </div>

      <div>
        <h4>Ordenar por</h4>
        <select value={sortBy} onChange={(e) => onSortByChange(e.target.value)}>
          <option value="relevancia">Más vendidos</option>
          <option value="precio-asc">Menor precio</option>
          <option value="precio-desc">Mayor precio</option>
        </select>
      </div>

      <button className="btn-limpiar" onClick={onClear}>↺ Limpiar filtros</button>
    </aside>
  );
};

export default Sidebar;
