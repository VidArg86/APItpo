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
 
import com.uade.tpo.e_commerce3.model.Rol;
import com.uade.tpo.e_commerce3.repository.UsuarioRepository;
import com.uade.tpo.e_commerce3.security.JwtFilter;
 
import lombok.RequiredArgsConstructor;
 
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
 
    private final JwtFilter jwtFilter;
    private final UsuarioRepository usuarioRepository;
 
    // Le dice a Spring Security cómo cargar un usuario desde la BD usando el email
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
 
    // Expone el AuthenticationManager para que AuthenticationService lo pueda usar
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
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
 
                // ── Rutas públicas (sin token) ──────────────────────────────
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()
 
                // ── Rutas de carrito: solo usuarios autenticados ────────────
                .requestMatchers("/api/carritos/**").authenticated()
 
                // ── Gestión de productos: solo VENDEDOR o ADMIN ─────────────
                .requestMatchers(HttpMethod.POST,   "/api/productos").hasAnyRole(Rol.VENDEDOR.name(), Rol.ADMIN.name())
                .requestMatchers(HttpMethod.PUT,    "/api/productos/**").hasAnyRole(Rol.VENDEDOR.name(), Rol.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasAnyRole(Rol.VENDEDOR.name(), Rol.ADMIN.name())
 
                // ── Gestión de usuarios: solo ADMIN ─────────────────────────
                .requestMatchers("/api/usuarios/**").hasRole(Rol.ADMIN.name())
 
                // ── Cualquier otra ruta requiere autenticación ───────────────
                .anyRequest().authenticated()
            )
            // Agrega el filtro JWT antes del filtro estándar de usuario/contraseña
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
 
        return http.build();
    }
}