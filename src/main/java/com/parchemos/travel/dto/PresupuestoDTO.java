package com.parchemos.travel.dto;

import com.parchemos.travel.model.Presupuesto;
import com.parchemos.travel.model.TipoViajero;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PresupuestoDTO {

    private Integer presupuestoId;

    @NotNull(message = "El usuarioId es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El monto maximo es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto maximo debe ser mayor a 0")
    private BigDecimal montoMaximo;

    @NotNull(message = "El tipo de viajero es obligatorio")
    private TipoViajero tipoViajero;

    private LocalDateTime fechaCreacion;

    @Size(max = 150, message = "La descripcion no puede superar 150 caracteres")
    private String descripcion;

    public PresupuestoDTO() {
    }

    public Integer getPresupuestoId() {
        return presupuestoId;
    }

    public void setPresupuestoId(Integer presupuestoId) {
        this.presupuestoId = presupuestoId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getMontoMaximo() {
        return montoMaximo;
    }

    public void setMontoMaximo(BigDecimal montoMaximo) {
        this.montoMaximo = montoMaximo;
    }

    public TipoViajero getTipoViajero() {
        return tipoViajero;
    }

    public void setTipoViajero(TipoViajero tipoViajero) {
        this.tipoViajero = tipoViajero;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static PresupuestoDTO fromEntity(Presupuesto presupuesto) {
        PresupuestoDTO dto = new PresupuestoDTO();
        dto.setPresupuestoId(presupuesto.getPresupuestoId());
        dto.setMontoMaximo(presupuesto.getMontoMaximo());
        dto.setTipoViajero(presupuesto.getTipoViajero());
        dto.setFechaCreacion(presupuesto.getFechaCreacion());
        dto.setDescripcion(presupuesto.getDescripcion());
        if (presupuesto.getUsuario() != null) {
            dto.setUsuarioId(presupuesto.getUsuario().getUsuarioId());
        }
        return dto;
    }

    public Presupuesto toEntity() {
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuestoId(this.presupuestoId);
        presupuesto.setMontoMaximo(this.montoMaximo);
        presupuesto.setTipoViajero(this.tipoViajero);
        presupuesto.setFechaCreacion(this.fechaCreacion);
        presupuesto.setDescripcion(this.descripcion);
        return presupuesto;
    }
}
