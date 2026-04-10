package com.uade.tpo.e_commerce3.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uade.tpo.e_commerce3.dto.LoginRequest;
import com.uade.tpo.e_commerce3.dto.RegisterRequest;
import com.uade.tpo.e_commerce3.model.Role;
import com.uade.tpo.e_commerce3.model.Usuario;
import com.uade.tpo.e_commerce3.repository.UsuarioRepository;
import com.uade.tpo.e_commerce3.security.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

// ✅ PASO 6: Service con la lógica de autenticación
@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // ✅ PASO 6: Registro de nuevo usuario
    public String register(RegisterRequest request) {

        // 1. Validar que el email no esté duplicado
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya existe en la base de datos");
        }

        // 2. Construir el usuario con el patrón Builder de Lombok (@Builder en Usuario)
        //    La contraseña se encripta con BCrypt — NUNCA se guarda en texto plano
        Usuario usuario = Usuario.builder()
                .nombreUsuario(request.getNombreUsuario())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // ← encriptación BCrypt
                .role(Role.USER) // todos los nuevos usuarios arrancan como USER
                .build();

        // 3. Persistir en la base de datos
        usuarioRepository.save(usuario);

        return "Usuario registrado exitosamente";
    }

    // ✅ PASO 6: Login — valida credenciales y devuelve token JWT
    public String authenticate(LoginRequest request) {

        // 1. Spring Security verifica email + password contra la base de datos
        //    Si las credenciales son incorrectas, lanza BadCredentialsException automáticamente
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        // 2. Obtener el usuario de la BD (ya sabemos que existe porque pasó la autenticación)
        Usuario user = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();

        // 3. Extraer los roles del usuario
        Set<String> roles = user.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toSet());

        // 4. Generar y devolver el token JWT firmado
        return jwtUtil.generateToken(user.getEmail(), roles);
    }
}
