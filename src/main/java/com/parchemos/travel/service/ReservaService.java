package com.parchemos.travel.service;

import com.parchemos.travel.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservaService {

    Page<Reserva> findAll(Pageable pageable);

    Reserva findById(Integer id);

    Page<Reserva> findByUsuarioId(Integer usuarioId, Pageable pageable);

    Page<Reserva> findByPaqueteId(Integer paqueteId, Pageable pageable);

    Reserva save(Reserva reserva, Integer usuarioId, Integer paqueteId, Integer presupuestoId);

    Reserva update(Integer id, Reserva reserva, Integer usuarioId, Integer paqueteId, Integer presupuestoId);

    void deleteById(Integer id);
}
