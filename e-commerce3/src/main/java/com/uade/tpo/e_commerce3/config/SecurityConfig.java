package com.uade.tpo.e_commerce3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.uade.tpo.e_commerce3.model.Role;
import com.uade.tpo.e_commerce3.repository.UsuarioRepository;
import com.uade.tpo.e_commerce3.security.JwtFilter;

import lombok.RequiredArgsConstructor;

// ✅ PASO 7: Configuración central de Spring Security
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UsuarioRepository usuarioRepository;

    // ✅ PASO 7: Carga el usuario desde la BD usando el email como identificador
    // Spring Security lo llama automáticamente durante la autenticación
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    // ✅ PASO 7: El AuthenticationManager coordina la validación de credenciales
    // Lo inyectamos en AuthenticationService para el método authenticate()
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ✅ PASO 7: BCrypt encripta las contraseñas antes de guardarlas
    // También las usa internamente para comparar al hacer login
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ PASO 7: Define qué rutas son públicas y cuáles requieren autenticación
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // las APIs REST no usan CSRF
            .authorizeHttpRequests(auth -> auth

                // RUTAS PÚBLICAS — no necesitan token
                .requestMatchers("/api/auth/**").permitAll()           // register y login
                .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()  // ver productos sin loguearse
                .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll() // ver categorías sin loguearse

                // RUTAS DE ADMIN — solo usuarios con rol ADMIN
                .requestMatchers("/api/usuarios/**").hasRole(Role.ADMIN.name())

                // RUTAS PROTEGIDAS — cualquier usuario autenticado (USER o ADMIN)
                .requestMatchers("/api/carritos/**").authenticated()
                .requestMatchers("/api/perfiles/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/productos/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/productos/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/productos/**").authenticated()

                // Cualquier otra ruta también requiere autenticación
                .anyRequest().authenticated()
            )
            // ✅ PASO 7: Insertar el JwtFilter antes del filtro de Spring Security
            // Se ejecuta en cada request para validar el token del header Authorization
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
