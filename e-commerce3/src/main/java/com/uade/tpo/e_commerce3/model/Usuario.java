package com.uade.tpo.e_commerce3.model;
 
import java.util.Collection;
import java.util.List;
 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
 
import com.fasterxml.jackson.annotation.JsonIgnore;
 
import jakarta.persistence.*;
import lombok.*;
 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(unique = true, nullable = false)
    private String email;
 
    // La contraseña se llama "password" para que Spring Security la encuentre automáticamente
    @Column(nullable = false)
    private String password;
 
    @Enumerated(EnumType.STRING)
    private Rol rol;
 
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private Perfil perfil;
 
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private Carrito carrito;
 
    // --- Métodos de UserDetails (requeridos por Spring Security) ---
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + (rol != null ? rol.name() : "CONSUMIDOR")));
    }
 
    // Spring Security usa getUsername() para identificar al usuario → devolvemos el email
    @Override
    public String getUsername() {
        return email;
    }
 
    @Override
    public boolean isAccountNonExpired()     { return true; }
 
    @Override
    public boolean isAccountNonLocked()      { return true; }
 
    @Override
    public boolean isCredentialsNonExpired() { return true; }
 
    @Override
    public boolean isEnabled()               { return true; }
}