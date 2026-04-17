package com.uade.tpo.e_commerce3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarritoSolicitudDTO {
    private String idProducto;
    private Integer cantidad;
}