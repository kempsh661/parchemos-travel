package com.parchemos.travel.service;

import com.parchemos.travel.model.Presupuesto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PresupuestoService {

    Page<Presupuesto> findAll(Pageable pageable);

    Presupuesto findById(Integer id);

    Page<Presupuesto> findByUsuarioId(Integer usuarioId, Pageable pageable);

    Presupuesto save(Presupuesto presupuesto, Integer usuarioId);

    Presupuesto update(Integer id, Presupuesto presupuesto, Integer usuarioId);

    void deleteById(Integer id);
}
