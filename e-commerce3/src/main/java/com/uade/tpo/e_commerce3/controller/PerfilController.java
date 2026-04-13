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

  // CREATE PERFIL //
  @PostMapping("/usuario/{usuarioId}")
  public ResponseEntity<?> crearPerfil(
      @PathVariable Long usuarioId,
      @RequestBody Perfil perfil) {

    return ResponseEntity.ok(
        perfilService.crearPerfil(usuarioId, perfil));
  }

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

  // DELETE PERFIL //
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    perfilService.eliminarPerfil(id);
    return ResponseEntity.noContent().build();
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
