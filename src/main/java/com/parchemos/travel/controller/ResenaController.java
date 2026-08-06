package com.parchemos.travel.controller;

import com.parchemos.travel.dto.ResenaDTO;
import com.parchemos.travel.model.Resena;
import com.parchemos.travel.service.ResenaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    private final ResenaService resenaService;

    @Autowired
    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    @GetMapping
    public Page<ResenaDTO> getAllResenas(Pageable pageable) {
        return resenaService.findAll(pageable).map(ResenaDTO::fromEntity);
    }

    @GetMapping("/{id}")
    public ResenaDTO getResenaById(@PathVariable Integer id) {
        return ResenaDTO.fromEntity(resenaService.findById(id));
    }

    @GetMapping("/paquete/{paqueteId}")
    public Page<ResenaDTO> getResenasByPaquete(@PathVariable Integer paqueteId, Pageable pageable) {
        return resenaService.findByPaqueteId(paqueteId, pageable).map(ResenaDTO::fromEntity);
    }

    @PostMapping
    public ResenaDTO createResena(@Valid @RequestBody ResenaDTO resenaDTO) {
        Resena resenaGuardada = resenaService.save(
                resenaDTO.toEntity(), resenaDTO.getUsuarioId(), resenaDTO.getPaqueteId());
        return ResenaDTO.fromEntity(resenaGuardada);
    }

    @PutMapping("/{id}")
    public ResenaDTO updateResena(@PathVariable Integer id, @Valid @RequestBody ResenaDTO resenaDTO) {
        Resena resenaActualizada = resenaService.update(
                id, resenaDTO.toEntity(), resenaDTO.getUsuarioId(), resenaDTO.getPaqueteId());
        return ResenaDTO.fromEntity(resenaActualizada);
    }

    @DeleteMapping("/{id}")
    public void deleteResena(@PathVariable Integer id) {
        resenaService.deleteById(id);
    }
}
