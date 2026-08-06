package com.parchemos.travel.repository;

import com.parchemos.travel.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    List<Reserva> findByUsuarioUsuarioId(Integer usuarioId);

    Page<Reserva> findByUsuarioUsuarioId(Integer usuarioId, Pageable pageable);

    List<Reserva> findByPaquetePaqueteId(Integer paqueteId);

    Page<Reserva> findByPaquetePaqueteId(Integer paqueteId, Pageable pageable);

    boolean existsByUsuarioUsuarioId(Integer usuarioId);
}
