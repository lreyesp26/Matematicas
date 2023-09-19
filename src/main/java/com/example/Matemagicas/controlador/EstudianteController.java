package com.example.Matemagicas.controlador;

import com.example.Matemagicas.modelos.Calificacion;
import com.example.Matemagicas.modelos.Estudiante;
import com.example.Matemagicas.modelos.Representate;
import com.example.Matemagicas.repositorio.EstudianteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @PostMapping("/guardar-estudiante")
    public String guardarEstudiante(@RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String fechadenacimiento,
            @RequestParam String correoelectronico,
            @RequestParam String contrasenia,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        Estudiante usuarioExistente = estudianteRepository.findByCorreoelectronico(correoelectronico);
        if (usuarioExistente != null) {
            // Agrega un mensaje de error al modelo
            redirectAttributes.addFlashAttribute("error", "El correo electrónico ya está en uso.");
            return "redirect:/registrorepresentado";
        }
        
        // Obtener el representanteId de la sesión
        Long representanteId = (Long) session.getAttribute("representanteId");

        // Crear un nuevo estudiante
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(nombre);
        estudiante.setApellido(apellido);
        estudiante.setFechadenacimiento(fechadenacimiento);
        estudiante.setCorreoelectronico(correoelectronico);
        estudiante.setContrasenia(contrasenia);

        // Asociar el estudiante con el representante
        Representate representante = new Representate();
        representante.setId(representanteId); // Aquí asignamos el representanteId obtenido de la sesión
        estudiante.setRepresentante(representante);

        // Guardar el estudiante en la base de datos
        estudiante = estudianteRepository.save(estudiante);
        
        // Crear una instancia de Calificacion y establecer la relación con el estudiante
        Calificacion calificacion = new Calificacion();
        calificacion.setEstudiante(estudiante);
        // Otras configuraciones de la calificación, como materia, nivel y calificación
        
        redirectAttributes.addFlashAttribute("success", "Estudiante registrado con éxito.");
        
        // Puedes redirigir a una página de éxito o realizar otras acciones
        return "redirect:/representante"; // Crea esta página si no existe
    }
}
