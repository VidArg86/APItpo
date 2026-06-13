import { apiSlice } from '../../app/apiSlice';

export const authApi = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    login: builder.mutation({
      query: (credentials) => ({
        url: '/auth/login',
        method: 'POST',
        body: { 
          email: credentials.email, 
          contraseña: credentials.password // Mapeado al DTO del backend
        },
        responseHandler: (response) => response.text(), // Tu backend devuelve un String (JWT)
      }),
    }),
    registerConsumer: builder.mutation({
      query: (userData) => ({
        url: '/auth/register',
        method: 'POST',
        body: { ...userData, contraseña: userData.password },
      }),
    }),
    // Podés agregar registerVendedor y registerAdmin siguiendo el mismo patrón
  }),
});

export const { useLoginMutation, useRegisterConsumerMutation } = authApi;