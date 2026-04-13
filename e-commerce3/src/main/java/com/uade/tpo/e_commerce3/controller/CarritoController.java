package com.uade.tpo.e_commerce3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<?> addProducto(
      @PathVariable Long carritoId,
      @PathVariable Long productoId,
      @RequestParam(defaultValue = "1") int cantidad) {
    try {
      Carrito carritoActualizado = carritoService.addProductoToCarrito(carritoId, productoId, cantidad);
      return ResponseEntity.ok(carritoActualizado);
    } catch (RuntimeException e) {
      // Si el ID del carrito o producto no existen, devolvemos el mensaje de error
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  // 5. Checkout del carrito
  @PostMapping("/{id}/checkout")
  public ResponseEntity<String> checkout(@PathVariable Long id) {
    try {
      String mensaje = carritoService.checkout(id);
      return ResponseEntity.ok(mensaje);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  // 6. Remove a product from a cart
  @DeleteMapping("/{carritoId}/productos/{productoId}")
  public ResponseEntity<Carrito> removeProducto(@PathVariable Long carritoId, @PathVariable Long productoId) {
    return ResponseEntity.ok(carritoService.removeProductoFromCarrito(carritoId, productoId));
  }

  // 7. Delete the entire cart
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCarrito(@PathVariable Long id) {
    carritoService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
