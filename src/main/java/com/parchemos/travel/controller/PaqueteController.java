package com.parchemos.travel.controller;

import com.parchemos.travel.dto.PaqueteDTO;
import com.parchemos.travel.dto.PaqueteDetalleDTO;
import com.parchemos.travel.model.Paquete;
import com.parchemos.travel.service.PaqueteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paquetes")
@Tag(name = "Paquetes", description = "CRUD de paquetes turísticos")
public class PaqueteController {

    private final PaqueteService paqueteService;

    @Autowired
    public PaqueteController(PaqueteService paqueteService) {
        this.paqueteService = paqueteService;
    }

    @GetMapping
    @Operation(summary = "Listar paquetes")
    public Page<PaqueteDTO> getAllPaquetes(Pageable pageable) {
        return paqueteService.findAll(pageable).map(PaqueteDTO::fromEntity);
    }

    @GetMapping("/detalle")
    @Operation(summary = "Listar paquetes con detalle", description = "Incluye información enriquecida del destino.")
    public Page<PaqueteDetalleDTO> getAllPaquetesDetalle(Pageable pageable) {
        return paqueteService.findAllDetalle(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener paquete por ID")
    public PaqueteDTO getPaqueteById(@PathVariable Integer id) {
        Paquete paquete = paqueteService.findById(id);
        return PaqueteDTO.fromEntity(paquete);
    }

    @GetMapping("/destino/{destinoId}")
    @Operation(summary = "Listar paquetes por destino")
    public Page<PaqueteDTO> getPaquetesByDestino(@PathVariable Integer destinoId, Pageable pageable) {
        return paqueteService.findByDestinoId(destinoId, pageable).map(PaqueteDTO::fromEntity);
    }

    @PostMapping
    @Operation(summary = "Crear paquete")
    public PaqueteDTO createPaquete(@Valid @RequestBody PaqueteDTO paqueteDTO) {
        Paquete paqueteGuardado = paqueteService.save(paqueteDTO.toEntity(), paqueteDTO.getDestinoId());
        return PaqueteDTO.fromEntity(paqueteGuardado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar paquete")
    public PaqueteDTO updatePaquete(@PathVariable Integer id, @Valid @RequestBody PaqueteDTO paqueteDTO) {
        Paquete paqueteActualizado = paqueteService.update(id, paqueteDTO.toEntity(), paqueteDTO.getDestinoId());
        return PaqueteDTO.fromEntity(paqueteActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar paquete")
    public void deletePaquete(@PathVariable Integer id) {
        paqueteService.deleteById(id);
    }
}
