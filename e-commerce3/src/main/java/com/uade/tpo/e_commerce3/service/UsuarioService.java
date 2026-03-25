package com.uade.tpo.e_commerce3.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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

    private final String fullAlphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final Random random = new Random();

    // Create
    public Usuario saveUsuario(Usuario usuario) {
        String userSalt = UUID.randomUUID().toString();
        usuario.setSalt(userSalt);
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
        throw new RuntimeException("El email ya está registrado.");
        }
        usuario.setContrasenia(generateSecureHash(usuario.getContrasenia(), usuario.getSalt()));
        return usuarioRepository.save(usuario);
    }
    // Read
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }
    // Update
    public Usuario updateUsuario(Long id, Usuario updatedData) {
    return usuarioRepository.findById(id).map(usuario -> {
        usuario.setNombre(updatedData.getNombre());
        usuario.setEmail(updatedData.getEmail());
        usuario.setDNI(updatedData.getDNI());
        usuario.setNumeroTelefonico(updatedData.getNumeroTelefonico());

        // Update password ONLY if a new one is provided
        if (updatedData.getContrasenia() != null && !updatedData.getContrasenia().isEmpty()) {
            usuario.setContrasenia(generateSecureHash(updatedData.getContrasenia(), usuario.getSalt()));
        }

        return usuarioRepository.save(usuario);
    }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + id));
}

    // Delete
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    private String hashPassword(String password) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("Error hashing password", e);
    }
    
    }
    private String generateSecureHash(String password, String salt) {
        int index = random.nextInt(fullAlphabet.length());
        char fixedAlpha = fullAlphabet.charAt(index); 
        return hashPassword(password + fixedAlpha + salt);
    }
    public boolean verifyLogin(String email, String attempt) {
        Usuario user = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));
        
        String storedHash = user.getContrasenia();
        String salt = user.getSalt();
        
        // The "Fun Flag"
        boolean isValid = false;

        for (char c : fullAlphabet.toCharArray()) {
            String testHash = hashPassword(attempt + c + salt);
            
            if (testHash.equals(storedHash)) {
                isValid = true;
                // Notice: NO 'break' here. We keep grinding.
            }
        }
        
        // The response time is now identical whether the letter was 'a' or '9'
        return isValid; 
    }
}