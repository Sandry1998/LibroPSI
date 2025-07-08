package com.ceatformacion.libropsi.controller;


import com.ceatformacion.libropsi.modell.Historial;
import com.ceatformacion.libropsi.modell.Libro;
import com.ceatformacion.libropsi.modell.Usuario;

import com.ceatformacion.libropsi.services.HistorialService;
import com.ceatformacion.libropsi.services.LibroService;
import com.ceatformacion.libropsi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/historial")
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LibroService libroService;

    // Vista admin: ver todos los historiales
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String verHistorialAdmin(Model model) {
        model.addAttribute("historiales", historialService.obtenerTodos());
        return "historial_admin";
    }

    // Vista usuario: ver historiales propios
    @GetMapping("/usuario")
    @PreAuthorize("hasRole('USER')")
    public String verHistorialUsuario(Model model, Principal principal) {
        Usuario usuario = usuarioService.findByUsername(principal.getName());
        model.addAttribute("historiales", historialService.obtenerPorUsuario(usuario.getId_usuario()));
        return "historial_usuario";
    }

    // Admin elimina historial
    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminarHistorial(@PathVariable int id) {
        historialService.eliminarHistorial(id);
        return "redirect:/historial/admin";
    }

    // Admin modifica estado de historial (reservado, cancelado, entregado)
    @PostMapping("/modificar-estado/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String modificarEstado(@PathVariable int id, @RequestParam String estado) {
        Historial historial = historialService.obtenerPorId(id);
        if(historial != null) {
            historial.setEstado(estado);
            historialService.guardarHistorial(historial);
        }
        return "redirect:/historial/admin";
    }

    // Admin agrega nuevo libro a la lista general (opcional)
    @GetMapping("/admin/agregar-libro")
    @PreAuthorize("hasRole('ADMIN')")
    public String mostrarFormularioLibro(Model model) {
        model.addAttribute("libro", new Libro());
        return "agregar_libro";
    }

    @PostMapping("/admin/agregar-libro")
    @PreAuthorize("hasRole('ADMIN')")
    public String agregarLibro(@ModelAttribute Libro libro) {
        libroService.guardarLibro(libro);
        return "redirect:/historial/admin";
    }
}


