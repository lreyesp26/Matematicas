package com.example.Matemagicas.controlador;

import com.example.Matemagicas.dto.EstudianteCalificacionDTO;
import com.example.Matemagicas.modelos.Calificacion;
import com.example.Matemagicas.modelos.Estudiante;
import com.example.Matemagicas.modelos.Representate;
import com.example.Matemagicas.repositorio.CalificacionRepository;
import com.example.Matemagicas.repositorio.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.example.Matemagicas.repositorio.RepresentateRepository;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"nombre", "userRole"})
public class LoginController {

    @Autowired
    private RepresentateRepository representanteRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @GetMapping("/representante")
    public String representantepage(HttpSession session) {
        if ((Long) session.getAttribute("representanteId") != null) {
            return "representante";
        }
        return "redirect:";
    }

    @GetMapping("/perfil")
    public String perfilpage(HttpSession session) {
        if ((Long) session.getAttribute("representanteId") != null) {
            return "perfil";
        }
        return "redirect:";
    }

    @GetMapping("/estudiante")
    public String estudiantepage(HttpSession session) {
        if ((String) session.getAttribute("nombreEstudiante") != null) {
            return "estudiante";
        }
        return "redirect:";
    }

    @GetMapping("/actividades")
    public String actividadespage(HttpSession session) {
        if ((String) session.getAttribute("nombreEstudiante") != null) {
            return "actividades";
        }
        return "redirect:";
    }

    @GetMapping("/calificaciones")
    public String calificacionespage(HttpSession session, Model model) {
        if ((String) session.getAttribute("nombreEstudiante") != null) {
            Long estudianteId = (Long) session.getAttribute("estudianteId");

            if (estudianteId != null) {
                Estudiante estudiante = estudianteRepository.findById(estudianteId).orElse(null);
                if (estudiante != null) {
                    List<Calificacion> calificaciones = estudiante.getCalificaciones();
                    model.addAttribute("calificaciones", calificaciones);
                    return "calificaciones";
                }
            }
        }
        return "redirect:";
    }

    @GetMapping("/pruebasuma")
    public String pruebasuma(HttpSession session) {
        if ((String) session.getAttribute("nombreEstudiante") != null) {
            return "pruebasuma";
        }
        return "redirect:";
    }

    @GetMapping("/pruebaresta")
    public String pruebaresta(HttpSession session) {
        if ((String) session.getAttribute("nombreEstudiante") != null) {
            return "pruebaresta";
        }
        return "redirect:";
    }

    @GetMapping("/pruebadivision")
    public String pruebadivision(HttpSession session) {
        if ((String) session.getAttribute("nombreEstudiante") != null) {
            return "pruebadivision";
        }
        return "redirect:";
    }

    @GetMapping("/pruebamultiplicacion")
    public String pruebamultiplicacion(HttpSession session) {
        if ((String) session.getAttribute("nombreEstudiante") != null) {
            return "pruebamultiplicacion";
        }
        return "redirect:";
    }

    @GetMapping("/index")
    public String index(@RequestParam(value = "logout", required = false) String logout, Model model) {
        if (logout != null) {
            // Agrega un mensaje de éxito de cierre de sesión si lo deseas
            model.addAttribute("logoutMessage", "Sesión cerrada correctamente.");
        }
        return "index"; // Esto carga la página de inicio
    }

    @GetMapping("/estudiantes")
    public String estudiantesPage(Model model, HttpSession session) {
        // Obtén el representante actual desde la sesión
        Long representanteId = (Long) session.getAttribute("representanteId");

        if (representanteId != null) {
            // Busca el representante actual
            Representate representante = representanteRepository.findById(representanteId).orElse(null);

            if (representante != null) {
                List<Estudiante> estudiantes = representante.getEstudiantes();

                List<EstudianteCalificacionDTO> estudiantesConCalificaciones = new ArrayList<>();

                for (Estudiante estudiante : estudiantes) {
                    for (Calificacion calificacion : estudiante.getCalificaciones()) {
                        EstudianteCalificacionDTO dto = new EstudianteCalificacionDTO();
                        dto.setNombre(estudiante.getNombre());
                        dto.setApellido(estudiante.getApellido());
                        dto.setMateria(calificacion.getMateria());
                        dto.setCalificacion(calificacion.getCalificacion());
                        estudiantesConCalificaciones.add(dto);
                    }
                }

                model.addAttribute("estudiantes", estudiantesConCalificaciones);
                return "estudiantes";
            }
        }
        return "redirect:/representante";
    }

    @PostMapping("/login")
    public String login(@RequestParam String correoelectronico, @RequestParam String contrasenia, Model model, HttpSession session) {
        Representate representante = representanteRepository.findByCorreoelectronico(correoelectronico);
        Estudiante estudiante = estudianteRepository.findByCorreoelectronico(correoelectronico);

        if (representante != null && representante.getContrasenia().equals(contrasenia)) {
            // Almacenar el representanteId en la sesión
            session.setAttribute("representanteId", representante.getId());
            session.setAttribute("nombreRepresentante", representante.getNombre());
            session.setAttribute("apellidoRepresentante", representante.getApellido());
            session.setAttribute("correoRepresentante", representante.getCorreoelectronico());

            model.addAttribute("userRole", "representante");
            return "redirect:/representante";
        } else if (estudiante != null && estudiante.getContrasenia().equals(contrasenia)) {
            // Almacenar el estudianteId en la sesión
            session.setAttribute("estudianteId", estudiante.getId());
            session.setAttribute("nombreEstudiante", estudiante.getNombre());
            session.setAttribute("apellidoEstudiante", estudiante.getApellido());
            session.setAttribute("correoEstudiante", estudiante.getCorreoelectronico());
            model.addAttribute("userRole", "estudiante");
            return "redirect:/estudiante";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "index"; // Vuelve a cargar la página de inicio de sesión con un mensaje de error
        }
    }

    @GetMapping("/registrorepresentado")
    public String registrorepresentadopage2(Model model, HttpSession session) {
        Long representanteId = (Long) session.getAttribute("representanteId");

        if (representanteId != null) {
            Representate representante = representanteRepository.findById(representanteId).orElse(null);

            if (representante != null) {
                List<Estudiante> estudiantes = representante.getEstudiantes();
                model.addAttribute("estudiantes", estudiantes);
                return "registrorepresentado";
            }
        }

        return "redirect:";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:";
    }
}
