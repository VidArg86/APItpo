package com.uade.tpo.e_commerce3.model;


import jakarta.persistence.*;
import lombok.Data;
import com.uade.tpo.e_commerce3.model.enums.Categoria;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "productos")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    private Integer stock;

    @Enumerated(EnumType.STRING)  // guarda el nombre ("CATEGORIA1") en vez del índice (5)
    @Column(nullable = false)
    private Categoria categoria;

    @Lob
    @Column(nullable = false)
    private String imagen;

}
