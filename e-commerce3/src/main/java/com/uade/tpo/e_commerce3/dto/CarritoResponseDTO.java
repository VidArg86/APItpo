package com.uade.tpo.e_commerce3.dto;

import java.util.List;

import lombok.Data;

@Data
public class CarritoResponseDTO {
    private Long id;
    private Double precioTotal;
    private List<ItemCarritoDTO> items;
    private Long usuarioId;
}

@Data
class ItemCarritoDTO {
    private Long productoId;
    private String productoNombre;
    private Integer cantidad;
    private Double precioUnitario;
}
