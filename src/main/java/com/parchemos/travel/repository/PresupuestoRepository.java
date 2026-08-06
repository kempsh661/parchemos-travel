package com.parchemos.travel.repository;

import com.parchemos.travel.model.Presupuesto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Integer> {

    List<Presupuesto> findByUsuarioUsuarioId(Integer usuarioId);

    Page<Presupuesto> findByUsuarioUsuarioId(Integer usuarioId, Pageable pageable);

    boolean existsByUsuarioUsuarioId(Integer usuarioId);
}
