package com.uade.tpo.e_commerce3.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.e_commerce3.model.Producto;
import com.uade.tpo.e_commerce3.model.enums.Categoria;
import com.uade.tpo.e_commerce3.service.ProductoService;
import org.springframework.web.multipart.MultipartFile;

@RestController

@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Producto newProducto = productoService.saveProducto(producto);

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

    @GetMapping("/ordenados")
    public List<Producto> getProductosOrdenados() {
        return productoService.getProductosOrdenados();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto productoDetails) {
        try {
            Producto updatedProducto = productoService.updateProducto(id, productoDetails);
            return ResponseEntity.ok(updatedProducto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build(); // Returns a 204 No Content status
    }

    private final String UPLOAD_DIR = "images/";
    @PostMapping("/{id}/image")
    public ResponseEntity<Producto> subirImage(@PathVariable Long id, @RequestParam("file") MultipartFile file, @RequestBody Producto productoDetails) {
        try {
            // 1. Guardar el archivo físicamente
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.write(path, bytes);

            // 2. Guardar la ruta en la base de datos
            Producto updatedProducto = productoService.updateProducto(id, productoDetails);
            return ResponseEntity.ok(updatedProducto);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
//    public ResponseEntity<Producto> addImage(@PathVariable Long id, @RequestParam("image") MultipartFile file){
//        try{
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(Objects.requireNonNull(file.getOriginalFilename()));
//            Files.write(path, bytes);
//             return ResponseEntity.status(HttpStatus.CREATED).body(null);
//        }catch (Exception e){
//            return ResponseEntity.badRequest().build();
//        }
//    }


    

    
}
