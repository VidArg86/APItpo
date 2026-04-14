package com.uade.tpo.e_commerce3.controller;
 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.uade.tpo.e_commerce3.dto.LoginRequest;
import com.uade.tpo.e_commerce3.dto.RegistrationRequestDTO;
import com.uade.tpo.e_commerce3.service.CreationService;
 
import lombok.RequiredArgsConstructor;
 
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class Authenticationcontroller {
 
    private final CreationService authenticationService;
 
    // POST /api/auth/register  →  registra usuario y devuelve JWT
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequestDTO request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
 
    // POST /api/auth/login  →  valida credenciales y devuelve JWT
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}