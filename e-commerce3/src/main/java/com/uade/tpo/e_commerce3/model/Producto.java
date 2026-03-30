package com.uade.tpo.e_commerce3.model;


import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import com.uade.tpo.e_commerce3.model.enums.Categoria;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

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
    @Basic(fetch = FetchType.LAZY)
    private String imagen;
}
