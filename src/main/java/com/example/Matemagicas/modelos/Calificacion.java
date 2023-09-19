package com.example.Matemagicas.modelos;

import jakarta.persistence.*;
import lombok.Data;

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

    @ManyToOne // Muchas calificaciones pueden pertenecer a un estudiante
    @JoinColumn(name = "estudiante_id") // Esto establece la clave foránea
    private Estudiante estudiante;
}
