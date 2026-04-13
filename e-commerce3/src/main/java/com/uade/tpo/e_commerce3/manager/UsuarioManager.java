package com.uade.tpo.e_commerce3.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.uade.tpo.e_commerce3.model.Usuario;
import com.uade.tpo.e_commerce3.model.Perfil;
import com.uade.tpo.e_commerce3.model.Carrito;
import com.uade.tpo.e_commerce3.model.Rol;
import com.uade.tpo.e_commerce3.repository.UsuarioRepository;
import com.uade.tpo.e_commerce3.repository.PerfilRepository;
import com.uade.tpo.e_commerce3.repository.CarritoRepository;

@Service
public class UsuarioManager {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PerfilRepository perfilRepository;

  @Autowired
  private CarritoRepository carritoRepository;

  @Transactional
  public Usuario registrarConsumidor(Usuario usuario, String nombre, String apellido, String dni, String telefono, String direccion) {
    if (usuarioRepository.existsByEmail(usuario.getEmail())) {
      throw new RuntimeException("El email ya está registrado.");
    }

    // 1. Set the role
    usuario.setRol(Rol.CONSUMIDOR);

    Usuario savedUsuario = usuarioRepository.save(usuario);

    // 2. Create and link the Carrito
    // By creating it here, we ensure a 1:1 relationship from the start
    Carrito nuevoCarrito = new Carrito();
    nuevoCarrito.setPrecioTotal(0.0);
    nuevoCarrito.setUsuario(usuario); // Link the objects
    usuario.setCarrito(nuevoCarrito); // Bi-directional link

    carritoRepository.save(nuevoCarrito);

    // 3. Save the Usuario (Cascade will handle the Carrito if configured, 

    // 4. Create and link the Perfil
    Perfil nuevoPerfil = new Perfil();
    nuevoPerfil.setNombre(nombre);
    nuevoPerfil.setApellido(apellido);
    nuevoPerfil.setDni(dni);
    nuevoPerfil.setUsuario(savedUsuario);
    nuevoPerfil.setTelefono(telefono); 
    nuevoPerfil.setDireccion(direccion);
    
    perfilRepository.save(nuevoPerfil);
    
    return savedUsuario;
  }
}
