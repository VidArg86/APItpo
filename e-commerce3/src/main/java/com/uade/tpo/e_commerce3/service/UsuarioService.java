package com.uade.tpo.e_commerce3.service;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.e_commerce3.model.Usuario;
import com.uade.tpo.e_commerce3.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Create
    public Usuario saveUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado.");
        }
        return usuarioRepository.save(usuario);
    }

    // Read
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }
    
    // Update
    public Usuario updateUsuario(Long id, Usuario updatedData) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombreUsuario(updatedData.getNombreUsuario());
            usuario.setNombre(updatedData.getNombre());
            usuario.setApellido(updatedData.getApellido());
            usuario.setEmail(updatedData.getEmail());
            if (updatedData.getContraseña() != null && !updatedData.getContraseña().isEmpty()) {
                usuario.setContraseña(updatedData.getContraseña());
            }
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + id));
    }

    // Delete
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Login simple — comparación directa, sin encriptación
    public boolean verifyLogin(String email, String contraseña) {
        return usuarioRepository.findByEmail(email)
                .map(usuario -> usuario.getContraseña().equals(contraseña))
                .orElse(false);
    }
}