package com.uade.tpo.e_commerce3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.e_commerce3.model.Producto;
import com.uade.tpo.e_commerce3.model.enums.Categoria;
import com.uade.tpo.e_commerce3.service.ProductoService;





@RestController
// para acceder a este controlador, la URL base será /api/productos
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Producto newProducto = productoService.saveProducto(producto);
        // Returns 201 Created and the new object with its generated ID
        return ResponseEntity.status(HttpStatus.CREATED).body(newProducto);
    }

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }
    // GET /api/productos/categoria/
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<?> getByCategoria(@PathVariable Categoria categoria) {
        try {
            List<Producto> productos = productoService.getProductosByCategoria(categoria);
            return ResponseEntity.ok(productos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Categoría inválida.");
        }
    }
    // GET /api/productos/ordenados
    @GetMapping("/ordenados")
    public List<Producto> getProductosOrdenados() {
        return productoService.getProductosOrdenados();
    }
    
    // 1. Update Endpoint (PUT)
    // URL Example: http://localhost:8080/api/productos/1
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto productoDetails) {
        try {
            Producto updatedProducto = productoService.updateProducto(id, productoDetails);
            return ResponseEntity.ok(updatedProducto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 2. Delete Endpoint (DELETE)
    // URL Example: http://localhost:8080/api/productos/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build(); // Returns a 204 No Content status
    }

    

    
}
