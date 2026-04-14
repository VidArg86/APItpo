package com.uade.tpo.e_commerce3.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class RegistrationRequestDTO {
    private String email;

    @JsonProperty("contraseña")  // ← esto le dice a Jackson cómo leer el campo del JSON
    private String passworld;

    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String direccion;
}
