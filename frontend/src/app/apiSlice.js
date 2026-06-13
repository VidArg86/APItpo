import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

export const apiSlice = createApi({
  reducerPath: 'api',
  baseQuery: fetchBaseQuery({
    baseUrl: 'http://localhost:8080/api', // Ajustá al puerto de tu Spring Boot
    prepareHeaders: (headers, { getState }) => {
      // Obtenemos el token desde el estado de Redux
      const token = getState().auth.token;
      if (token) {
        headers.set('authorization', `Bearer ${token}`);
      }
      return headers;
    },
  }),
  tagTypes: ['Producto', 'Carrito'], // Para manejar la invalidación de caché
  endpoints: (builder) => ({}),
});