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

import com.uade.tpo.e_commerce3.dto.LoginRequest;
import com.uade.tpo.e_commerce3.model.Usuario;
import com.uade.tpo.e_commerce3.service.UsuarioService;



@RestController
// para acceder a este controlador, la URL base será /api/usuarios
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService UsuarioService;

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return UsuarioService.getAllUsuarios();
    }
    // Endpoint para obtener un usuario específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Long id) {
        try {
            // Busca el usuario en el service
            Usuario usuario = UsuarioService.obtenerUsuarioPorId(id);

            // Devuelve HTTP 200 con el usuario
            return ResponseEntity.ok(usuario);

        } catch (RuntimeException e) {
            // Si no existe, devuelve HTTP 404 con mensaje
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado con ID: " + id);
        }
    }
    
//<?> is a wildcard
    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        try{
            Usuario newUsuario = UsuarioService.saveUsuario(usuario);
            return new ResponseEntity<>(newUsuario, HttpStatus.CREATED);
        } catch (RuntimeException e) {
        // 409 Conflict is the standard for "this already exists"
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        try {
            Usuario updatedUsuario = UsuarioService.updateUsuario(id, usuarioDetails);
            return ResponseEntity.ok(updatedUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        UsuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
    boolean success = UsuarioService.verifyLogin(request.getEmail(), request.getContraseña());

    if (success) {
        return ResponseEntity.ok("Login exitoso.");
    } else {
        return ResponseEntity.status(401).body("Credenciales inválidas.");
    }
}
}