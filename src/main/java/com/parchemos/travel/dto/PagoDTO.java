package com.parchemos.travel.dto;

import com.parchemos.travel.model.EstadoPago;
import com.parchemos.travel.model.MetodoPago;
import com.parchemos.travel.model.Pago;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagoDTO {

    private Integer pagoId;

    @NotNull(message = "El reservaId es obligatorio")
    private Integer reservaId;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    private LocalDateTime fechaPago;

    @NotNull(message = "El metodo de pago es obligatorio")
    private MetodoPago metodoPago;

    private EstadoPago estado;

    public PagoDTO() {
    }

    public Integer getPagoId() {
        return pagoId;
    }

    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
    }

    public Integer getReservaId() {
        return reservaId;
    }

    public void setReservaId(Integer reservaId) {
        this.reservaId = reservaId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public static PagoDTO fromEntity(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setPagoId(pago.getPagoId());
        dto.setMonto(pago.getMonto());
        dto.setFechaPago(pago.getFechaPago());
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setEstado(pago.getEstado());
        if (pago.getReserva() != null) {
            dto.setReservaId(pago.getReserva().getReservaId());
        }
        return dto;
    }

    public Pago toEntity() {
        Pago pago = new Pago();
        pago.setPagoId(this.pagoId);
        pago.setMonto(this.monto);
        pago.setFechaPago(this.fechaPago);
        pago.setMetodoPago(this.metodoPago);
        pago.setEstado(this.estado);
        return pago;
    }
}
