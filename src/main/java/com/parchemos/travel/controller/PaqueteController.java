package com.parchemos.travel.controller;

import com.parchemos.travel.dto.PaqueteDTO;
import com.parchemos.travel.dto.PaqueteDetalleDTO;
import com.parchemos.travel.model.Paquete;
import com.parchemos.travel.service.PaqueteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paquetes")
public class PaqueteController {

    private final PaqueteService paqueteService;

    @Autowired
    public PaqueteController(PaqueteService paqueteService) {
        this.paqueteService = paqueteService;
    }

    @GetMapping
    public Page<PaqueteDTO> getAllPaquetes(Pageable pageable) {
        return paqueteService.findAll(pageable).map(PaqueteDTO::fromEntity);
    }

    @GetMapping("/detalle")
    public Page<PaqueteDetalleDTO> getAllPaquetesDetalle(Pageable pageable) {
        return paqueteService.findAllDetalle(pageable);
    }

    @GetMapping("/{id}")
    public PaqueteDTO getPaqueteById(@PathVariable Integer id) {
        Paquete paquete = paqueteService.findById(id);
        return PaqueteDTO.fromEntity(paquete);
    }

    @GetMapping("/destino/{destinoId}")
    public Page<PaqueteDTO> getPaquetesByDestino(@PathVariable Integer destinoId, Pageable pageable) {
        return paqueteService.findByDestinoId(destinoId, pageable).map(PaqueteDTO::fromEntity);
    }

    @PostMapping
    public PaqueteDTO createPaquete(@Valid @RequestBody PaqueteDTO paqueteDTO) {
        Paquete paqueteGuardado = paqueteService.save(paqueteDTO.toEntity(), paqueteDTO.getDestinoId());
        return PaqueteDTO.fromEntity(paqueteGuardado);
    }

    @PutMapping("/{id}")
    public PaqueteDTO updatePaquete(@PathVariable Integer id, @Valid @RequestBody PaqueteDTO paqueteDTO) {
        Paquete paqueteActualizado = paqueteService.update(id, paqueteDTO.toEntity(), paqueteDTO.getDestinoId());
        return PaqueteDTO.fromEntity(paqueteActualizado);
    }

    @DeleteMapping("/{id}")
    public void deletePaquete(@PathVariable Integer id) {
        paqueteService.deleteById(id);
    }
}
