package com.lamp.amazonas.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.lamp.amazonas.modelo.ProductoEntity;
import org.springframework.transaction.annotation.Transactional;
import com.lamp.amazonas.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ProductoService {
    private final ProductoRepository repository;

    // leer todo los servicios de productos
    @Transactional(readOnly = true)
    public List<ProductoEntity> obtenerTodo() {
        return repository.findAll();
    }

    // leer un producto por id
    @Transactional(readOnly = true)
    public ProductoEntity obtenerPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    // guardar un producto
    @Transactional
    public ProductoEntity guardarProducto(ProductoEntity producto) {
        return repository.save(producto);
    }

    // eliminar un producto
    @Transactional
    public void eliminarProducto(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado, no se puede eliminar");
        }
        repository.deleteById(id);
    }

    //// actualizar un producto
    @Transactional
    public ProductoEntity actualizarProducto(Long id, ProductoEntity detalleProductoEntity) {
        ProductoEntity productoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado, no se puede actualizar"));

        BeanUtils.copyProperties(detalleProductoEntity, productoExistente, "id");

        return repository.save(productoExistente);
    }
}
