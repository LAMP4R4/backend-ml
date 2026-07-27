package com.lamp.amazonas.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lamp.amazonas.modelo.ClienteEntity;
import com.lamp.amazonas.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional(readOnly = true)
    public List<ClienteEntity> obtenerTodo() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public ClienteEntity obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }

    @Transactional
    public ClienteEntity guardar(ClienteEntity cliente) {
        return repository.save(cliente);
    }

    @Transactional
    public ClienteEntity actualizar(Long id, ClienteEntity detalle) {
        ClienteEntity existente = obtenerPorId(id);
        BeanUtils.copyProperties(detalle, existente, "id");
        return repository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }
}
