/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Matemagicas.controlador;

import com.example.Matemagicas.modelos.Calificacion;
import com.example.Matemagicas.modelos.Estudiante;
import com.example.Matemagicas.repositorio.CalificacionRepository;
import com.example.Matemagicas.repositorio.EstudianteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Luis
 */
@Controller
public class CalificacionController {

    @Autowired
    private CalificacionRepository calificacionRepository;
    
    @Autowired
    private EstudianteRepository estudianteRepository;

    @GetMapping("/enviarpuntuacion")
    public String enviarPuntuacion(@RequestParam("materia") String materia, @RequestParam("calificacion") Double calificacion, HttpSession session) {
        // Obtén el estudianteId desde la sesión
        Long estudianteId = (Long) session.getAttribute("estudianteId");

        if (estudianteId != null) {
            // Obtén el estudiante correspondiente a partir del estudianteId
            Estudiante estudiante = estudianteRepository.findById(estudianteId).orElse(null);

            if (estudiante != null) {
                // Crea una nueva instancia de Calificacion
                Calificacion nuevaCalificacion = new Calificacion();
                nuevaCalificacion.setMateria(materia);
                nuevaCalificacion.setCalificacion(calificacion);

                // Asigna el estudiante a la calificación
                nuevaCalificacion.setEstudiante(estudiante);

                // Guarda la calificación en la base de datos
                calificacionRepository.save(nuevaCalificacion);

                // Puedes redirigir al usuario a una página de confirmación o a donde desees
                return "redirect:/actividades";
            }
        }
        return "redirect:/"; // Redirige a la página de inicio o maneja el error de alguna otra manera
    }
}
