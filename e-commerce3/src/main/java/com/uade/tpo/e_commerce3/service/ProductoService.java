package com.uade.tpo.e_commerce3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.e_commerce3.model.Producto;
import com.uade.tpo.e_commerce3.model.enums.Categoria;
import com.uade.tpo.e_commerce3.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {
 
    @Autowired
    private ProductoRepository productoRepository;

    // Create
    public Producto saveProducto(Producto producto) {
    // We pass the object to the repository, and it returns the saved entity (with the new ID)
    return productoRepository.save(producto);
    }
    
    // Read
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    // Filtrar por categoría
    public List<Producto> getProductosByCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    // Update Method
    public Producto updateProducto(Long id, Producto updatedData) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(updatedData.getNombre());
            producto.setDescripcion(updatedData.getDescripcion());
            // Save and return the updated version
            return productoRepository.save(producto);
        }).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    //Delete Method 
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
    public List<Producto> getProductosOrdenados() {
    return productoRepository.findAllByOrderByNombreAsc();
}
}
