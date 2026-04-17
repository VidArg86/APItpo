package com.uade.tpo.e_commerce3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDetalleDTO {
    private String id;
    private String nombre;
    private Double precio;
    private Integer stockActual;
}