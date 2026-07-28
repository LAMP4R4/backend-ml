package com.lamp.amazonas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamp.amazonas.modelo.ProductoEntity;
import com.lamp.amazonas.services.ProductoService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/productos") // mapeo general productos
@CrossOrigin(origins = {"http://localhost:5173", "http://qyjcnbbsv9tzdlofdphp4cqe.168.231.67.126.sslip.io"}) // permiso de react para consumir la api
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService servicio;

    // endpoint para ver todos los productos
    @GetMapping("/") // otra manera de hacer un get mapping es @GetMapping("/")
    public ResponseEntity<List<ProductoEntity>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodo());
    }

    // consultar un producto por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductoEntity> obtenerDetalles(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));// 200 OK
    }

    // eliminar un producto por id
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoEntity> eliminarProducto(@PathVariable Long id) {
        servicio.eliminarProducto(id);
        return ResponseEntity.noContent().build();// 204 No Content

    }

    // Agregar un producto
    @PostMapping("/")
    public ResponseEntity<ProductoEntity> agregarProducto(@RequestBody ProductoEntity producto) {
        ProductoEntity nuevo = servicio.guardarProducto(producto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);// 201 Created
    }

    // Actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ProductoEntity producto) {
        try {
            ProductoEntity productoActualizado = servicio.actualizarProducto(id, producto);
            return ResponseEntity.ok(productoActualizado); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // 404 Not Found
        }
    }
}
