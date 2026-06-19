import { configureStore } from '@reduxjs/toolkit';
import favoritosReducer from './favoritosSlice';
import cartReducer from './cartSlice';
import authReducer from './authSlice';

const store = configureStore({
    reducer: {
        favoritos: favoritosReducer,
        cart: cartReducer,
        auth: authReducer,
    },
});

export default store;
