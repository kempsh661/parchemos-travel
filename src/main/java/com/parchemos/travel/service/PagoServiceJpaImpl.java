package com.parchemos.travel.service;

import com.parchemos.travel.exception.BadRequestException;
import com.parchemos.travel.exception.ResourceNotFoundException;
import com.parchemos.travel.model.EstadoPago;
import com.parchemos.travel.model.Pago;
import com.parchemos.travel.model.Reserva;
import com.parchemos.travel.repository.PagoRepository;
import com.parchemos.travel.repository.ReservaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PagoServiceJpaImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final ReservaRepository reservaRepository;
    private final EntityManager entityManager;

    @Autowired
    public PagoServiceJpaImpl(PagoRepository pagoRepository,
                              ReservaRepository reservaRepository,
                              EntityManager entityManager) {
        this.pagoRepository = pagoRepository;
        this.reservaRepository = reservaRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pago> findAll(Pageable pageable) {
        return pagoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Pago findById(Integer id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pago> findByReservaId(Integer reservaId, Pageable pageable) {
        if (!reservaRepository.existsById(reservaId)) {
            throw new ResourceNotFoundException("Reserva no encontrada con id: " + reservaId);
        }
        return pagoRepository.findByReservaReservaId(reservaId, pageable);
    }

    @Override
    public Pago save(Pago pago, Integer reservaId) {
        validarPago(pago, reservaId);
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + reservaId));
        if (pago.getFechaPago() == null) {
            pago.setFechaPago(LocalDateTime.now());
        }
        if (pago.getEstado() == null) {
            pago.setEstado(EstadoPago.PENDIENTE);
        }
        pago.setReserva(reserva);
        return pagoRepository.save(pago);
    }

    @Override
    public Pago update(Integer id, Pago pago, Integer reservaId) {
        if (!pagoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pago no encontrado para actualizar con id: " + id);
        }
        validarPago(pago, reservaId);
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + reservaId));
        pago.setPagoId(id);
        pago.setReserva(reserva);
        return entityManager.merge(pago);
    }

    @Override
    public void deleteById(Integer id) {
        if (!pagoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pago no encontrado para eliminar con id: " + id);
        }
        pagoRepository.deleteById(id);
    }

    private void validarPago(Pago pago, Integer reservaId) {
        if (reservaId == null) {
            throw new BadRequestException("El reserva_id es obligatorio");
        }
        if (pago.getMonto() == null || pago.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("El monto debe ser mayor a 0");
        }
        if (pago.getMetodoPago() == null) {
            throw new BadRequestException("El metodo de pago es obligatorio");
        }
    }
}
