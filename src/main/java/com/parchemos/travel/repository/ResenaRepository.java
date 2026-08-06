package com.parchemos.travel.repository;

import com.parchemos.travel.model.Resena;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Integer> {

    List<Resena> findByPaquetePaqueteId(Integer paqueteId);

    Page<Resena> findByPaquetePaqueteId(Integer paqueteId, Pageable pageable);

    List<Resena> findByUsuarioUsuarioId(Integer usuarioId);

    boolean existsByUsuarioUsuarioId(Integer usuarioId);
}
