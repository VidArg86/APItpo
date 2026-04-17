package com.uade.tpo.e_commerce3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String extension; // e.g., "png", "jpg"

    @Lob
    @Basic(fetch = FetchType.LAZY) // This prevents loading the image until you actually call getData()
    @Column(columnDefinition = "LONGBLOB")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonIgnore
    private Producto producto;

    @OneToOne
    @JoinColumn(name = "perfil_id")
    @JsonIgnore
    private Perfil perfil;

}
