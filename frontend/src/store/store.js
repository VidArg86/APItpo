import { configureStore } from '@reduxjs/toolkit';
import favoritosReducer from './favoritosSlice';

const store = configureStore({
    reducer: {
        favoritos: favoritosReducer,
    },
});

export default store;