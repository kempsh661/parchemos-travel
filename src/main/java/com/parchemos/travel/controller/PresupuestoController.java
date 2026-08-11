package com.parchemos.travel.controller;

import com.parchemos.travel.dto.PresupuestoDTO;
import com.parchemos.travel.model.Presupuesto;
import com.parchemos.travel.service.PresupuestoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/presupuestos")
@Tag(name = "Presupuestos", description = "CRUD de presupuestos de viaje por usuario")
public class PresupuestoController {

    private final PresupuestoService presupuestoService;

    @Autowired
    public PresupuestoController(PresupuestoService presupuestoService) {
        this.presupuestoService = presupuestoService;
    }

    @GetMapping
    @Operation(summary = "Listar presupuestos")
    public Page<PresupuestoDTO> getAllPresupuestos(Pageable pageable) {
        return presupuestoService.findAll(pageable).map(PresupuestoDTO::fromEntity);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener presupuesto por ID")
    public PresupuestoDTO getPresupuestoById(@PathVariable Integer id) {
        return PresupuestoDTO.fromEntity(presupuestoService.findById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar presupuestos por usuario")
    public Page<PresupuestoDTO> getPresupuestosByUsuario(@PathVariable Integer usuarioId, Pageable pageable) {
        return presupuestoService.findByUsuarioId(usuarioId, pageable).map(PresupuestoDTO::fromEntity);
    }

    @PostMapping
    @Operation(summary = "Crear presupuesto")
    public PresupuestoDTO createPresupuesto(@Valid @RequestBody PresupuestoDTO presupuestoDTO) {
        Presupuesto presupuestoGuardado = presupuestoService.save(
                presupuestoDTO.toEntity(), presupuestoDTO.getUsuarioId());
        return PresupuestoDTO.fromEntity(presupuestoGuardado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar presupuesto")
    public PresupuestoDTO updatePresupuesto(@PathVariable Integer id, @Valid @RequestBody PresupuestoDTO presupuestoDTO) {
        Presupuesto presupuestoActualizado = presupuestoService.update(
                id, presupuestoDTO.toEntity(), presupuestoDTO.getUsuarioId());
        return PresupuestoDTO.fromEntity(presupuestoActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar presupuesto")
    public void deletePresupuesto(@PathVariable Integer id) {
        presupuestoService.deleteById(id);
    }
}
