package com.parchemos.travel.service;

import com.parchemos.travel.model.Destino;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DestinoService {

    Page<Destino> findAll(Pageable pageable);

    Destino findById(Integer id);

    Destino save(Destino destino);

    Destino update(Integer id, Destino destino);

    void deleteById(Integer id);
}
