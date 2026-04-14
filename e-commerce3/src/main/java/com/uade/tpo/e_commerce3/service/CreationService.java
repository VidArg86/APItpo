package com.uade.tpo.e_commerce3.service;
 
import java.util.Set;
import java.util.stream.Collectors;
 
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
 
import com.uade.tpo.e_commerce3.dto.LoginRequest;
import com.uade.tpo.e_commerce3.dto.RegistrationRequestDTO;
import com.uade.tpo.e_commerce3.exception.EmailAlreadyExistsException;
import com.uade.tpo.e_commerce3.model.Carrito;
import com.uade.tpo.e_commerce3.model.Perfil;
import com.uade.tpo.e_commerce3.model.Rol;
import com.uade.tpo.e_commerce3.model.Usuario;
import com.uade.tpo.e_commerce3.repository.CarritoRepository;
import com.uade.tpo.e_commerce3.repository.PerfilRepository;
import com.uade.tpo.e_commerce3.repository.UsuarioRepository;
import com.uade.tpo.e_commerce3.security.JwtUtil;
 
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
 
@Service
@Transactional
@RequiredArgsConstructor
public class CreationService {
 
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final CarritoRepository carritoRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
 
    /**
     * Registra un nuevo usuario CONSUMIDOR con su Perfil y Carrito asociados.
     * Devuelve directamente el token JWT para que pueda operar sin hacer login extra.
     */
    public String register(RegistrationRequestDTO request) {
 
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }
 
        // 1. Crear usuario con contraseña encriptada
        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassworld()))
                .rol(Rol.CONSUMIDOR)
                .build();
 
        Usuario savedUsuario = usuarioRepository.save(usuario);
 
        // 2. Crear y vincular el Perfil
        Perfil perfil = new Perfil();
        perfil.setNombre(request.getNombre());
        perfil.setApellido(request.getApellido());
        perfil.setDni(request.getDni());
        perfil.setTelefono(request.getTelefono());
        perfil.setDireccion(request.getDireccion());
        perfil.setUsuario(savedUsuario);
        perfilRepository.save(perfil);
 
        // 3. Crear y vincular el Carrito
        Carrito carrito = new Carrito();
        carrito.setUsuario(savedUsuario);
        carrito.setPrecioTotal(0.0);
        carritoRepository.save(carrito);
 
        // 4. Generar y devolver el token JWT
        Set<String> roles = savedUsuario.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toSet());
 
        return jwtUtil.generateToken(savedUsuario.getEmail(), roles);
    }
 
    /**
     * Valida credenciales y devuelve un token JWT si son correctas.
     */
    public String authenticate(LoginRequest request) {
 
        // Spring Security verifica email + contraseña contra la BD automáticamente
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassworld()
                )
        );
 
        Usuario user = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();
 
        Set<String> roles = user.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toSet());
 
        return jwtUtil.generateToken(user.getEmail(), roles);
    }
}