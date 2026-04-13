package com.uade.tpo.e_commerce3.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.uade.tpo.e_commerce3.model.Usuario;
import com.uade.tpo.e_commerce3.model.Perfil;
import com.uade.tpo.e_commerce3.model.Rol;
import com.uade.tpo.e_commerce3.repository.UsuarioRepository;
import com.uade.tpo.e_commerce3.repository.PerfilRepository;

@Service
public class UsuarioManager {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PerfilRepository perfilRepository;

  @Transactional
  public Usuario registrarConsumidor(Usuario usuario, String nombre, String apellido, String dni, String telefono, String direccion) {
    if (usuarioRepository.existsByEmail(usuario.getEmail())) {
      throw new RuntimeException("El email ya está registrado.");
    }

    // Using the Enum instead of a String
    usuario.setRol(Rol.CONSUMIDOR);

    Usuario savedUsuario = usuarioRepository.save(usuario);

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
