package com.uade.tpo.e_commerce3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.e_commerce3.model.Perfil;
import com.uade.tpo.e_commerce3.service.PerfilService;

@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {

  @Autowired
  private PerfilService perfilService;

  // GET PERFIL POR ID (DTO) //
  @GetMapping("/{id}")
  public ResponseEntity<?> obtenerPerfil(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(
          perfilService.obtenerPerfilDTO(id));
    } catch (RuntimeException e) {
      return ResponseEntity.status(404)
          .body("Perfil no encontrado con ID: " + id);
    }
  }

  // GET ALL PERFILES //
  @GetMapping
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok(
        perfilService.obtenerTodos());
  }

  // UPDATE PERFIL //
  @PutMapping("/{id}")
  public ResponseEntity<?> update(
      @PathVariable Long id,
      @RequestBody Perfil perfil) {

    perfilService.actualizarPerfil(id, perfil);

    return ResponseEntity.ok(
        perfilService.obtenerPerfilDTO(id));
  }

  // GET POR USUARIO (PRO) //
  @GetMapping("/usuario/{id}")
  public ResponseEntity<?> obtenerPerfilPorUsuario(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(
          perfilService.obtenerPerfilPorUsuario(id));
    } catch (RuntimeException e) {
      return ResponseEntity.status(404)
          .body("Perfil no encontrado para el usuario ID: " + id);
    }
  }
}