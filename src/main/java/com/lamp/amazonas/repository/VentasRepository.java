package com.lamp.amazonas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lamp.amazonas.modelo.VentasEntity;

public interface VentasRepository extends JpaRepository<VentasEntity, Long> {
    List<VentasEntity> findByClienteEmail(String email);
}
