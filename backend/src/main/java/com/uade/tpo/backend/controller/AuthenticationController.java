package com.uade.tpo.backend.controller;
 
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.uade.tpo.backend.dto.LoginRequest;
import com.uade.tpo.backend.dto.RegistrationRequestDTO;
import com.uade.tpo.backend.service.CreationService;
import com.uade.tpo.backend.security.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
 
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
 
    private final CreationService authenticationService;
    private final JwtUtil jwtUtil; // Inyectamos JwtUtil para leer información del token al registrar

    // Helper privado para estructurar la cookie HttpOnly con SameSite=Lax para prevenir CSRF
    private void addJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("jwt_token", token);
        cookie.setHttpOnly(true);   // <-- Bloquea acceso desde JS (Mitiga XSS)
        cookie.setSecure(false);    // <-- Cambiar a TRUE si usás HTTPS en producción
        cookie.setPath("/");        // <-- Disponible en todas las rutas del backend
        cookie.setMaxAge(86400);    // <-- Duración de la cookie en segundos (1 día)
        
        // Para navegadores modernos, se añade la cabecera con SameSite=Lax para evitar ataques CSRF
        response.addHeader("Set-Cookie", String.format("%s=%s; Max-Age=%d; Path=%s; HttpOnly; SameSite=Lax", 
                cookie.getName(), cookie.getValue(), cookie.getMaxAge(), cookie.getPath()));
    }
 
    // POST /api/auth/login  →  valida credenciales y setea la Cookie HttpOnly
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        String token = authenticationService.authenticate(request);
        
        // Extraemos los roles y el email directamente usando el JwtUtil existente
        Set<String> roles = jwtUtil.getRoles(token);
        
        // Seteamos la cookie en la respuesta HTTP
        addJwtCookie(response, token);
        
        // Retornamos el perfil del usuario en el cuerpo (SIN el token expuesto)
        return ResponseEntity.ok(Map.of(
            "email", request.getEmail(),
            "roles", roles
        ));
    }

    // POST /api/auth/register  →  registra usuario CONSUMIDOR y setea la Cookie
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDTO request, HttpServletResponse response) {
        String token = authenticationService.register(request);
        
        Set<String> roles;
        try {
            roles = jwtUtil.getRoles(token);
        } catch (Exception e) {
            // Fallback: Si el token de registro no incluyó claims de roles todavía, asignamos el rol correspondiente
            roles = Set.of("ROLE_CONSUMIDOR"); 
        }
        
        addJwtCookie(response, token);
        
        return ResponseEntity.ok(Map.of(
            "email", request.getEmail(),
            "roles", roles
        ));
    }

    // POST /api/auth/register/vendedor  →  registra VENDEDOR y setea la Cookie
    @PostMapping("/register/vendedor")
    public ResponseEntity<?> registerVendedor(@RequestBody RegistrationRequestDTO request, HttpServletResponse response) {
        String token = authenticationService.registerVendedor(request);
        Set<String> roles = jwtUtil.getRoles(token);
        addJwtCookie(response, token);
        return ResponseEntity.ok(Map.of("email", request.getEmail(), "roles", roles));
    }

    // POST /api/auth/register/admin  →  registra ADMIN y setea la Cookie
    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegistrationRequestDTO request, HttpServletResponse response) {
        String token = authenticationService.registerAdmin(request);
        Set<String> roles = jwtUtil.getRoles(token);
        addJwtCookie(response, token);
        return ResponseEntity.ok(Map.of("email", request.getEmail(), "roles", roles));
    }

    // POST /api/auth/logout  →  Invalida la cookie borrándola del navegador
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        response.addHeader("Set-Cookie", "jwt_token=; Max-Age=0; Path=/; HttpOnly; SameSite=Lax");
        return ResponseEntity.ok(Map.of("message", "Sesión cerrada correctamente"));
    }

    // GET /api/auth/me  →  Reconstruye el estado de Redux cuando se presiona F5
    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return ResponseEntity.status(401).body(Map.of("error", "No autenticado"));
        }
        
        Set<String> roles = auth.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toSet());
                
        return ResponseEntity.ok(Map.of(
            "email", auth.getName(),
            "roles", roles
        ));
    }
}