package com.uade.tpo.e_commerce3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.e_commerce3.model.Categoria;
import com.uade.tpo.e_commerce3.model.Producto;
import com.uade.tpo.e_commerce3.repository.CategoriaRepository;
import com.uade.tpo.e_commerce3.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Create
    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Read
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    // Filtrar por categoria (usando el id de la categoria)
    public List<Producto> getProductosByCategoria(Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: " + categoriaId));
        return productoRepository.findByCategoria(categoria);
    }

    // Update
    public Producto updateProducto(Long id, Producto updatedData) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(updatedData.getNombre());
            producto.setDescripcion(updatedData.getDescripcion());
            producto.setPrecio(updatedData.getPrecio());
            producto.setStock(updatedData.getStock());
            if (updatedData.getCategoria() != null) {
                producto.setCategoria(updatedData.getCategoria());
            }
            return productoRepository.save(producto);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    // Delete
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

    // Ordenados alfabeticamente
    public List<Producto> getProductosOrdenados() {
        return productoRepository.findAllByOrderByNombreAsc();
    }
}
