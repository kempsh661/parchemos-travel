package com.parchemos.travel.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "presupuestos")
public class Presupuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "presupuesto_id")
    private Integer presupuestoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "monto_maximo", precision = 10, scale = 2)
    private BigDecimal montoMaximo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_viajero")
    private TipoViajero tipoViajero;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(length = 150)
    private String descripcion;

    public Presupuesto() {
    }

    public Integer getPresupuestoId() {
        return presupuestoId;
    }

    public void setPresupuestoId(Integer presupuestoId) {
        this.presupuestoId = presupuestoId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
}
