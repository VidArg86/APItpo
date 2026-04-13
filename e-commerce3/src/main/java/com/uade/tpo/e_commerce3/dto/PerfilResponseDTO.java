package com.uade.tpo.e_commerce3.dto;

import lombok.Data;

@Data
public class PerfilResponseDTO {

  private Long id;
  private String nombreCompleto;
  private String telefono;
  private String direccion;
  private String fechaNacimiento;

  private Long usuarioId;
  private String nombreUsuario;
}
