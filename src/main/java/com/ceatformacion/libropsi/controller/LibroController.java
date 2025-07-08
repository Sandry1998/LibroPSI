package com.ceatformacion.libropsi.controller;

import com.ceatformacion.libropsi.modell.Historial;
import com.ceatformacion.libropsi.modell.Libro;
import com.ceatformacion.libropsi.modell.Usuario;
import com.ceatformacion.libropsi.repository.LibroRepository;
import com.ceatformacion.libropsi.services.HistorialService;
import com.ceatformacion.libropsi.services.LibroService;
import com.ceatformacion.libropsi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private HistorialService historialService;

    @Autowired
    private UsuarioService usuarioService;

    // Mostrar todos los libros (para usuarios y admins)
    @GetMapping("/todos")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String listarLibros(Model model) {
        List<Libro> libros = libroService.obtenerTodos();
        model.addAttribute("libros", libros);
        return "libros_lista";  // Vista para mostrar todos los libros
    }

    // Mostrar formulario para agregar libro (solo admin)
    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String mostrarFormularioNuevoLibro(Model model) {
        model.addAttribute("libro", new Libro());
        return "libro_formulario";  // Formulario para crear libro
    }

    // Guardar libro (nuevo o editado) - solo admin
    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public String guardarLibro(@ModelAttribute Libro libro) {
        libroService.guardarLibro(libro);
        return "redirect:/libros/todos";
    }

    // Mostrar formulario para editar libro - solo admin
    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        Libro libro = libroService.obtenerPorId(id);
        if (libro == null) {
            return "redirect:/libros/todos";
        }
        model.addAttribute("libro", libro);
        return "libro_formulario";
    }

    // Eliminar libro - solo admin
    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminarLibro(@PathVariable int id) {
        libroService.eliminarLibro(id);
        return "redirect:/libros/todos";
    }

    // Reservar libro (solo usuarios)
    @PostMapping("/reservar/{id}")
    @PreAuthorize("hasRole('USER')")
    public String reservarLibro(@PathVariable int id, Principal principal) {
        Libro libro = libroService.obtenerPorId(id);
        if (libro == null) {
            return "redirect:/libros/todos";
        }
        Usuario usuario = usuarioService.findByUsername(principal.getName());

        // Verificar si ya existe reserva activa (opcional)
        boolean yaReservado = historialService.existeReservaActiva(usuario.getId_usuario(), id);
        if (yaReservado) {
            // Puedes agregar un mensaje de error si quieres mostrar en la vista
            return "redirect:/libros/todos?error=Ya tienes este libro reservado";
        }

        Historial historial = new Historial();
        historial.setLibro(libro);
        historial.setUsuario(usuario);
        historial.setFechaRecogida(LocalDate.now());
        historial.setEstado("RESERVADO");
        historial.setObservaciones("Reserva realizada desde la web");

        historialService.guardarHistorial(historial);

        return "redirect:/libros/todos?success=Libro reservado";
    }
}