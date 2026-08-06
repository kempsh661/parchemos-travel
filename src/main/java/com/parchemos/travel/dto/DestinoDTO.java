package com.parchemos.travel.dto;

import com.parchemos.travel.model.Destino;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DestinoDTO {

    private Integer destinoId;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;

    @Size(max = 80, message = "El pais no puede superar 80 caracteres")
    private String pais;

    @Size(max = 80, message = "La ciudad no puede superar 80 caracteres")
    private String ciudad;

    private String descripcion;

    public DestinoDTO() {
    }

    public Integer getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(Integer destinoId) {
        this.destinoId = destinoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static DestinoDTO fromEntity(Destino destino) {
        DestinoDTO dto = new DestinoDTO();
        dto.setDestinoId(destino.getDestinoId());
        dto.setNombre(destino.getNombre());
        dto.setPais(destino.getPais());
        dto.setCiudad(destino.getCiudad());
        dto.setDescripcion(destino.getDescripcion());
        return dto;
    }

    public Destino toEntity() {
        Destino destino = new Destino();
        destino.setDestinoId(this.destinoId);
        destino.setNombre(this.nombre);
        destino.setPais(this.pais);
        destino.setCiudad(this.ciudad);
        destino.setDescripcion(this.descripcion);
        return destino;
    }
}
