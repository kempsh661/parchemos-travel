package com.parchemos.travel.dto;

import com.parchemos.travel.model.EstadoReserva;
import com.parchemos.travel.model.Reserva;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ReservaDTO {

    private Integer reservaId;

    @NotNull(message = "El usuarioId es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El paqueteId es obligatorio")
    private Integer paqueteId;

    private Integer presupuestoId;
    private LocalDateTime fechaReserva;

    @NotNull(message = "El numero de personas es obligatorio")
    @Min(value = 1, message = "El numero de personas debe ser mayor a 0")
    private Integer numPersonas;

    private EstadoReserva estado;

    public ReservaDTO() {
    }

    public Integer getReservaId() {
        return reservaId;
    }

    public void setReservaId(Integer reservaId) {
        this.reservaId = reservaId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getPaqueteId() {
        return paqueteId;
    }

    public void setPaqueteId(Integer paqueteId) {
        this.paqueteId = paqueteId;
    }

    public Integer getPresupuestoId() {
        return presupuestoId;
    }

    public void setPresupuestoId(Integer presupuestoId) {
        this.presupuestoId = presupuestoId;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Integer getNumPersonas() {
        return numPersonas;
    }

    public void setNumPersonas(Integer numPersonas) {
        this.numPersonas = numPersonas;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public static ReservaDTO fromEntity(Reserva reserva) {
        ReservaDTO dto = new ReservaDTO();
        dto.setReservaId(reserva.getReservaId());
        dto.setFechaReserva(reserva.getFechaReserva());
        dto.setNumPersonas(reserva.getNumPersonas());
        dto.setEstado(reserva.getEstado());
        if (reserva.getUsuario() != null) {
            dto.setUsuarioId(reserva.getUsuario().getUsuarioId());
        }
        if (reserva.getPaquete() != null) {
            dto.setPaqueteId(reserva.getPaquete().getPaqueteId());
        }
        if (reserva.getPresupuesto() != null) {
            dto.setPresupuestoId(reserva.getPresupuesto().getPresupuestoId());
        }
        return dto;
    }

    public Reserva toEntity() {
        Reserva reserva = new Reserva();
        reserva.setReservaId(this.reservaId);
        reserva.setFechaReserva(this.fechaReserva);
        reserva.setNumPersonas(this.numPersonas);
        reserva.setEstado(this.estado);
        return reserva;
    }
}
