package com.example.Matemagicas.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date; // Importar la clase Date

@Entity
@Table(name = "Calificacion")
@Data
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String materia;

    @Column
    private Double calificacion;

    @Column(name = "supervisado")
    private Boolean supervisado;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "fecha_supervision")
    private Date fechaSupervision;

    @ManyToOne // Muchas calificaciones pueden pertenecer a un estudiante
    @JoinColumn(name = "estudiante_id") // Esto establece la clave foránea
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "representante_supervisor_id")
    private Representate representanteSupervisor;

}
