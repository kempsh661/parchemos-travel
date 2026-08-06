package com.parchemos.travel.service;

import com.parchemos.travel.exception.ConflictException;
import com.parchemos.travel.exception.ResourceNotFoundException;
import com.parchemos.travel.model.Destino;
import com.parchemos.travel.repository.DestinoRepository;
import com.parchemos.travel.repository.PaqueteRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DestinoServiceJpaImpl implements DestinoService {

    private final DestinoRepository destinoRepository;
    private final PaqueteRepository paqueteRepository;
    private final EntityManager entityManager;

    @Autowired
    public DestinoServiceJpaImpl(DestinoRepository destinoRepository,
                                 PaqueteRepository paqueteRepository,
                                 EntityManager entityManager) {
        this.destinoRepository = destinoRepository;
        this.paqueteRepository = paqueteRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Destino> findAll(Pageable pageable) {
        System.out.println("Buscando destinos paginados...");
        return destinoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Destino findById(Integer id) {
        System.out.println("Buscando destino con id: " + id);
        return destinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destino no encontrado con id: " + id));
    }

    @Override
    public Destino save(Destino destino) {
        System.out.println("Guardando destino: " + destino.getNombre());
        return destinoRepository.save(destino);
    }

    @Override
    public Destino update(Integer id, Destino destino) {
        System.out.println("Actualizando destino con id: " + id);
        if (!destinoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Destino no encontrado para actualizar con id: " + id);
        }
        destino.setDestinoId(id);
        return entityManager.merge(destino);
    }

    @Override
    public void deleteById(Integer id) {
        System.out.println("Eliminando destino con id: " + id);
        if (!destinoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Destino no encontrado para eliminar con id: " + id);
        }
        if (paqueteRepository.existsByDestinoDestinoId(id)) {
            throw new ConflictException("No se puede eliminar el destino porque tiene paquetes asociados");
        }
        destinoRepository.deleteById(id);
        System.out.println("Destino eliminado correctamente.");
    }
}
