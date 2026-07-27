package com.lamp.amazonas.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lamp.amazonas.modelo.ProveedoresEntity;
import com.lamp.amazonas.repository.ProveedoresRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProveedoresService {

    private final ProveedoresRepository repository;

    @Transactional(readOnly = true)
    public List<ProveedoresEntity> obtenerTodo() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public ProveedoresEntity obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + id));
    }

    @Transactional
    public ProveedoresEntity guardar(ProveedoresEntity proveedor) {
        return repository.save(proveedor);
    }

    @Transactional
    public ProveedoresEntity actualizar(Long id, ProveedoresEntity detalle) {
        ProveedoresEntity existente = obtenerPorId(id);
        BeanUtils.copyProperties(detalle, existente, "id");
        return repository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Proveedor no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }
}
