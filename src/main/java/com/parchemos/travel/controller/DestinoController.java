package com.parchemos.travel.controller;

import com.parchemos.travel.dto.DestinoDTO;
import com.parchemos.travel.model.Destino;
import com.parchemos.travel.service.DestinoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/destinos")
@Tag(name = "Destinos", description = "CRUD de destinos turísticos")
public class DestinoController {

    private final DestinoService destinoService;

    @Autowired
    public DestinoController(DestinoService destinoService) {
        this.destinoService = destinoService;
    }

    @GetMapping
    @Operation(summary = "Listar destinos")
    public Page<DestinoDTO> getAllDestinos(Pageable pageable) {
        return destinoService.findAll(pageable).map(DestinoDTO::fromEntity);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener destino por ID")
    public DestinoDTO getDestinoById(@PathVariable Integer id) {
        Destino destino = destinoService.findById(id);
        return DestinoDTO.fromEntity(destino);
    }

    @PostMapping
    @Operation(summary = "Crear destino")
    public DestinoDTO createDestino(@Valid @RequestBody DestinoDTO destinoDTO) {
        Destino destinoGuardado = destinoService.save(destinoDTO.toEntity());
        return DestinoDTO.fromEntity(destinoGuardado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar destino")
    public DestinoDTO updateDestino(@PathVariable Integer id, @Valid @RequestBody DestinoDTO destinoDTO) {
        Destino destinoActualizado = destinoService.update(id, destinoDTO.toEntity());
        return DestinoDTO.fromEntity(destinoActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar destino")
    public void deleteDestino(@PathVariable Integer id) {
        destinoService.deleteById(id);
    }
}
