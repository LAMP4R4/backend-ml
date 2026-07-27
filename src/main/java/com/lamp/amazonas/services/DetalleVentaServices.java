package com.lamp.amazonas.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lamp.amazonas.modelo.DetalleVentaEntity;
import com.lamp.amazonas.repository.DetalleVentaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetalleVentaServices {

    private final DetalleVentaRepository repository;

    @Transactional(readOnly = true)
    public List<DetalleVentaEntity> obtenerTodo() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public DetalleVentaEntity obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<DetalleVentaEntity> obtenerPorVenta(Long ventaId) {
        return repository.findByVentaId(ventaId);
    }

    @Transactional
    public DetalleVentaEntity guardar(DetalleVentaEntity detalle) {
        return repository.save(detalle);
    }

    @Transactional
    public DetalleVentaEntity actualizar(Long id, DetalleVentaEntity nuevoDetalle) {
        DetalleVentaEntity existente = obtenerPorId(id);
        BeanUtils.copyProperties(nuevoDetalle, existente, "id");
        return repository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Detalle de venta no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

}
