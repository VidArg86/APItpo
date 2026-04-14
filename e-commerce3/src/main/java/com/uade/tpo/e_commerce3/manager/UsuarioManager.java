package com.uade.tpo.e_commerce3.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.uade.tpo.e_commerce3.model.*;
import com.uade.tpo.e_commerce3.repository.*;

@Service
public class UsuarioManager {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PerfilRepository perfilRepository;
    @Autowired private CarritoRepository carritoRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @Value("${app.security.pepper}")
    private String pepper;

    @Transactional
    public Usuario registrarConsumidor(Usuario usuario, String nombre, String apellido, String dni, String telefono, String direccion) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        // 1. Security: Hash + Pepper
        usuario.setRol(Rol.CONSUMIDOR);
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña() + pepper));

        // 2. Link Carrito
        Carrito nuevoCarrito = new Carrito();
        nuevoCarrito.setUsuario(usuario);
        nuevoCarrito.setPrecioTotal(0.0);
        usuario.setCarrito(nuevoCarrito);
        carritoRepository.save(nuevoCarrito);

        // 3. Save User
        Usuario savedUsuario = usuarioRepository.save(usuario);

        // 4. Link Perfil
        Perfil nuevoPerfil = new Perfil();
        nuevoPerfil.setNombre(nombre);
        nuevoPerfil.setApellido(apellido);
        nuevoPerfil.setDni(dni);
        nuevoPerfil.setUsuario(savedUsuario);
        perfilRepository.save(nuevoPerfil);

        return savedUsuario;
    }
}