package com.uade.tpo.e_commerce3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.e_commerce3.dto.LoginRequest;
import com.uade.tpo.e_commerce3.dto.RegisterRequest;
import com.uade.tpo.e_commerce3.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

// ✅ PASO 5: Controller que expone los endpoints de autenticación
// Estos endpoints son PÚBLICOS — configurados en SecurityConfig para no requerir token
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // POST /api/auth/register
    // Recibe los datos del nuevo usuario y devuelve un mensaje de confirmación
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    // POST /api/auth/login
    // Recibe email y password, devuelve el token JWT para usar en futuros requests
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
