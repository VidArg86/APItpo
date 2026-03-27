package com.uade.tpo.e_commerce3.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.e_commerce3.model.Carrito;
import com.uade.tpo.e_commerce3.model.ItemCarrito;
import com.uade.tpo.e_commerce3.model.Producto;
import com.uade.tpo.e_commerce3.repository.CarritoRepository;
import com.uade.tpo.e_commerce3.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

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
     * Adds a product to the cart. If the product is already there, it increases quantity.
     */
    public Carrito addProductoToCarrito(Long carritoId, Long productoId, int cantidad) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito not found"));
        
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto not found"));

        // 1. Check if we have enough stock in the master Producto table
        if (producto.getStock() < cantidad) {
            throw new RuntimeException("No hay suficiente stock disponible");
        }

        // 2. Check if this product is already in the cart list
        Optional<ItemCarrito> itemExistente = carrito.getProductos().stream()
                .filter(item -> item.getProducto().getId().equals(productoId))
                .findFirst();

        if (itemExistente.isPresent()) {
            // Update quantity of the existing line item
            ItemCarrito item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
        } else {
            // Create a new Line Item (ItemCarrito)
            ItemCarrito nuevoItem = new ItemCarrito();
            nuevoItem.setCarrito(carrito);
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(cantidad);
            // Add it to the list in Carrito
            carrito.getProductos().add(nuevoItem);
        }
        
        recalculateTotal(carrito);
        return carritoRepository.save(carrito);
    }

    /**
     * Removes an entire line item from the cart.
     */
    public Carrito removeProductoFromCarrito(Long carritoId, Long productoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito not found"));

        // Remove the ItemCarrito entry that matches the product ID
        carrito.getProductos().removeIf(item -> item.getProducto().getId().equals(productoId));
        
        recalculateTotal(carrito);
        return carritoRepository.save(carrito);
    }

    /**
     * Clears all items from the cart.
     */
    public Carrito clearCarrito(Long id) {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito not found"));
        
        // Because of orphanRemoval = true, this deletes rows from items_carrito table
        carrito.getProductos().clear();
        carrito.setPrecioTotal(0.0);
        
        return carritoRepository.save(carrito);
    }

    // Helper method: Price * Quantity for every item
    private void recalculateTotal(Carrito carrito) {
        double total = carrito.getProductos().stream()
                .mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
                .sum();
        carrito.setPrecioTotal(total);
    }
}