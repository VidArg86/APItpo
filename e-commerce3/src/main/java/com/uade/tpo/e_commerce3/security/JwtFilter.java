package com.uade.tpo.e_commerce3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

// ✅ PASO 7 (soporte): Filtro que intercepta cada request HTTP y valida el token JWT
// Configurado en SecurityConfig con addFilterBefore()
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Leer el header Authorization del request
        //    Ejemplo: "Bearer eyJhbGciOiJIUzI1NiJ9..."
        String header = request.getHeader("Authorization");

        // 2. Verificar que el header existe y tiene el formato correcto
        if (header != null && header.startsWith("Bearer ")) {

            // 3. Extraer solo el token (sacar el "Bearer " del inicio)
            String token = header.substring(7);

            // 4. Validar el token (firma + expiración)
            if (jwtUtil.validateToken(token)) {

                // 5. Extraer el email y los roles del token
                String username = jwtUtil.getUsername(token);
                Set<String> roles = jwtUtil.getRoles(token);

                // 6. Convertir los roles en authorities que Spring Security entiende
                var authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                // 7. Crear el objeto de autenticación y guardarlo en el contexto de seguridad
                //    A partir de acá, Spring Security sabe que el usuario está autenticado
                var auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // 8. Pasar el request al siguiente filtro (o al controller si ya no hay más filtros)
        filterChain.doFilter(request, response);
    }
}
