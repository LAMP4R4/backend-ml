package com.lamp.amazonas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamp.amazonas.modelo.DetalleVentaEntity;
import com.lamp.amazonas.services.DetalleVentaServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/detalle-ventas")
@CrossOrigin(origins = {"http://localhost:5173", "http://qyjcnbbsv9tzdlofdphp4cqe.168.231.67.126.sslip.io"})
@RequiredArgsConstructor
public class DetalleVentaController {

    private final DetalleVentaServices servicio;

    @GetMapping
    public ResponseEntity<List<DetalleVentaEntity>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVentaEntity> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<DetalleVentaEntity>> obtenerPorVenta(@PathVariable Long ventaId) {
        return ResponseEntity.ok(servicio.obtenerPorVenta(ventaId));
    }

    @PostMapping("/")
    public ResponseEntity<DetalleVentaEntity> agregar(@RequestBody DetalleVentaEntity detalle) {
        return new ResponseEntity<>(servicio.guardar(detalle), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody DetalleVentaEntity detalle) {
        try {
            return ResponseEntity.ok(servicio.actualizar(id, detalle));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
