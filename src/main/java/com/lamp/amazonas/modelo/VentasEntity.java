package com.lamp.amazonas.modelo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ventas")
@Data
public class VentasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private java.time.LocalDateTime fecha;

    private Double total;

    private String estadoPago;

    // ........RELACIONES FORANEAS (FK).......
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    // llave foranea de venta
    private ClienteEntity cliente;
    // mapeo de la relacion uno a muchos con detalle de venta
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    // Relacion uno a muchos con detalle de venta
    private List<DetalleVentaEntity> detalle = new ArrayList<>();

    public VentasEntity() {
    }
}
