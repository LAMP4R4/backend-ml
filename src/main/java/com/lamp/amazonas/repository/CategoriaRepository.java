package com.lamp.amazonas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lamp.amazonas.modelo.CategoriaEntity;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
}
