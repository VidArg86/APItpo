package com.uade.tpo.e_commerce3.model;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.*;
import java.util.List;

@Data
@Entity
@Table(name = "perfiles")
public class Perfil {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;
  private String apellido;
  private String dni;
  private String telefono;
  private String direccion;

  @OneToOne
  @JoinColumn(name = "usuario_id", nullable = false, unique = true)
  private Usuario usuario;

  // Perfil solo puede ser una imagen.
  @OneToOne(mappedBy = "perfil", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Imagen imagenes;
}
