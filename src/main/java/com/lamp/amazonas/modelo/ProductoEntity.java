package com.lamp.amazonas.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer stock;

    private String imagenUrl;

    @Builder.Default
    private Boolean activo = true;

    // ........RELACIONES FORANEAS (FK).......
    @ManyToOne(fetch = FetchType.EAGER) // el fetch es recomendable usar en cuestionarios
    @JoinColumn(name = "categoria_id") // llave foranea de categoria
    private CategoriaEntity categoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proveedor_id") // llave foranea de proveedor
    private ProveedoresEntity proveedor;

}
