package com.parchemos.travel.service;

import com.parchemos.travel.exception.BadRequestException;
import com.parchemos.travel.exception.ResourceNotFoundException;
import com.parchemos.travel.model.Presupuesto;
import com.parchemos.travel.model.Usuario;
import com.parchemos.travel.repository.PresupuestoRepository;
import com.parchemos.travel.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PresupuestoServiceJpaImpl implements PresupuestoService {

    private final PresupuestoRepository presupuestoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EntityManager entityManager;

    @Autowired
    public PresupuestoServiceJpaImpl(PresupuestoRepository presupuestoRepository,
                                       UsuarioRepository usuarioRepository,
                                       EntityManager entityManager) {
        this.presupuestoRepository = presupuestoRepository;
        this.usuarioRepository = usuarioRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Presupuesto> findAll(Pageable pageable) {
        return presupuestoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Presupuesto findById(Integer id) {
        return presupuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Presupuesto> findByUsuarioId(Integer usuarioId, Pageable pageable) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId);
        }
        return presupuestoRepository.findByUsuarioUsuarioId(usuarioId, pageable);
    }

    @Override
    public Presupuesto save(Presupuesto presupuesto, Integer usuarioId) {
        validarPresupuesto(presupuesto, usuarioId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));
        if (presupuesto.getFechaCreacion() == null) {
            presupuesto.setFechaCreacion(LocalDateTime.now());
        }
        presupuesto.setUsuario(usuario);
        return presupuestoRepository.save(presupuesto);
    }

    @Override
    public Presupuesto update(Integer id, Presupuesto presupuesto, Integer usuarioId) {
        if (!presupuestoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Presupuesto no encontrado para actualizar con id: " + id);
        }
        validarPresupuesto(presupuesto, usuarioId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));
        presupuesto.setPresupuestoId(id);
        presupuesto.setUsuario(usuario);
        return entityManager.merge(presupuesto);
    }

    @Override
    public void deleteById(Integer id) {
        if (!presupuestoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Presupuesto no encontrado para eliminar con id: " + id);
        }
        presupuestoRepository.deleteById(id);
    }

    private void validarPresupuesto(Presupuesto presupuesto, Integer usuarioId) {
        if (usuarioId == null) {
            throw new BadRequestException("El usuario_id es obligatorio");
        }
        if (presupuesto.getTipoViajero() == null) {
            throw new BadRequestException("El tipo de viajero es obligatorio");
        }
        if (presupuesto.getMontoMaximo() == null || presupuesto.getMontoMaximo().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("El monto maximo debe ser mayor a 0");
        }
    }
}
