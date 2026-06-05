import { configureStore } from '@reduxjs/toolkit';
import favoritosReducer from './favoritosSlice';

export const store = configureStore({
  reducer: {
    favoritos: favoritosReducer,
  },
});