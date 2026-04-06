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

    // Filtrar por categoria
    public List<Producto> getProductosByCategoria(Long categoriaId) {
        // Validamos que la categoria exista
        categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: " + categoriaId));
        return productoRepository.findByCategoriaId(categoriaId);
    }

    // Agregar una categoria a un producto existente
    public Producto agregarCategoria(Long productoId, Long categoriaId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + productoId));
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: " + categoriaId));

        if (!producto.getCategorias().contains(categoria)) {
            producto.getCategorias().add(categoria);
        }
        return productoRepository.save(producto);
    }

    // Quitar una categoria de un producto
    public Producto quitarCategoria(Long productoId, Long categoriaId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + productoId));
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: " + categoriaId));

        producto.getCategorias().remove(categoria);
        return productoRepository.save(producto);
    }

    // Update
    public Producto updateProducto(Long id, Producto updatedData) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(updatedData.getNombre());
            producto.setDescripcion(updatedData.getDescripcion());
            producto.setPrecio(updatedData.getPrecio());
            producto.setStock(updatedData.getStock());
            if (updatedData.getCategorias() != null && !updatedData.getCategorias().isEmpty()) {
                producto.setCategorias(updatedData.getCategorias());
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
