package com.uade.tpo.e_commerce3.dto;

import lombok.Data;

// ✅ PASO 4: DTO para el registro de nuevos usuarios
// Solo contiene los campos necesarios para registrarse
// La entidad Usuario tiene más campos (id, role, perfil) que el cliente no debe enviar
@Data
public class RegisterRequest {
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
}
