package com.uade.tpo.e_commerce3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.e_commerce3.model.Carrito;
import com.uade.tpo.e_commerce3.service.CarritoService;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // 1. Get all carts
    @GetMapping
    public List<Carrito> getAllCarritos() {
        return carritoService.findAll();
    }

    // 2. Get a specific cart by ID
    @GetMapping("/{id}")
    public ResponseEntity<Carrito> getCarritoById(@PathVariable Long id) {
        return carritoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Create a new cart (Initial setup)
    @PostMapping
    public Carrito createCarrito(@RequestBody Carrito carrito) {
        return carritoService.create(carrito);
    }

    // 4. Add a product to a cart
    // Path example: POST /api/carritos/1/productos/5
    @PostMapping("/{carritoId}/productos/{productoId}")
    public ResponseEntity<Carrito> addProducto(@PathVariable Long carritoId, @PathVariable Long productoId,@RequestParam(defaultValue = "1") int cantidad) {
        try {
            return ResponseEntity.ok(carritoService.addProductoToCarrito(carritoId, productoId, cantidad));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Remove a product from a cart
    @DeleteMapping("/{carritoId}/productos/{productoId}")
    public ResponseEntity<Carrito> removeProducto(@PathVariable Long carritoId, @PathVariable Long productoId) {
        return ResponseEntity.ok(carritoService.removeProductoFromCarrito(carritoId, productoId));
    }

    // 6. Delete the entire cart
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrito(@PathVariable Long id) {
        carritoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}