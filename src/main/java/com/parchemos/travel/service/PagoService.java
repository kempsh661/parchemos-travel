package com.parchemos.travel.service;

import com.parchemos.travel.model.Pago;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagoService {

    Page<Pago> findAll(Pageable pageable);

    Pago findById(Integer id);

    Page<Pago> findByReservaId(Integer reservaId, Pageable pageable);

    Pago save(Pago pago, Integer reservaId);

    Pago update(Integer id, Pago pago, Integer reservaId);

    void deleteById(Integer id);
}
