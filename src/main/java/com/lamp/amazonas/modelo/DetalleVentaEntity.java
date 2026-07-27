package com.lamp.amazonas.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "detalle_venta")

@Data
public class DetalleVentaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    private Double precioUnitario;

    private Double subtotal;

    // ........RELACIONES FORANEAS (FK).......
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "venta_id") // llave foranea de venta
    private VentasEntity venta;

    @ManyToOne
    @JoinColumn(name = "pro_id") // llave foranea de producto
    private ProductoEntity producto;

    public DetalleVentaEntity() {
    }
}
