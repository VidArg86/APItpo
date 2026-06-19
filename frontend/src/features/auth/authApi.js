import { apiSlice } from '../../app/apiSlice';

export const authApi = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    login: builder.mutation({
      query: (credentials) => ({
        url: '/auth/login',
        method: 'POST',
        body: { 
          email: credentials.email, 
          contraseña: credentials.password 
        },
      }),
    }),
    logoutServer: builder.mutation({
      query: () => ({
        url: '/auth/logout',
        method: 'POST',
      }),
    }),
    // Query que reconstruye la sesión tras presionar F5
    getMe: builder.query({
      query: () => '/auth/me',
    }),
    registerConsumer: builder.mutation({
      query: (userData) => ({
        url: '/auth/register',
        method: 'POST',
        body: { ...userData, contraseña: userData.password },
      }),
    }),
  }),
});

export const { 
  useLoginMutation, 
  useLogoutServerMutation, 
  useGetMeQuery, 
  useRegisterConsumerMutation 
} = authApi;