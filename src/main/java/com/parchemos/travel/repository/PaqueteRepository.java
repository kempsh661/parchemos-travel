package com.parchemos.travel.repository;

import com.parchemos.travel.model.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaqueteRepository extends JpaRepository<Paquete, Integer> {

    @EntityGraph(attributePaths = {"destino"})
    @Query("SELECT p FROM Paquete p")
    Page<Paquete> findAllWithDestino(Pageable pageable);

    @EntityGraph(attributePaths = {"destino"})
    Page<Paquete> findByDestinoDestinoId(Integer destinoId, Pageable pageable);

    List<Paquete> findByDestinoDestinoId(Integer destinoId);

    boolean existsByDestinoDestinoId(Integer destinoId);
}
