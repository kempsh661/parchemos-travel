package com.parchemos.travel.controller;

import com.parchemos.travel.dto.ReservaDTO;
import com.parchemos.travel.model.Reserva;
import com.parchemos.travel.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reservas", description = "CRUD de reservas de paquetes")
public class ReservaController {

    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    @Operation(summary = "Listar reservas")
    public Page<ReservaDTO> getAllReservas(Pageable pageable) {
        return reservaService.findAll(pageable).map(ReservaDTO::fromEntity);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reserva por ID")
    public ReservaDTO getReservaById(@PathVariable Integer id) {
        return ReservaDTO.fromEntity(reservaService.findById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar reservas por usuario")
    public Page<ReservaDTO> getReservasByUsuario(@PathVariable Integer usuarioId, Pageable pageable) {
        return reservaService.findByUsuarioId(usuarioId, pageable).map(ReservaDTO::fromEntity);
    }

    @GetMapping("/paquete/{paqueteId}")
    @Operation(summary = "Listar reservas por paquete")
    public Page<ReservaDTO> getReservasByPaquete(@PathVariable Integer paqueteId, Pageable pageable) {
        return reservaService.findByPaqueteId(paqueteId, pageable).map(ReservaDTO::fromEntity);
    }

    @PostMapping
    @Operation(summary = "Crear reserva")
    public ReservaDTO createReserva(@Valid @RequestBody ReservaDTO reservaDTO) {
        Reserva reservaGuardada = reservaService.save(
                reservaDTO.toEntity(),
                reservaDTO.getUsuarioId(),
                reservaDTO.getPaqueteId(),
                reservaDTO.getPresupuestoId());
        return ReservaDTO.fromEntity(reservaGuardada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reserva")
    public ReservaDTO updateReserva(@PathVariable Integer id, @Valid @RequestBody ReservaDTO reservaDTO) {
        Reserva reservaActualizada = reservaService.update(
                id,
                reservaDTO.toEntity(),
                reservaDTO.getUsuarioId(),
                reservaDTO.getPaqueteId(),
                reservaDTO.getPresupuestoId());
        return ReservaDTO.fromEntity(reservaActualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reserva")
    public void deleteReserva(@PathVariable Integer id) {
        reservaService.deleteById(id);
    }
}
