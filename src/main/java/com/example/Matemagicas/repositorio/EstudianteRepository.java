package com.example.Matemagicas.repositorio;

import com.example.Matemagicas.modelos.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Estudiante findByCorreoelectronico(String correoelectronico);
}
