package com.tienda.modelo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Producto {
    private int idProducto;
    private String nombre;
    private BigDecimal precio;
    private int stock;
}

