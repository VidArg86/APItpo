package com.uade.tpo.e_commerce3.dto;

import lombok.Data;

@Data
public class RegistrationRequestDTO {
  // Auth fields
  private String email;
  private String contraseña;

  // Profile fields
  private String nombre;
  private String apellido;
  private String dni;
  private String telefono;
  private String direccion;
}
