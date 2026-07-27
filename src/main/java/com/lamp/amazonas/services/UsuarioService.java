package com.lamp.amazonas.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lamp.amazonas.dto.RegistroRequest;
import com.lamp.amazonas.modelo.ClienteEntity;
import com.lamp.amazonas.modelo.Rol;
import com.lamp.amazonas.modelo.UsuarioEntity;
import com.lamp.amazonas.repository.ClienteRepository;
import com.lamp.amazonas.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioEntity saveUsuario(RegistroRequest request) {
        // encode the password before saving

        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya esta en uso");
        }
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setNombre(request.getNombre());
        usuario.setDireccion(request.getDireccion());
        usuario.setTelefono(request.getTelefono());

        Rol rol = Rol.ROLE_CLIENTE;// ROLE DEFAULT
        if (request.getRol() != null && request.getRol().equalsIgnoreCase("ROLE_ADMIN")) {
            rol = Rol.ROLE_ADMIN;

        }
        usuario.setRole(rol);
        UsuarioEntity saveUsuario = usuarioRepository.save(usuario);

        if (rol == rol.ROLE_CLIENTE) {
            ClienteEntity cliente = new ClienteEntity();
            cliente.setNombre(request.getNombre());
            cliente.setEmail(request.getUsername());
            cliente.setDireccion(request.getDireccion());
            cliente.setTelefono(request.getTelefono());
            clienteRepository.save(cliente);

        }

        return saveUsuario;

    }

}
