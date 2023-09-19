package com.example.Matemagicas.controlador;

import com.example.Matemagicas.modelos.Representate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.Matemagicas.repositorio.RepresentateRepository;

/**
 *
 * @author Reyes
 */
@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private RepresentateRepository usuarioRepository;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @PostMapping
    public String registrarUsuario(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String fechadenacimiento,
            @RequestParam String correoelectronico,
            @RequestParam String contrasenia,
            RedirectAttributes redirectAttributes
    ) {
        // Verifica si ya existe un usuario con el mismo correo electrónico
        Representate usuarioExistente = usuarioRepository.findByCorreoelectronico(correoelectronico);
        if (usuarioExistente != null) {
            // Agrega un mensaje de error al modelo
            redirectAttributes.addFlashAttribute("error", "El correo electrónico ya está en uso.");
            return "redirect:/registro/register";
        }

        // Crea una instancia de Usuario con los datos del formulario
        Representate usuario = new Representate();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setFechadenacimiento(fechadenacimiento);
        usuario.setCorreoelectronico(correoelectronico);
        usuario.setContrasenia(contrasenia);

        // Guarda el usuario en la base de datos
        usuarioRepository.save(usuario);

        // Agrega un mensaje de éxito al modelo
        redirectAttributes.addFlashAttribute("success", "Te has registrado correctamente.");

        // Redirige nuevamente a la página de registro
        return "redirect:/registro/register";
    }
}
