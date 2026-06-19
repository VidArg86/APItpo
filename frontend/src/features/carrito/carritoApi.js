import { apiSlice } from '../../app/apiSlice';

export const carritoApi = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    getCarritoById: builder.query({
      query: (id) => `/carritos/${id}`,
      providesTags: ['Carrito'],
    }),
    addProductoAlCarrito: builder.mutation({
      query: ({ carritoId, productoId, cantidad }) => ({
        url: `/carritos/${carritoId}/productos/${productoId}`,
        method: 'POST',
        params: { cantidad }, // Va como @RequestParam
      }),
      invalidatesTags: ['Carrito'],
    }),
    checkoutCarrito: builder.mutation({
      query: (id) => ({
        url: `/carritos/${id}/checkout`,
        method: 'POST',
        responseHandler: (response) => response.text(), // Devuelve texto de confirmación
      }),
      invalidatesTags: ['Carrito'],
    }),
    vaciarCarrito: builder.mutation({
      query: (id) => ({
        url: `/carritos/${id}/vaciar`,
        method: 'DELETE',
      }),
      invalidatesTags: ['Carrito'],
    }),
  }),
});

export const {
  useGetCarritoByIdQuery,
  useAddProductoAlCarritoMutation,
  useCheckoutCarritoMutation,
  useVaciarCarritoMutation,
} = carritoApi;