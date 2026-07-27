package com.lamp.amazonas.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lamp.amazonas.modelo.CategoriaEntity;
import com.lamp.amazonas.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    @Transactional(readOnly = true)
    public List<CategoriaEntity> obtenerTodo() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public CategoriaEntity obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: " + id));
    }

    @Transactional
    public CategoriaEntity guardar(CategoriaEntity categoria) {
        return repository.save(categoria);
    }

    @Transactional
    public CategoriaEntity actualizar(Long id, CategoriaEntity detalle) {
        CategoriaEntity existente = obtenerPorId(id);
        BeanUtils.copyProperties(detalle, existente, "id");
        return repository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }
}
