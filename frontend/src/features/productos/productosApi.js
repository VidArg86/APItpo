import { apiSlice } from '../../app/apiSlice';

export const productosApi = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    getProductos: builder.query({
      query: () => '/productos',
      providesTags: ['Producto'],
    }),
    getProductosOrdenados: builder.query({
      query: () => '/productos/ordenados',
      providesTags: ['Producto'],
    }),
    createProducto: builder.mutation({
      query: (nuevoProducto) => ({
        url: '/productos',
        method: 'POST',
        body: nuevoProducto,
      }),
      invalidatesTags: ['Producto'],
    }),
    deleteProducto: builder.mutation({
      query: (id) => ({
        url: `/productos/${id}`,
        method: 'DELETE',
      }),
      invalidatesTags: ['Producto'],
    }),
  }),
});

export const { 
  useGetProductosQuery, 
  useGetProductosOrdenadosQuery,
  useCreateProductoMutation, 
  useDeleteProductoMutation 
} = productosApi;