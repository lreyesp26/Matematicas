/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Matemagicas.controlador;

import com.example.Matemagicas.modelos.Calificacion;
import com.example.Matemagicas.modelos.Estudiante;
import com.example.Matemagicas.modelos.Representate;
import com.example.Matemagicas.repositorio.CalificacionRepository;
import com.example.Matemagicas.repositorio.EstudianteRepository;
import com.example.Matemagicas.repositorio.RepresentateRepository;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalificacionController {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private RepresentateRepository representateRepository;

    @GetMapping("/enviarpuntuacion")
    public String enviarPuntuacion(
            @RequestParam("materia") String materia,
            @RequestParam("calificacion") Double calificacion,
            @RequestParam(value = "supervisado", required = false) Boolean supervisado,
            @RequestParam(value = "observaciones", required = false) String observaciones,
            HttpSession session
    ) {
        // Obtén el estudianteId desde la sesión
        Long estudianteId = (Long) session.getAttribute("estudianteId");

        if (estudianteId != null) {
            // Obtén el estudiante correspondiente a partir del estudianteId
            Estudiante estudiante = estudianteRepository.findById(estudianteId).orElse(null);

            if (estudiante != null) {
                // Obtén el representante del estudiante
                Representate representante = estudiante.getRepresentante();

                // Crea una nueva instancia de Calificacion
                Calificacion nuevaCalificacion = new Calificacion();
                nuevaCalificacion.setMateria(materia);
                nuevaCalificacion.setCalificacion(calificacion);
                nuevaCalificacion.setSupervisado(supervisado);
                nuevaCalificacion.setObservaciones(observaciones);

                // Agrega la fecha de supervisión actual
                //Date fechaSupervision = new Date(); // Esto obtiene la fecha y hora actual
                //nuevaCalificacion.setFechaSupervision(fechaSupervision);
                // Asigna el estudiante a la calificación
                nuevaCalificacion.setEstudiante(estudiante);

                // Asigna el representante supervisor a la calificación
                nuevaCalificacion.setRepresentanteSupervisor(representante);

                // Guarda la calificación en la base de datos
                calificacionRepository.save(nuevaCalificacion);

                // Puedes redirigir al usuario a una página de confirmación o a donde desees
                return "redirect:/actividades";
            }
        }
        return "redirect:/"; // Redirige a la página de inicio o maneja el error de alguna otra manera
    }

    @GetMapping("/actualizarFechaSupervision")
    public String actualizarFechaSupervision(@RequestParam Long calificacionId) {
        Optional<Calificacion> calificacionOptional = calificacionRepository.findById(calificacionId);

        if (calificacionOptional.isPresent()) {
            Calificacion calificacion = calificacionOptional.get();
            calificacion.setFechaSupervision(new Date()); // Establecer la fecha de supervisión actual
            calificacionRepository.save(calificacion);

            // Redirige de vuelta a la página de lista de estudiantes
            return "redirect:/lista-estudiantes";
        } else {
            // Manejar el caso en que no se encuentra la calificación
            return "redirect:/"; // Redirige a la página de inicio o maneja el error de alguna otra manera
        }
    }

    @GetMapping("/verCalificaciones/{id}")
    public String verCalificaciones(@PathVariable Long id, Model model) {
        // Buscar el estudiante por ID
        Optional<Estudiante> estudianteOptional = estudianteRepository.findById(id);

        if (estudianteOptional.isPresent()) {
            Estudiante estudiante = estudianteOptional.get();

            // Obtener las calificaciones del estudiante
            List<Calificacion> calificaciones = estudiante.getCalificaciones();

            model.addAttribute("estudiante", estudiante);
            model.addAttribute("calificaciones", calificaciones);

            return "verCalificaciones"; // Asegúrate de crear la vista "verCalificaciones.html"
        } else {
            // Manejar el caso en que el estudiante no se encuentra
            return "redirect:/estudiantes"; // Redirige a la lista de estudiantes u otra página
        }
    }

    @PostMapping("/checkCalificacion")
    public String checkCalificacion(@RequestParam Long calificacionId, HttpSession session) {
        // Obtén el representante actual desde la sesión
        Long representanteId = (Long) session.getAttribute("representanteId");

        if (representanteId != null) {
            // Busca la calificación por ID
            Optional<Calificacion> calificacionOptional = calificacionRepository.findById(calificacionId);

            if (calificacionOptional.isPresent()) {
                Calificacion calificacion = calificacionOptional.get();

                // Verifica si la calificación ya ha sido supervisada
                if (!calificacion.getSupervisado()) {
                    // Cambia el valor de supervisado a true
                    calificacion.setSupervisado(true);

                    // Actualiza la fecha y hora de supervisión
                    calificacion.setFechaSupervision(new Date());

                    // Asigna el representante que supervisó la calificación
                    Representate representanteSupervisor = representateRepository.findById(representanteId).orElse(null);
                    calificacion.setRepresentanteSupervisor(representanteSupervisor);

                    // Guarda la calificación actualizada en la base de datos
                    calificacionRepository.save(calificacion);
                } else {
                    // La calificación ya ha sido supervisada, maneja el error o redirige
                    // a alguna página de error o información relevante.
                }
            }
        }

        // Redirige de vuelta a la página de lista de estudiantes o donde desees
        return "redirect:/estudiantes";
    }
}
