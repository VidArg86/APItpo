package com.uade.tpo.e_commerce3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.e_commerce3.model.Carrito;
import com.uade.tpo.e_commerce3.model.Producto;


import jakarta.transaction.Transactional;

@Service
@Transactional
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository; // Assuming you have a Producto repository

    // --- BASIC CRUD ---

    public List<Carrito> findAll() {
        return carritoRepository.findAll();
    }

    public Optional<Carrito> findById(Long id) {
        return carritoRepository.findById(id);
    }

    public Carrito create(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public void delete(Long id) {
        carritoRepository.deleteById(id);
    }

    // --- SHOPPING CART SPECIFIC LOGIC ---

    /**
     * Adds a product to the cart and updates the total price.
     */
    public Carrito addProductoToCarrito(Long carritoId, Long productoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito not found"));
        
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto not found"));

        carrito.getProductos().add(producto);
        
        // Update total price logic
        recalculateTotal(carrito);
        
        return carritoRepository.save(carrito);
    }

    /**
     * Removes a product from the cart and updates the total price.
     */
    public Carrito removeProductoFromCarrito(Long carritoId, Long productoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito not found"));

        carrito.getProductos().removeIf(p -> p.getId().equals(productoId));
        
        recalculateTotal(carrito);
        
        return carritoRepository.save(carrito);
    }

    /**
     * Clears all products from the cart.
     */
    public Carrito clearCarrito(Long id) {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito not found"));
        
        carrito.getProductos().clear();
        carrito.setPrecioTotal(0.0);
        
        return carritoRepository.save(carrito);
    }

    // Helper method to keep total price in sync
    private void recalculateTotal(Carrito carrito) {
        double total = carrito.getProductos().stream()
                .mapToDouble(Producto::getPrecio) // Assuming Producto has a getPrecio()
                .sum();
        carrito.setPrecioTotal(total);
    }
}
}
