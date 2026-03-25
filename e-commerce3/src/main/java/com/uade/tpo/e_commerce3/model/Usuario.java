package com.uade.tpo.e_commerce3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private int DNI;

    private String numeroTelefonico;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasenia;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String salt;
    }