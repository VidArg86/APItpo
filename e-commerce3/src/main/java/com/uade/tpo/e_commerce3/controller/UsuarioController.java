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
        
        // This calls the diabolical 62-character loop
        boolean success = UsuarioService.verifyLogin(request.getEmail(), request.getPassword());

        if (success) {
            return ResponseEntity.ok("Login successful!.");
        } else {
            // We return 401 Unauthorized for a failed login
            return ResponseEntity.status(401).body("Credenciales Invalidas, intente con otra contraseña o email.");
        }
    }
}