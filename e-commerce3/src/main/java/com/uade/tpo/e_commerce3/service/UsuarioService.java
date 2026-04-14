package com.uade.tpo.e_commerce3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uade.tpo.e_commerce3.model.Usuario;
import com.uade.tpo.e_commerce3.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // This should be stored in your application.properties or environment variables
    @Value("${app.security.pepper}")
    private String pepper;

    // Create
    public Usuario saveUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado.");
        }
        
        // Apply pepper and hash
        String passwordWithPepper = usuario.getContraseña() + pepper;
        usuario.setContraseña(passwordEncoder.encode(passwordWithPepper));
        
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
            usuario.setEmail(updatedData.getEmail());
            
            if (updatedData.getContraseña() != null && !updatedData.getContraseña().isEmpty()) {
                // Apply pepper and re-hash new password
                String passwordWithPepper = updatedData.getContraseña() + pepper;
                usuario.setContraseña(passwordEncoder.encode(passwordWithPepper));
            }
            
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + id));
    }

    // Delete
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Login logic using BCrypt matchers
    public boolean verifyLogin(String email, String rawPassword) {
    return usuarioRepository.findByEmail(email)
        .map(user -> passwordEncoder.matches(rawPassword + pepper, user.getContraseña()))
        .orElse(false);
    }
}