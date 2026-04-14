package com.uade.tpo.e_commerce3.model;

import jakarta.persistence.*;
import lombok.Data;

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
}
