package com.perfulandia.perfulandia.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "detalle")

@Data

@NoArgsConstructor

@AllArgsConstructor
@Entity
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer productoId;

    private Integer cantidad;

    private Integer precioUnitarios;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

}
