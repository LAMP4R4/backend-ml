package com.lamp.amazonas.controller;

import java.security.Principal;
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

import com.lamp.amazonas.modelo.VentasEntity;
import com.lamp.amazonas.services.VentasService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ventas")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class VentasController {

    private final VentasService servicio;

    @GetMapping
    public ResponseEntity<List<VentasEntity>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentasEntity> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));

    }

    @GetMapping("/mis-compras")
    public ResponseEntity<List<VentasEntity>> listarMisCompras(Principal principal) {
        String email = principal.getName();
        return ResponseEntity.ok(servicio.obtenerVentasPorCliente(email));

    }

    @PostMapping("/")
    public ResponseEntity<?> agregar(@RequestBody VentasEntity venta, Principal principal) {
        try {
            String email = principal.getName();
            VentasEntity nuevaVenta = servicio.procesarVenta(venta, email);
            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody VentasEntity venta) {
        try {
            return ResponseEntity.ok(servicio.actualizar(id, venta));
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
