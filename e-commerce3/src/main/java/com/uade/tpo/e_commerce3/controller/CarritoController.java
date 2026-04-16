package com.uade.tpo.e_commerce3.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.e_commerce3.model.Carrito;
import com.uade.tpo.e_commerce3.service.CarritoService;
import com.uade.tpo.e_commerce3.dto.CarritoSolicitudDTO; // Importante: Importar el DTO

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

    /**
     * 3. Add a product to a cart (Versión DTO)
     * Ahora recibe la CarritoSolicitudDTO en el @RequestBody.
     */
    @PostMapping("/{carritoId}/productos")
    public ResponseEntity<?> addProducto(
            @PathVariable Long carritoId,
            @RequestBody CarritoSolicitudDTO solicitud) { // Cambiado para recibir el DTO
        try {
            // Llamamos a la nueva firma del método en el Service
            Carrito carritoActualizado = carritoService.addProductoToCarrito(carritoId, solicitud);
            return ResponseEntity.ok(carritoActualizado);
        } catch (Exception e) {
            // Capturamos InsufficientStockException o ResourceNotFoundException
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 4. Checkout del carrito
    @PostMapping("/{id}/checkout")
    public ResponseEntity<String> checkout(@PathVariable Long id) {
        try {
            String mensaje = carritoService.checkout(id);
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 5. Remove a product from a cart
    @DeleteMapping("/{carritoId}/productos/{productoId}")
    public ResponseEntity<Carrito> removeProducto(@PathVariable Long carritoId, @PathVariable Long productoId) {
        return ResponseEntity.ok(carritoService.removeProductoFromCarrito(carritoId, productoId));
    }

    // 6. Elimina el carrito completo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrito(@PathVariable Long id) {
        carritoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 7. Vaciar el carrito
    @DeleteMapping("/{id}/vaciar")
    public ResponseEntity<Carrito> vaciarCarrito(@PathVariable Long id) {
        return ResponseEntity.ok(carritoService.clearCarrito(id));
    }
}