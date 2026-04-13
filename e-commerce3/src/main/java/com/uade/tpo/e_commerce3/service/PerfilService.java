package com.uade.tpo.e_commerce3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.e_commerce3.dto.PerfilResponseDTO;
import com.uade.tpo.e_commerce3.model.Perfil;
import com.uade.tpo.e_commerce3.model.Usuario;
import com.uade.tpo.e_commerce3.repository.PerfilRepository;
import com.uade.tpo.e_commerce3.repository.UsuarioRepository;

@Service
public class PerfilService {

  @Autowired
  private PerfilRepository perfilRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  // CREATE PERFIL //
  public Perfil crearPerfil(Long usuarioId, Perfil perfil) {
    Usuario usuario = usuarioRepository.findById(usuarioId)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    perfil.setUsuario(usuario);

    return perfilRepository.save(perfil);
  }

  // GET POR ID (con DTO) //
  public PerfilResponseDTO obtenerPerfilDTO(Long id) {
    Perfil perfil = perfilRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

    return mapToDTO(perfil);
  }

  // GET ALL //
  public List<Perfil> obtenerTodos() {
    return perfilRepository.findAll();
  }

  // UPDATE //
  public Perfil actualizarPerfil(Long id, Perfil nuevoPerfil) {
    Perfil perfil = perfilRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

    // Fixed field names to match the 1.0 Model
    perfil.setNombre(nuevoPerfil.getNombre());
    perfil.setApellido(nuevoPerfil.getApellido());
    perfil.setDni(nuevoPerfil.getDni());
    perfil.setTelefono(nuevoPerfil.getTelefono());
    perfil.setDireccion(nuevoPerfil.getDireccion());

    return perfilRepository.save(perfil);
  }

  // DELETE //
  public void eliminarPerfil(Long id) {
    if (!perfilRepository.existsById(id)) {
      throw new RuntimeException("Perfil no encontrado");
    }
    perfilRepository.deleteById(id);
  }

  // GET POR USUARIO (PRO) //
  public PerfilResponseDTO obtenerPerfilPorUsuario(Long usuarioId) {
    Perfil perfil = perfilRepository.findByUsuarioId(usuarioId)
        .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

    return mapToDTO(perfil);
  }

  // MÉTODO PRIVADO PARA MAPEAR //
  private PerfilResponseDTO mapToDTO(Perfil perfil) {
    PerfilResponseDTO dto = new PerfilResponseDTO();
    dto.setId(perfil.getId());

    // Combine fields for the DTO if your DTO still uses "nombreCompleto"
    dto.setNombreCompleto(perfil.getNombre() + " " + perfil.getApellido());

    dto.setTelefono(perfil.getTelefono());
    dto.setDireccion(perfil.getDireccion());
    dto.setUsuarioId(perfil.getUsuario().getId());

    return dto;
  }
}
