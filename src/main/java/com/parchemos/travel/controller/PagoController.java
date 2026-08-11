package com.parchemos.travel.controller;

import com.parchemos.travel.dto.PagoDTO;
import com.parchemos.travel.model.Pago;
import com.parchemos.travel.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "CRUD de pagos asociados a reservas")
public class PagoController {

    private final PagoService pagoService;

    @Autowired
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    @Operation(summary = "Listar pagos")
    public Page<PagoDTO> getAllPagos(Pageable pageable) {
        return pagoService.findAll(pageable).map(PagoDTO::fromEntity);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID")
    public PagoDTO getPagoById(@PathVariable Integer id) {
        return PagoDTO.fromEntity(pagoService.findById(id));
    }

    @GetMapping("/reserva/{reservaId}")
    @Operation(summary = "Listar pagos por reserva")
    public Page<PagoDTO> getPagosByReserva(@PathVariable Integer reservaId, Pageable pageable) {
        return pagoService.findByReservaId(reservaId, pageable).map(PagoDTO::fromEntity);
    }

    @PostMapping
    @Operation(summary = "Crear pago")
    public PagoDTO createPago(@Valid @RequestBody PagoDTO pagoDTO) {
        Pago pagoGuardado = pagoService.save(pagoDTO.toEntity(), pagoDTO.getReservaId());
        return PagoDTO.fromEntity(pagoGuardado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pago")
    public PagoDTO updatePago(@PathVariable Integer id, @Valid @RequestBody PagoDTO pagoDTO) {
        Pago pagoActualizado = pagoService.update(id, pagoDTO.toEntity(), pagoDTO.getReservaId());
        return PagoDTO.fromEntity(pagoActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pago")
    public void deletePago(@PathVariable Integer id) {
        pagoService.deleteById(id);
    }
}
