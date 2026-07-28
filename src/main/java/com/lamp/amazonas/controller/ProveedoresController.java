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

import com.lamp.amazonas.modelo.ProveedoresEntity;
import com.lamp.amazonas.services.ProveedoresService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/proveedores")
@CrossOrigin(origins = {"http://localhost:5173", "http://qyjcnbbsv9tzdlofdphp4cqe.168.231.67.126.sslip.io"})
@RequiredArgsConstructor
public class ProveedoresController {

    private final ProveedoresService servicio;

    @GetMapping("/")
    public ResponseEntity<List<ProveedoresEntity>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedoresEntity> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @PostMapping("/")
    public ResponseEntity<ProveedoresEntity> agregar(@RequestBody ProveedoresEntity proveedor) {
        return new ResponseEntity<>(servicio.guardar(proveedor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ProveedoresEntity proveedor) {
        try {
            return ResponseEntity.ok(servicio.actualizar(id, proveedor));
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
