// src/store/productsSlice.js
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { apiFetch, apiUpload } from '../services/api';

export const fetchProductos = createAsyncThunk(
  'productos/fetchAll',
  async (_, { rejectWithValue }) => {
    try {
      return await apiFetch('/productos');
    } catch (err) {
      return rejectWithValue(err.message);
    }
  }
);

export const fetchCategorias = createAsyncThunk(
  'productos/fetchCategorias',
  async (_, { rejectWithValue }) => {
    try {
      return await apiFetch('/categorias');
    } catch (err) {
      return rejectWithValue(err.message);
    }
  }
);

// Crea el producto, le asocia categorias y sube las imagenes seleccionadas
export const createProducto = createAsyncThunk(
  'productos/create',
  async ({ nombre, descripcion, precio, stock, categoriaIds = [], imagenes = [] }, { rejectWithValue }) => {
    try {
      let producto = await apiFetch('/productos', {
        method: 'POST',
        body: JSON.stringify({ nombre, descripcion, precio, stock }),
      });

      for (const categoriaId of categoriaIds) {
        producto = await apiFetch(`/productos/${producto.id}/categorias/${categoriaId}`, { method: 'POST' });
      }

      for (const file of imagenes) {
        const formData = new FormData();
        formData.append('file', file);
        await apiUpload(`/productos/${producto.id}/imagenes`, formData);
      }

      return await apiFetch(`/productos/${producto.id}`);
    } catch (err) {
      return rejectWithValue(err.message);
    }
  }
);

export const updateProducto = createAsyncThunk(
  'productos/update',
  async ({ id, nombre, descripcion, precio, stock, categoriaIds = [], previousCategoriaIds = [], imagenes = [] }, { rejectWithValue }) => {
    try {
      await apiFetch(`/productos/${id}`, {
        method: 'PUT',
        body: JSON.stringify({ nombre, descripcion, precio, stock }),
      });

      const toAdd = categoriaIds.filter((c) => !previousCategoriaIds.includes(c));
      const toRemove = previousCategoriaIds.filter((c) => !categoriaIds.includes(c));

      for (const categoriaId of toAdd) {
        await apiFetch(`/productos/${id}/categorias/${categoriaId}`, { method: 'POST' });
      }
      for (const categoriaId of toRemove) {
        await apiFetch(`/productos/${id}/categorias/${categoriaId}`, { method: 'DELETE' });
      }

      for (const file of imagenes) {
        const formData = new FormData();
        formData.append('file', file);
        await apiUpload(`/productos/${id}/imagenes`, formData);
      }

      return await apiFetch(`/productos/${id}`);
    } catch (err) {
      return rejectWithValue(err.message);
    }
  }
);

export const deleteImagenProducto = createAsyncThunk(
  'productos/deleteImagen',
  async ({ productoId, imagenId }, { rejectWithValue }) => {
    try {
      await apiFetch(`/productos/${productoId}/imagenes/${imagenId}`, { method: 'DELETE' });
      return await apiFetch(`/productos/${productoId}`);
    } catch (err) {
      return rejectWithValue(err.message);
    }
  }
);

export const deleteProducto = createAsyncThunk(
  'productos/delete',
  async (id, { rejectWithValue }) => {
    try {
      await apiFetch(`/productos/${id}`, { method: 'DELETE' });
      return id;
    } catch (err) {
      return rejectWithValue(err.message);
    }
  }
);

const initialState = {
  items: [],
  categorias: [],
  loading: false,
  saving: false,
  error: null,
};

const upsertProducto = (state, producto) => {
  const index = state.items.findIndex((p) => p.id === producto.id);
  if (index >= 0) state.items[index] = producto;
  else state.items.unshift(producto);
};

const productsSlice = createSlice({
  name: 'productos',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchProductos.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchProductos.fulfilled, (state, action) => {
        state.loading = false;
        state.items = action.payload || [];
      })
      .addCase(fetchProductos.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })
      .addCase(fetchCategorias.fulfilled, (state, action) => {
        state.categorias = action.payload || [];
      })
      .addCase(createProducto.pending, (state) => {
        state.saving = true;
        state.error = null;
      })
      .addCase(createProducto.fulfilled, (state, action) => {
        state.saving = false;
        upsertProducto(state, action.payload);
      })
      .addCase(createProducto.rejected, (state, action) => {
        state.saving = false;
        state.error = action.payload;
      })
      .addCase(updateProducto.pending, (state) => {
        state.saving = true;
        state.error = null;
      })
      .addCase(updateProducto.fulfilled, (state, action) => {
        state.saving = false;
        upsertProducto(state, action.payload);
      })
      .addCase(updateProducto.rejected, (state, action) => {
        state.saving = false;
        state.error = action.payload;
      })
      .addCase(deleteImagenProducto.fulfilled, (state, action) => {
        upsertProducto(state, action.payload);
      })
      .addCase(deleteProducto.fulfilled, (state, action) => {
        state.items = state.items.filter((p) => p.id !== action.payload);
      });
  },
});

export default productsSlice.reducer;
