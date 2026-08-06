package com.parchemos.travel.service;

import com.parchemos.travel.dto.PaqueteDetalleDTO;
import com.parchemos.travel.model.Paquete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaqueteService {

    Page<Paquete> findAll(Pageable pageable);

    Page<PaqueteDetalleDTO> findAllDetalle(Pageable pageable);

    Paquete findById(Integer id);

    Page<Paquete> findByDestinoId(Integer destinoId, Pageable pageable);

    Paquete save(Paquete paquete, Integer destinoId);

    Paquete update(Integer id, Paquete paquete, Integer destinoId);

    void deleteById(Integer id);
}
