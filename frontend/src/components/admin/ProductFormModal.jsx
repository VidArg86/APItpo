import { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import ProductImage from './ProductImage';
import { deleteImagenProducto } from '../../store/productsSlice';

const emptyForm = { nombre: '', descripcion: '', precio: '', stock: '', categoriaIds: [] };

const ProductFormModal = ({ producto, categorias, saving, onClose, onSubmit }) => {
  const dispatch = useDispatch();
  const [form, setForm] = useState(emptyForm);
  const [nuevasImagenes, setNuevasImagenes] = useState([]);
  const [previews, setPreviews] = useState([]);

  const isEdit = Boolean(producto);

  useEffect(() => {
    if (producto) {
      setForm({
        nombre: producto.nombre || '',
        descripcion: producto.descripcion || '',
        precio: producto.precio ?? '',
        stock: producto.stock ?? '',
        categoriaIds: (producto.categorias || []).map((c) => c.id),
      });
    } else {
      setForm(emptyForm);
    }
    setNuevasImagenes([]);
    setPreviews([]);
  }, [producto]);

  const handleChange = (field) => (e) => {
    setForm((prev) => ({ ...prev, [field]: e.target.value }));
  };

  const toggleCategoria = (id) => {
    setForm((prev) => ({
      ...prev,
      categoriaIds: prev.categoriaIds.includes(id)
        ? prev.categoriaIds.filter((c) => c !== id)
        : [...prev.categoriaIds, id],
    }));
  };

  const handleFiles = (files) => {
    const list = Array.from(files);
    setNuevasImagenes((prev) => [...prev, ...list]);
    setPreviews((prev) => [...prev, ...list.map((f) => URL.createObjectURL(f))]);
  };

  const removeNuevaImagen = (index) => {
    setNuevasImagenes((prev) => prev.filter((_, i) => i !== index));
    setPreviews((prev) => prev.filter((_, i) => i !== index));
  };

  const handleRemoveExistingImagen = (imagenId) => {
    dispatch(deleteImagenProducto({ productoId: producto.id, imagenId }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({
      nombre: form.nombre,
      descripcion: form.descripcion,
      precio: Number(form.precio),
      stock: Number(form.stock),
      categoriaIds: form.categoriaIds,
      previousCategoriaIds: (producto?.categorias || []).map((c) => c.id),
      imagenes: nuevasImagenes,
    });
  };

  return (
    <div className="admin-modal-overlay" onClick={onClose}>
      <div className="admin-modal" onClick={(e) => e.stopPropagation()}>
        <div className="admin-modal-header">
          <h2>{isEdit ? 'Editar producto' : 'Nuevo producto'}</h2>
          <button className="admin-modal-close" onClick={onClose} aria-label="Cerrar">✕</button>
        </div>

        <form onSubmit={handleSubmit} className="admin-form">
          <div className="admin-form-row">
            <label>
              Nombre
              <input type="text" value={form.nombre} onChange={handleChange('nombre')} required />
            </label>
            <label>
              Precio
              <input type="number" step="0.01" min="0" value={form.precio} onChange={handleChange('precio')} required />
            </label>
            <label>
              Stock
              <input type="number" min="0" value={form.stock} onChange={handleChange('stock')} required />
            </label>
          </div>

          <label>
            Descripción
            <textarea rows={4} value={form.descripcion} onChange={handleChange('descripcion')} />
          </label>

          <div>
            <p className="admin-form-label">Categorías</p>
            <div className="admin-chip-group">
              {categorias.map((cat) => (
                <button
                  type="button"
                  key={cat.id}
                  className={`admin-chip${form.categoriaIds.includes(cat.id) ? ' active' : ''}`}
                  onClick={() => toggleCategoria(cat.id)}
                >
                  {cat.nombre}
                </button>
              ))}
            </div>
          </div>

          <div>
            <p className="admin-form-label">Fotos del producto</p>
            <label className="admin-dropzone">
              <input
                type="file"
                accept="image/*"
                multiple
                hidden
                onChange={(e) => handleFiles(e.target.files)}
              />
              <span>📷 Hacé clic para subir fotos</span>
            </label>

            <div className="admin-image-preview-grid">
              {isEdit && (producto.imagenes || []).map((img) => (
                <div className="admin-image-preview" key={img.id}>
                  <ProductImage productoId={producto.id} imagenId={img.id} alt={producto.nombre} className="admin-preview-img" />
                  <button type="button" onClick={() => handleRemoveExistingImagen(img.id)} title="Eliminar imagen">✕</button>
                </div>
              ))}
              {previews.map((src, i) => (
                <div className="admin-image-preview" key={src}>
                  <img src={src} alt="" className="admin-preview-img" />
                  <button type="button" onClick={() => removeNuevaImagen(i)} title="Quitar">✕</button>
                </div>
              ))}
            </div>
          </div>

          <div className="admin-modal-actions">
            <button type="button" className="admin-btn-secondary" onClick={onClose}>Cancelar</button>
            <button type="submit" className="admin-btn-primary" disabled={saving}>
              {saving ? 'Guardando...' : 'Guardar producto'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ProductFormModal;
