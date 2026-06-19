import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

export const apiSlice = createApi({
  reducerPath: 'api',
  baseQuery: fetchBaseQuery({
    baseUrl: 'http://localhost:8080/api', 
    credentials: 'include', 
  }),
  tagTypes: ['Producto', 'Carrito'], 
  endpoints: (builder) => ({}),
});