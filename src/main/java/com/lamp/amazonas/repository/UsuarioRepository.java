package com.lamp.amazonas.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.lamp.amazonas.modelo.UsuarioEntity;

@Repository

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByUsername(String username);

    boolean existsByUsername(String username);

}
