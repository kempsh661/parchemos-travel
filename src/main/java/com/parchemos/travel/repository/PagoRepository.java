package com.parchemos.travel.repository;

import com.parchemos.travel.model.Pago;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    List<Pago> findByReservaReservaId(Integer reservaId);

    Page<Pago> findByReservaReservaId(Integer reservaId, Pageable pageable);

    boolean existsByReservaReservaId(Integer reservaId);
}
