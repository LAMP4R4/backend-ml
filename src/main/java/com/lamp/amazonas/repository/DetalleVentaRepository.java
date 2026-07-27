package com.lamp.amazonas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lamp.amazonas.modelo.DetalleVentaEntity;

public interface DetalleVentaRepository extends JpaRepository<DetalleVentaEntity, Long> {

    List<DetalleVentaEntity> findByVentaId(Long ventaId);

}
