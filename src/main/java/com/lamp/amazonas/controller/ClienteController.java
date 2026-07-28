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

import com.lamp.amazonas.modelo.ClienteEntity;
import com.lamp.amazonas.services.ClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/clientes")
@CrossOrigin(origins = {"http://localhost:5173", "http://qyjcnbbsv9tzdlofdphp4cqe.168.231.67.126.sslip.io"})
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService servicio;

    @GetMapping
    public ResponseEntity<List<ClienteEntity>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @PostMapping("/")
    public ResponseEntity<ClienteEntity> agregar(@RequestBody ClienteEntity cliente) {
        return new ResponseEntity<>(servicio.guardar(cliente), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ClienteEntity cliente) {
        try {
            return ResponseEntity.ok(servicio.actualizar(id, cliente));
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
