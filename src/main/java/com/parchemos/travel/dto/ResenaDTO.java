package com.parchemos.travel.dto;

import com.parchemos.travel.model.Resena;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ResenaDTO {

    private Integer resenaId;

    @NotNull(message = "El usuarioId es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El paqueteId es obligatorio")
    private Integer paqueteId;

    @NotNull(message = "La calificacion es obligatoria")
    @Min(value = 1, message = "La calificacion debe estar entre 1 y 5")
    @Max(value = 5, message = "La calificacion debe estar entre 1 y 5")
    private Byte calificacion;

    private String comentario;
    private LocalDateTime fecha;

    public ResenaDTO() {
    }

    public Integer getResenaId() {
        return resenaId;
    }

    public void setResenaId(Integer resenaId) {
        this.resenaId = resenaId;
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

    public Byte getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Byte calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public static ResenaDTO fromEntity(Resena resena) {
        ResenaDTO dto = new ResenaDTO();
        dto.setResenaId(resena.getResenaId());
        dto.setCalificacion(resena.getCalificacion());
        dto.setComentario(resena.getComentario());
        dto.setFecha(resena.getFecha());
        if (resena.getUsuario() != null) {
            dto.setUsuarioId(resena.getUsuario().getUsuarioId());
        }
        if (resena.getPaquete() != null) {
            dto.setPaqueteId(resena.getPaquete().getPaqueteId());
        }
        return dto;
    }

    public Resena toEntity() {
        Resena resena = new Resena();
        resena.setResenaId(this.resenaId);
        resena.setCalificacion(this.calificacion);
        resena.setComentario(this.comentario);
        resena.setFecha(this.fecha);
        return resena;
    }
}
