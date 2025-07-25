package com.ceatformacion.libropsi.controller;

import com.ceatformacion.libropsi.modell.Historial;
import com.ceatformacion.libropsi.modell.Libro;
import com.ceatformacion.libropsi.modell.Usuario;

import com.ceatformacion.libropsi.repository.HistorialRepository;
import com.ceatformacion.libropsi.services.HistorialService;
import com.ceatformacion.libropsi.services.LibroService;
import com.ceatformacion.libropsi.services.UsuarioDetails;
import com.ceatformacion.libropsi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/libros")
public class LibroController {
    @Autowired
    private LibroService libroService;

    @Autowired
    private HistorialService historialService;
    @Autowired
    private HistorialRepository historialRepository;

    @GetMapping("/todos")
    public String verLibros(@RequestParam(required = false) String titulo,
                            Model model,
                            @AuthenticationPrincipal UsuarioDetails usuarioDetails) {
        List<Libro> libros;

        if (titulo != null && !titulo.trim().isEmpty()) {
            libros = libroService.buscarPorTitulo(titulo);
        } else {
            libros = libroService.obtenerTodos();
        }

        model.addAttribute("libros", libros);
        model.addAttribute("titulo", titulo); // para mantener el valor en el input

        if (usuarioDetails != null && usuarioDetails.getUsuario() != null) {
            model.addAttribute("nombreUsuario", usuarioDetails.getUsuario().getUsername().toUpperCase());
        }

        return "libros_lista";
    }


    @GetMapping("/nuevo")
    public String nuevoLibro(Model model) {
        model.addAttribute("libro", new Libro());
        return "libro_formulario";
    }

    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute Libro libro) {
        libroService.guardarLibro(libro);
        return "redirect:/libros/todos";
    }

    @GetMapping("/editar/{idLibro}")
    public String editarLibro(@PathVariable int idLibro, Model model) {
        libroService.obtenerPorId(idLibro).ifPresent(libro -> model.addAttribute("libro", libro));
        return "libro_formulario";
    }

    @GetMapping("/eliminar/{idLibro}")
    public String eliminarLibro(@PathVariable int idLibro) {
        libroService.eliminarLibro(idLibro);
        return "redirect:/libros/todos";
    }


}
