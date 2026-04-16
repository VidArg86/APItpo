package com.uade.tpo.e_commerce3.controller;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.uade.tpo.e_commerce3.model.Imagen;
import com.uade.tpo.e_commerce3.service.ImagenService;

@RestController
@RequestMapping("/api/productos") // Base is now the product
public class ImagenController {

    @Autowired
    private ImagenService imagenService;

    // URL: POST http://localhost:8080/api/productos/1/imagenes
    @PostMapping("/{productoId}/imagenes")
    public ResponseEntity<Imagen> uploadImagen(
            @RequestParam("file") MultipartFile file,
            @PathVariable Long productoId) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(imagenService.uploadImagen(file, productoId));
    }

    // URL: GET http://localhost:8080/api/productos/1/imagenes/1
    @GetMapping("/{productoId}/imagenes/{id}")
    public ResponseEntity<byte[]> getImagen(
            @PathVariable Long productoId, // Path variable for consistency
            @PathVariable Long id) {

        // We still use the service to get the image by its unique ID
        Imagen img = imagenService.getImagen(id);

        // Optional: Logic to verify the image actually belongs to the product
        if (!img.getProducto().getId().equals(productoId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(img.getExtension()))
                .body(img.getData());
    }

    @PutMapping("/{productoId}/imagenes/{id}")
    public ResponseEntity<Imagen> updateImagen(
            @PathVariable Long productoId,
            @PathVariable Long id,
            @RequestBody String nuevoNombre) {
        // Basic validation to ensure the image belongs to the product
        Imagen img = imagenService.getImagen(id);
        if (!img.getProducto().getId().equals(productoId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(imagenService.updateImagenNombre(id, nuevoNombre));
    }

    // URL: DELETE http://localhost:8080/api/productos/1/imagenes/1
    @DeleteMapping("/{productoId}/imagenes/{id}")
    public ResponseEntity<?> deleteImagen(
            @PathVariable Long productoId,
            @PathVariable Long id) {

        Imagen img = imagenService.getImagen(id);
        if (!img.getProducto().getId().equals(productoId) ||img.getProducto().getId().equals(productoId)) {
            imagenService.deleteImagen(id);
        }

        return ResponseEntity.noContent().build();
    }
}