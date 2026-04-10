package com.uade.tpo.e_commerce3.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Set;

// ✅ PASO 7 (soporte): Utilidades para generar y validar tokens JWT
@Component
public class JwtUtil {

    // Clave secreta declarada en application.properties (jwt.secret)
    // Se usa para firmar el token — solo el servidor la conoce
    @Value("${jwt.secret}")
    private String secret;

    // Tiempo de vida del token en milisegundos, declarado en application.properties (jwt.expiration)
    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Genera el token JWT con el email del usuario y sus roles
    // Se llama desde AuthenticationService.authenticate() al hacer login
    public String generateToken(String username, Set<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", String.join(",", roles))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extrae el email del usuario del token
    // Se llama desde JwtFilter para saber quién está haciendo el request
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Extrae los roles del token
    // Se llama desde JwtFilter para asignar las authorities de Spring Security
    public Set<String> getRoles(String token) {
        String roles = (String) getClaims(token).get("roles");
        return Set.of(roles.split(","));
    }

    // Valida que el token no haya expirado y que la firma sea correcta
    // Se llama desde JwtFilter en cada request
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // Parsea el token y extrae todos sus datos (claims)
    // Si el token está manipulado o expirado lanza una excepción
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
