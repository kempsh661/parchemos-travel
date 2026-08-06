package com.parchemos.travel.service;

import com.parchemos.travel.exception.BadRequestException;
import com.parchemos.travel.exception.ConflictException;
import com.parchemos.travel.exception.ResourceNotFoundException;
import com.parchemos.travel.model.RolUsuario;
import com.parchemos.travel.model.Usuario;
import com.parchemos.travel.repository.PresupuestoRepository;
import com.parchemos.travel.repository.ResenaRepository;
import com.parchemos.travel.repository.ReservaRepository;
import com.parchemos.travel.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UsuarioServiceJpaImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PresupuestoRepository presupuestoRepository;
    private final ReservaRepository reservaRepository;
    private final ResenaRepository resenaRepository;
    private final EntityManager entityManager;

    @Autowired
    public UsuarioServiceJpaImpl(UsuarioRepository usuarioRepository,
                                 PresupuestoRepository presupuestoRepository,
                                 ReservaRepository reservaRepository,
                                 ResenaRepository resenaRepository,
                                 EntityManager entityManager) {
        this.usuarioRepository = usuarioRepository;
        this.presupuestoRepository = presupuestoRepository;
        this.reservaRepository = reservaRepository;
        this.resenaRepository = resenaRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAll(Pageable pageable) {
        System.out.println("Buscando usuarios paginados...");
        return usuarioRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findById(Integer id) {
        System.out.println("Buscando usuario con id: " + id);
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
    }

    @Override
    public Usuario save(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new ConflictException("Ya existe un usuario con el email: " + usuario.getEmail());
        }
        if (usuario.getFechaRegistro() == null) {
            usuario.setFechaRegistro(LocalDateTime.now());
        }
        if (usuario.getRol() == null) {
            usuario.setRol(RolUsuario.CLIENTE);
        }
        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new BadRequestException("La contraseña es obligatoria");
        }
        System.out.println("Guardando usuario: " + usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Integer id, Usuario usuario) {
        System.out.println("Actualizando usuario con id: " + id);
        Usuario existente = findById(id);
        if (!existente.getEmail().equals(usuario.getEmail()) && usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new ConflictException("Ya existe otro usuario con el email: " + usuario.getEmail());
        }
        usuario.setUsuarioId(id);
        usuario.setPassword(existente.getPassword());
        if (usuario.getRol() == null) {
            usuario.setRol(existente.getRol());
        }
        if (usuario.getFechaRegistro() == null) {
            usuario.setFechaRegistro(existente.getFechaRegistro());
        }
        return entityManager.merge(usuario);
    }

    @Override
    public void deleteById(Integer id) {
        System.out.println("Eliminando usuario con id: " + id);
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado para eliminar con id: " + id);
        }
        if (presupuestoRepository.existsByUsuarioUsuarioId(id)
                || reservaRepository.existsByUsuarioUsuarioId(id)
                || resenaRepository.existsByUsuarioUsuarioId(id)) {
            throw new ConflictException("No se puede eliminar el usuario porque tiene registros asociados");
        }
        usuarioRepository.deleteById(id);
        System.out.println("Usuario eliminado correctamente.");
    }
}
