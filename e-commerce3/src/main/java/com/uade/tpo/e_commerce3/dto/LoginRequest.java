package com.uade.tpo.e_commerce3.dto;
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest {
    private String email;

    @JsonProperty("contraseña")
    private String passworld;
}