package com.uade.tpo.e_commerce3.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.uade.tpo.e_commerce3.model.Imagen;
import com.uade.tpo.e_commerce3.model.Producto;
import com.uade.tpo.e_commerce3.repository.ImagenRepository;
import com.uade.tpo.e_commerce3.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImagenService {

    @Autowired
    private ImagenRepository imagenRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public Imagen uploadImagen(MultipartFile file, Long productoId) throws IOException {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Imagen imagen = new Imagen();
        imagen.setNombre(file.getOriginalFilename());
        imagen.setExtension(file.getContentType());
        imagen.setData(file.getBytes());
        imagen.setProducto(producto);

        return imagenRepository.save(imagen);
    }

    public Imagen getImagen(Long id) {
        return imagenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada"));
    }

    public Imagen updateImagenNombre(Long id, String nuevoNombre) {
        return imagenRepository.findById(id).map(imagen -> {
            imagen.setNombre(nuevoNombre);
            return imagenRepository.save(imagen);
        }).orElseThrow(() -> new RuntimeException("Imagen no encontrada"));
    }

    public void deleteImagen(Long id) {
        if (!imagenRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        imagenRepository.deleteById(id);
    }
}