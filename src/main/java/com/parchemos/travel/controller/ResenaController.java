package com.parchemos.travel.controller;

import com.parchemos.travel.dto.ResenaDTO;
import com.parchemos.travel.model.Resena;
import com.parchemos.travel.service.ResenaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resenas")
@Tag(name = "Reseñas", description = "CRUD de reseñas de paquetes")
public class ResenaController {

    private final ResenaService resenaService;

    @Autowired
    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    @GetMapping
    @Operation(summary = "Listar reseñas")
    public Page<ResenaDTO> getAllResenas(Pageable pageable) {
        return resenaService.findAll(pageable).map(ResenaDTO::fromEntity);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reseña por ID")
    public ResenaDTO getResenaById(@PathVariable Integer id) {
        return ResenaDTO.fromEntity(resenaService.findById(id));
    }

    @GetMapping("/paquete/{paqueteId}")
    @Operation(summary = "Listar reseñas por paquete")
    public Page<ResenaDTO> getResenasByPaquete(@PathVariable Integer paqueteId, Pageable pageable) {
        return resenaService.findByPaqueteId(paqueteId, pageable).map(ResenaDTO::fromEntity);
    }

    @PostMapping
    @Operation(summary = "Crear reseña")
    public ResenaDTO createResena(@Valid @RequestBody ResenaDTO resenaDTO) {
        Resena resenaGuardada = resenaService.save(
                resenaDTO.toEntity(), resenaDTO.getUsuarioId(), resenaDTO.getPaqueteId());
        return ResenaDTO.fromEntity(resenaGuardada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reseña")
    public ResenaDTO updateResena(@PathVariable Integer id, @Valid @RequestBody ResenaDTO resenaDTO) {
        Resena resenaActualizada = resenaService.update(
                id, resenaDTO.toEntity(), resenaDTO.getUsuarioId(), resenaDTO.getPaqueteId());
        return ResenaDTO.fromEntity(resenaActualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reseña")
    public void deleteResena(@PathVariable Integer id) {
        resenaService.deleteById(id);
    }
}
