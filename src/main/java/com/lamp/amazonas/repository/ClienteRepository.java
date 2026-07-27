package com.lamp.amazonas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lamp.amazonas.modelo.ClienteEntity;
import java.util.List;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByEmail(String email);

}
