package com.example.Matemagicas.modelos;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "Representate")
@Data
public class Representate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String fechadenacimiento;

    @Column
    private String correoelectronico;

    @Column
    private String contrasenia;

    // Define la relación con la clase Estudiante
    @OneToMany(mappedBy = "representante")
    private List<Estudiante> estudiantes;

    @OneToMany(mappedBy = "representanteSupervisor")
    private List<Calificacion> calificacionesSupervisadas;

}
