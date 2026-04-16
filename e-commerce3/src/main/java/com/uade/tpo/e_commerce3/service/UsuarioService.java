package com.uade.tpo.e_commerce3.service;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.uade.tpo.e_commerce3.model.Usuario;
import com.uade.tpo.e_commerce3.repository.UsuarioRepository;
 
import jakarta.transaction.Transactional;
 
/**
 * NOTA: El registro y login ahora pasan por AuthenticationService.
 * Este service queda para operaciones admin (listar, buscar, eliminar usuarios).
 */
@Service
@Transactional
public class UsuarioService {
 
    @Autowired
    private UsuarioRepository usuarioRepository;
 
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }
 
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }
 
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}