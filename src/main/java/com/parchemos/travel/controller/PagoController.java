package com.parchemos.travel.controller;

import com.parchemos.travel.dto.PagoDTO;
import com.parchemos.travel.model.Pago;
import com.parchemos.travel.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    @Autowired
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public Page<PagoDTO> getAllPagos(Pageable pageable) {
        return pagoService.findAll(pageable).map(PagoDTO::fromEntity);
    }

    @GetMapping("/{id}")
    public PagoDTO getPagoById(@PathVariable Integer id) {
        return PagoDTO.fromEntity(pagoService.findById(id));
    }

    @GetMapping("/reserva/{reservaId}")
    public Page<PagoDTO> getPagosByReserva(@PathVariable Integer reservaId, Pageable pageable) {
        return pagoService.findByReservaId(reservaId, pageable).map(PagoDTO::fromEntity);
    }

    @PostMapping
    public PagoDTO createPago(@Valid @RequestBody PagoDTO pagoDTO) {
        Pago pagoGuardado = pagoService.save(pagoDTO.toEntity(), pagoDTO.getReservaId());
        return PagoDTO.fromEntity(pagoGuardado);
    }

    @PutMapping("/{id}")
    public PagoDTO updatePago(@PathVariable Integer id, @Valid @RequestBody PagoDTO pagoDTO) {
        Pago pagoActualizado = pagoService.update(id, pagoDTO.toEntity(), pagoDTO.getReservaId());
        return PagoDTO.fromEntity(pagoActualizado);
    }

    @DeleteMapping("/{id}")
    public void deletePago(@PathVariable Integer id) {
        pagoService.deleteById(id);
    }
}
