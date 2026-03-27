package com.uade.tpo.e_commerce3.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "carritos")
public class Carrito {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreUsuario;

    @Column(nullable = false)
    private Double precioTotal;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id") // This column will be created in the Producto table
    private List<Producto> productos = new ArrayList<>();
}
