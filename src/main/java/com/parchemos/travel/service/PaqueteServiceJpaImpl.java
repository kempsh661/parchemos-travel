package com.parchemos.travel.service;

import com.parchemos.travel.exception.BadRequestException;
import com.parchemos.travel.exception.ResourceNotFoundException;
import com.parchemos.travel.dto.PaqueteDetalleDTO;
import com.parchemos.travel.model.Destino;
import com.parchemos.travel.model.Paquete;
import com.parchemos.travel.repository.DestinoRepository;
import com.parchemos.travel.repository.PaqueteRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PaqueteServiceJpaImpl implements PaqueteService {

    private final PaqueteRepository paqueteRepository;
    private final DestinoRepository destinoRepository;
    private final EntityManager entityManager;

    @Autowired
    public PaqueteServiceJpaImpl(PaqueteRepository paqueteRepository,
                                 DestinoRepository destinoRepository,
                                 EntityManager entityManager) {
        this.paqueteRepository = paqueteRepository;
        this.destinoRepository = destinoRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paquete> findAll(Pageable pageable) {
        System.out.println("Buscando paquetes paginados...");
        return paqueteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaqueteDetalleDTO> findAllDetalle(Pageable pageable) {
        System.out.println("Buscando paquetes con detalle de destino...");
        return paqueteRepository.findAllWithDestino(pageable)
                .map(PaqueteDetalleDTO::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Paquete findById(Integer id) {
        System.out.println("Buscando paquete con id: " + id);
        return paqueteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paquete no encontrado con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paquete> findByDestinoId(Integer destinoId, Pageable pageable) {
        System.out.println("Buscando paquetes del destino id: " + destinoId);
        if (!destinoRepository.existsById(destinoId)) {
            throw new ResourceNotFoundException("Destino no encontrado con id: " + destinoId);
        }
        return paqueteRepository.findByDestinoDestinoId(destinoId, pageable);
    }

    @Override
    public Paquete save(Paquete paquete, Integer destinoId) {
        System.out.println("Guardando paquete: " + paquete.getNombre());
        validarPaquete(paquete, destinoId);
        Destino destino = destinoRepository.findById(destinoId)
                .orElseThrow(() -> new ResourceNotFoundException("Destino no encontrado con id: " + destinoId));
        paquete.setDestino(destino);
        return paqueteRepository.save(paquete);
    }

    @Override
    public Paquete update(Integer id, Paquete paquete, Integer destinoId) {
        System.out.println("Actualizando paquete con id: " + id);
        if (!paqueteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paquete no encontrado para actualizar con id: " + id);
        }
        validarPaquete(paquete, destinoId);
        Destino destino = destinoRepository.findById(destinoId)
                .orElseThrow(() -> new ResourceNotFoundException("Destino no encontrado con id: " + destinoId));
        paquete.setPaqueteId(id);
        paquete.setDestino(destino);
        return entityManager.merge(paquete);
    }

    @Override
    public void deleteById(Integer id) {
        System.out.println("Eliminando paquete con id: " + id);
        if (!paqueteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paquete no encontrado para eliminar con id: " + id);
        }
        paqueteRepository.deleteById(id);
        System.out.println("Paquete eliminado correctamente.");
    }

    private void validarPaquete(Paquete paquete, Integer destinoId) {
        if (paquete.getNombre() == null || paquete.getNombre().isBlank()) {
            throw new BadRequestException("El nombre del paquete es obligatorio");
        }
        if (destinoId == null) {
            throw new BadRequestException("El destino_id es obligatorio");
        }
        if (paquete.getPrecio() == null || paquete.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("El precio debe ser mayor a 0");
        }
        if (paquete.getDuracionDias() == null || paquete.getDuracionDias() <= 0) {
            throw new BadRequestException("La duracion en dias debe ser mayor a 0");
        }
        if (paquete.getCupoMaximo() == null || paquete.getCupoMaximo() <= 0) {
            throw new BadRequestException("El cupo maximo debe ser mayor a 0");
        }
    }
}
