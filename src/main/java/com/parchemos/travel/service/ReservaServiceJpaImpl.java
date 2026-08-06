package com.parchemos.travel.service;

import com.parchemos.travel.exception.BadRequestException;
import com.parchemos.travel.exception.ConflictException;
import com.parchemos.travel.exception.ResourceNotFoundException;
import com.parchemos.travel.model.*;
import com.parchemos.travel.repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReservaServiceJpaImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PaqueteRepository paqueteRepository;
    private final PresupuestoRepository presupuestoRepository;
    private final PagoRepository pagoRepository;
    private final EntityManager entityManager;

    @Autowired
    public ReservaServiceJpaImpl(ReservaRepository reservaRepository,
                                 UsuarioRepository usuarioRepository,
                                 PaqueteRepository paqueteRepository,
                                 PresupuestoRepository presupuestoRepository,
                                 PagoRepository pagoRepository,
                                 EntityManager entityManager) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.paqueteRepository = paqueteRepository;
        this.presupuestoRepository = presupuestoRepository;
        this.pagoRepository = pagoRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reserva> findAll(Pageable pageable) {
        return reservaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Reserva findById(Integer id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reserva> findByUsuarioId(Integer usuarioId, Pageable pageable) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId);
        }
        return reservaRepository.findByUsuarioUsuarioId(usuarioId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reserva> findByPaqueteId(Integer paqueteId, Pageable pageable) {
        if (!paqueteRepository.existsById(paqueteId)) {
            throw new ResourceNotFoundException("Paquete no encontrado con id: " + paqueteId);
        }
        return reservaRepository.findByPaquetePaqueteId(paqueteId, pageable);
    }

    @Override
    @Transactional
    public Reserva save(Reserva reserva, Integer usuarioId, Integer paqueteId, Integer presupuestoId) {
        validarReserva(reserva, usuarioId, paqueteId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));
        Paquete paquete = paqueteRepository.findById(paqueteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paquete no encontrado con id: " + paqueteId));
        reserva.setUsuario(usuario);
        reserva.setPaquete(paquete);
        reserva.setPresupuesto(obtenerPresupuestoOpcional(presupuestoId, usuarioId));
        if (reserva.getFechaReserva() == null) {
            reserva.setFechaReserva(LocalDateTime.now());
        }
        if (reserva.getEstado() == null) {
            reserva.setEstado(EstadoReserva.PENDIENTE);
        }
        return reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public Reserva update(Integer id, Reserva reserva, Integer usuarioId, Integer paqueteId, Integer presupuestoId) {
        if (!reservaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reserva no encontrada para actualizar con id: " + id);
        }
        validarReserva(reserva, usuarioId, paqueteId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));
        Paquete paquete = paqueteRepository.findById(paqueteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paquete no encontrado con id: " + paqueteId));
        reserva.setReservaId(id);
        reserva.setUsuario(usuario);
        reserva.setPaquete(paquete);
        reserva.setPresupuesto(obtenerPresupuestoOpcional(presupuestoId, usuarioId));
        return entityManager.merge(reserva);
    }

    @Override
    public void deleteById(Integer id) {
        if (!reservaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reserva no encontrada para eliminar con id: " + id);
        }
        if (pagoRepository.existsByReservaReservaId(id)) {
            throw new ConflictException("No se puede eliminar la reserva porque tiene pagos asociados");
        }
        reservaRepository.deleteById(id);
    }

    private Presupuesto obtenerPresupuestoOpcional(Integer presupuestoId, Integer usuarioId) {
        if (presupuestoId == null) {
            return null;
        }
        Presupuesto presupuesto = presupuestoRepository.findById(presupuestoId)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado con id: " + presupuestoId));
        if (!presupuesto.getUsuario().getUsuarioId().equals(usuarioId)) {
            throw new BadRequestException("El presupuesto no pertenece al usuario indicado");
        }
        return presupuesto;
    }

    private void validarReserva(Reserva reserva, Integer usuarioId, Integer paqueteId) {
        if (usuarioId == null) {
            throw new BadRequestException("El usuario_id es obligatorio");
        }
        if (paqueteId == null) {
            throw new BadRequestException("El paquete_id es obligatorio");
        }
        if (reserva.getNumPersonas() == null || reserva.getNumPersonas() <= 0) {
            throw new BadRequestException("El numero de personas debe ser mayor a 0");
        }
    }
}
