package com.uade.tpo.backend.config;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; // <-- AGREGAR IMPORT
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 
import com.uade.tpo.backend.model.Rol;
import com.uade.tpo.backend.repository.UsuarioRepository;
import com.uade.tpo.backend.security.JwtFilter;

import java.util.List;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
 
import lombok.RequiredArgsConstructor;
 
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
 
    private final JwtFilter jwtFilter;
    private final UsuarioRepository usuarioRepository;
 
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
 
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            // Aseguramos que Spring no intente crear sesiones HTTP en el servidor
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
 
                // ── CRUCIAL: Permitir la ruta de errores para evitar que las excepciones se enmascaren en un 403 ──
                .requestMatchers("/error").permitAll()
                
                // Permitir solicitudes de preflight CORS (OPTIONS) de manera explícita
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
 
                // ── Rutas públicas (sin token) ──────────────────────────────
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/productos", "/api/productos/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()
                
                // ── Rutas de carrito: solo usuarios autenticados ────────────
                .requestMatchers("/api/carritos/**").hasAnyRole(Rol.CONSUMIDOR.name(), Rol.ADMIN.name())
 
                // ── Gestión de productos: solo VENDEDOR o ADMIN ─────────────
                .requestMatchers(HttpMethod.POST,   "/api/productos").hasAnyRole(Rol.VENDEDOR.name(), Rol.ADMIN.name())
                .requestMatchers(HttpMethod.PUT,    "/api/productos/**").hasAnyRole(Rol.VENDEDOR.name(), Rol.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasAnyRole(Rol.VENDEDOR.name(), Rol.ADMIN.name())
 
                // ── Gestión de usuarios: solo ADMIN ─────────────────────────
                .requestMatchers("/api/usuarios/**").hasRole(Rol.ADMIN.name())
                
                // ── Cualquier otra ruta requiere autenticación ───────────────
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
 
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}