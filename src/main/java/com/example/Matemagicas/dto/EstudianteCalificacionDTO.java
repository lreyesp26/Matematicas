package com.example.Matemagicas.dto;

import java.util.Date;

public class EstudianteCalificacionDTO {

    private Long id; // Campo para el identificador único
    private String nombre;
    private String apellido;
    private String materia;
    private Double calificacion;
    private Boolean supervisado;
    private String observaciones;
    private Date fechaSupervision;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public Boolean getSupervisado() {
        return supervisado;
    }

    public void setSupervisado(Boolean supervisado) {
        this.supervisado = supervisado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaSupervision() {
        return fechaSupervision;
    }

    public void setFechaSupervision(Date fechaSupervision) {
        this.fechaSupervision = fechaSupervision;
    }
}
