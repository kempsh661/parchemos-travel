package com.parchemos.travel.service;

import com.parchemos.travel.model.Resena;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResenaService {

    Page<Resena> findAll(Pageable pageable);

    Resena findById(Integer id);

    Page<Resena> findByPaqueteId(Integer paqueteId, Pageable pageable);

    Resena save(Resena resena, Integer usuarioId, Integer paqueteId);

    Resena update(Integer id, Resena resena, Integer usuarioId, Integer paqueteId);

    void deleteById(Integer id);
}
