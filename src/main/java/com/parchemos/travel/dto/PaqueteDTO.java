package com.parchemos.travel.dto;

import com.parchemos.travel.model.Paquete;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class PaqueteDTO {

    private Integer paqueteId;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar 120 caracteres")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @NotNull(message = "La duracion en dias es obligatoria")
    @Min(value = 1, message = "La duracion en dias debe ser mayor a 0")
    private Integer duracionDias;

    @NotNull(message = "El cupo maximo es obligatorio")
    @Min(value = 1, message = "El cupo maximo debe ser mayor a 0")
    private Integer cupoMaximo;

    @NotNull(message = "El destinoId es obligatorio")
    private Integer destinoId;

    @Size(max = 500, message = "La URL de imagen no puede superar 500 caracteres")
    private String imagenUrl;

    @Size(max = 50, message = "La categoria no puede superar 50 caracteres")
    private String categoria;

    @Size(max = 50, message = "El tipo de viaje no puede superar 50 caracteres")
    private String tipoViaje;

    @Size(max = 80, message = "La duracion en texto no puede superar 80 caracteres")
    private String duracionTexto;

    private String servicios;

    public PaqueteDTO() {
    }

    public Integer getPaqueteId() {
        return paqueteId;
    }

    public void setPaqueteId(Integer paqueteId) {
        this.paqueteId = paqueteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(Integer duracionDias) {
        this.duracionDias = duracionDias;
    }

    public Integer getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(Integer cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public Integer getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(Integer destinoId) {
        this.destinoId = destinoId;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipoViaje() {
        return tipoViaje;
    }

    public void setTipoViaje(String tipoViaje) {
        this.tipoViaje = tipoViaje;
    }

    public String getDuracionTexto() {
        return duracionTexto;
    }

    public void setDuracionTexto(String duracionTexto) {
        this.duracionTexto = duracionTexto;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    public static PaqueteDTO fromEntity(Paquete paquete) {
        PaqueteDTO dto = new PaqueteDTO();
        dto.setPaqueteId(paquete.getPaqueteId());
        dto.setNombre(paquete.getNombre());
        dto.setDescripcion(paquete.getDescripcion());
        dto.setPrecio(paquete.getPrecio());
        dto.setDuracionDias(paquete.getDuracionDias());
        dto.setCupoMaximo(paquete.getCupoMaximo());
        dto.setImagenUrl(paquete.getImagenUrl());
        dto.setCategoria(paquete.getCategoria());
        dto.setTipoViaje(paquete.getTipoViaje());
        dto.setDuracionTexto(paquete.getDuracionTexto());
        dto.setServicios(paquete.getServicios());
        if (paquete.getDestino() != null) {
            dto.setDestinoId(paquete.getDestino().getDestinoId());
        }
        return dto;
    }

    public Paquete toEntity() {
        Paquete paquete = new Paquete();
        paquete.setPaqueteId(this.paqueteId);
        paquete.setNombre(this.nombre);
        paquete.setDescripcion(this.descripcion);
        paquete.setPrecio(this.precio);
        paquete.setDuracionDias(this.duracionDias);
        paquete.setCupoMaximo(this.cupoMaximo);
        paquete.setImagenUrl(this.imagenUrl);
        paquete.setCategoria(this.categoria);
        paquete.setTipoViaje(this.tipoViaje);
        paquete.setDuracionTexto(this.duracionTexto);
        paquete.setServicios(this.servicios);
        return paquete;
    }
}
