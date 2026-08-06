package com.parchemos.travel.service;

import com.parchemos.travel.exception.BadRequestException;
import com.parchemos.travel.exception.ResourceNotFoundException;
import com.parchemos.travel.model.Paquete;
import com.parchemos.travel.model.Resena;
import com.parchemos.travel.model.Usuario;
import com.parchemos.travel.repository.PaqueteRepository;
import com.parchemos.travel.repository.ResenaRepository;
import com.parchemos.travel.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ResenaServiceJpaImpl implements ResenaService {

    private final ResenaRepository resenaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PaqueteRepository paqueteRepository;
    private final EntityManager entityManager;

    @Autowired
    public ResenaServiceJpaImpl(ResenaRepository resenaRepository,
                                UsuarioRepository usuarioRepository,
                                PaqueteRepository paqueteRepository,
                                EntityManager entityManager) {
        this.resenaRepository = resenaRepository;
        this.usuarioRepository = usuarioRepository;
        this.paqueteRepository = paqueteRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Resena> findAll(Pageable pageable) {
        return resenaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Resena findById(Integer id) {
        return resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resena no encontrada con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Resena> findByPaqueteId(Integer paqueteId, Pageable pageable) {
        if (!paqueteRepository.existsById(paqueteId)) {
            throw new ResourceNotFoundException("Paquete no encontrado con id: " + paqueteId);
        }
        return resenaRepository.findByPaquetePaqueteId(paqueteId, pageable);
    }

    @Override
    public Resena save(Resena resena, Integer usuarioId, Integer paqueteId) {
        validarResena(resena, usuarioId, paqueteId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));
        Paquete paquete = paqueteRepository.findById(paqueteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paquete no encontrado con id: " + paqueteId));
        if (resena.getFecha() == null) {
            resena.setFecha(LocalDateTime.now());
        }
        resena.setUsuario(usuario);
        resena.setPaquete(paquete);
        return resenaRepository.save(resena);
    }

    @Override
    public Resena update(Integer id, Resena resena, Integer usuarioId, Integer paqueteId) {
        if (!resenaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resena no encontrada para actualizar con id: " + id);
        }
        validarResena(resena, usuarioId, paqueteId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));
        Paquete paquete = paqueteRepository.findById(paqueteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paquete no encontrado con id: " + paqueteId));
        resena.setResenaId(id);
        resena.setUsuario(usuario);
        resena.setPaquete(paquete);
        return entityManager.merge(resena);
    }

    @Override
    public void deleteById(Integer id) {
        if (!resenaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resena no encontrada para eliminar con id: " + id);
        }
        resenaRepository.deleteById(id);
    }

    private void validarResena(Resena resena, Integer usuarioId, Integer paqueteId) {
        if (usuarioId == null) {
            throw new BadRequestException("El usuario_id es obligatorio");
        }
        if (paqueteId == null) {
            throw new BadRequestException("El paquete_id es obligatorio");
        }
        if (resena.getCalificacion() == null || resena.getCalificacion() < 1 || resena.getCalificacion() > 5) {
            throw new BadRequestException("La calificacion debe estar entre 1 y 5");
        }
    }
}
