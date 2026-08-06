package com.parchemos.travel.dto;

import com.parchemos.travel.model.Destino;
import com.parchemos.travel.model.Paquete;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PaqueteDetalleDTO {

    private Integer paqueteId;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer duracionDias;
    private String duracionTexto;
    private Integer cupoMaximo;
    private String imagenUrl;
    private String categoria;
    private String tipoViaje;
    private List<String> servicios;

    private Integer destinoId;
    private String destinoNombre;
    private String destinoCiudad;
    private String destinoPais;
    private String destinoDescripcion;
    private String region;

    public PaqueteDetalleDTO() {
    }

    public static PaqueteDetalleDTO fromEntity(Paquete paquete) {
        PaqueteDetalleDTO dto = new PaqueteDetalleDTO();
        dto.setPaqueteId(paquete.getPaqueteId());
        dto.setNombre(paquete.getNombre());
        dto.setDescripcion(paquete.getDescripcion());
        dto.setPrecio(paquete.getPrecio());
        dto.setDuracionDias(paquete.getDuracionDias());
        dto.setDuracionTexto(paquete.getDuracionTexto());
        dto.setCupoMaximo(paquete.getCupoMaximo());
        dto.setImagenUrl(paquete.getImagenUrl());
        dto.setCategoria(paquete.getCategoria());
        dto.setTipoViaje(paquete.getTipoViaje());
        dto.setServicios(parseServicios(paquete.getServicios()));

        Destino destino = paquete.getDestino();
        if (destino != null) {
            dto.setDestinoId(destino.getDestinoId());
            dto.setDestinoNombre(destino.getNombre());
            dto.setDestinoCiudad(destino.getCiudad());
            dto.setDestinoPais(destino.getPais());
            dto.setDestinoDescripcion(destino.getDescripcion());
            dto.setRegion(buildRegion(destino.getCiudad(), destino.getPais()));
        }

        if (dto.getDuracionTexto() == null && paquete.getDuracionDias() != null) {
            dto.setDuracionTexto(paquete.getDuracionDias() + " días");
        }

        return dto;
    }

    private static String buildRegion(String ciudad, String pais) {
        if (ciudad != null && !ciudad.isBlank() && pais != null && !pais.isBlank()) {
            return ciudad.trim() + ", " + pais.trim();
        }
        if (ciudad != null && !ciudad.isBlank()) {
            return ciudad.trim();
        }
        if (pais != null && !pais.isBlank()) {
            return pais.trim();
        }
        return "";
    }

    private static List<String> parseServicios(String servicios) {
        if (servicios == null || servicios.isBlank()) {
            return List.of("Hotel", "Transporte", "Alimentación");
        }
        return Arrays.stream(servicios.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
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

    public String getDuracionTexto() {
        return duracionTexto;
    }

    public void setDuracionTexto(String duracionTexto) {
        this.duracionTexto = duracionTexto;
    }

    public Integer getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(Integer cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
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

    public List<String> getServicios() {
        return servicios != null ? servicios : Collections.emptyList();
    }

    public void setServicios(List<String> servicios) {
        this.servicios = servicios;
    }

    public Integer getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(Integer destinoId) {
        this.destinoId = destinoId;
    }

    public String getDestinoNombre() {
        return destinoNombre;
    }

    public void setDestinoNombre(String destinoNombre) {
        this.destinoNombre = destinoNombre;
    }

    public String getDestinoCiudad() {
        return destinoCiudad;
    }

    public void setDestinoCiudad(String destinoCiudad) {
        this.destinoCiudad = destinoCiudad;
    }

    public String getDestinoPais() {
        return destinoPais;
    }

    public void setDestinoPais(String destinoPais) {
        this.destinoPais = destinoPais;
    }

    public String getDestinoDescripcion() {
        return destinoDescripcion;
    }

    public void setDestinoDescripcion(String destinoDescripcion) {
        this.destinoDescripcion = destinoDescripcion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
