// src/store/authSlice.js
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { apiFetch } from '../services/api';

// POST /api/auth/login
export const loginUser = createAsyncThunk(
  'auth/login',
  async ({ email, contraseña }, { rejectWithValue }) => {
    try {
      const token = await apiFetch('/auth/login', {
        method: 'POST',
        body: JSON.stringify({ email, contraseña }),
      });
      localStorage.setItem('token', token);
      return token;
    } catch (err) {
      return rejectWithValue(err.message);
    }
  }
);

const initialState = {
  token: localStorage.getItem('token') || null,
  isLoggedIn: !!localStorage.getItem('token'),
  loading: false,
  error: null,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    logout: (state) => {
      localStorage.removeItem('token');
      state.token = null;
      state.isLoggedIn = false;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(loginUser.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(loginUser.fulfilled, (state, action) => {
        state.loading = false;
        state.token = action.payload;
        state.isLoggedIn = true;
      })
      .addCase(loginUser.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      });
  },
});

export const { logout } = authSlice.actions;
export default authSlice.reducer;
