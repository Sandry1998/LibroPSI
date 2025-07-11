package com.ceatformacion.libropsi.controller;


import com.ceatformacion.libropsi.modell.Historial;
import com.ceatformacion.libropsi.modell.Libro;
import com.ceatformacion.libropsi.modell.Usuario;

import com.ceatformacion.libropsi.services.HistorialService;
import com.ceatformacion.libropsi.services.LibroService;
import com.ceatformacion.libropsi.services.UsuarioDetails;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/historial")
public class HistorialController {
    @Autowired
    private HistorialService historialService;
    @Autowired
    private LibroService libroService;

    @GetMapping("/usuario")
    public String verHistorialUsuario(@AuthenticationPrincipal UsuarioDetails usuarioDetails, Model model) {
        Usuario usuario = usuarioDetails.getUsuario();
        List<Historial> historial = historialService.obtenerPorUsuario(usuario);
        model.addAttribute("historial", historial);
        return "historial_usuario";
    }

    @PostMapping("/reservar/{idLibro}")
    public String reservarLibro(@PathVariable int idLibro, @AuthenticationPrincipal UsuarioDetails usuarioDetails) {
        Optional<Libro> libroOpt = libroService.obtenerPorId(idLibro);
        if (libroOpt.isEmpty() || usuarioDetails == null || usuarioDetails.getUsuario() == null) {
            return "redirect:/libros/todos?error=No se pudo reservar";
        }

        Historial historial = new Historial();
        historial.setLibro(libroOpt.get());
        historial.setUsuario(usuarioDetails.getUsuario());
        historial.setEstado("RESERVADO");
        historial.setFechaReserva(LocalDate.now());
        historial.setFechaDevolucion(LocalDate.now().plusDays(7));
        historial.setObservaciones("Reserva creada");

        historialService.guardarHistorial(historial);
        return "redirect:/historial/usuario";
    }

    @GetMapping("/eliminar/{idHistorial}")
    public String eliminarHistorial(@PathVariable int idHistorial, @AuthenticationPrincipal UsuarioDetails usuarioDetails) {
        historialService.eliminarHistorial(idHistorial);
        return "redirect:/historial/usuario";
    }

    @GetMapping("/admin")
    public String historialAdmin(Model model) {
        model.addAttribute("historial", historialService.obtenerTodos());
        return "historial_admin";
    }

    @PostMapping("/editar")
    public String editarHistorial(@RequestParam int idHistorial,
                                  @RequestParam String estado,
                                  @RequestParam String observaciones) {
        Optional<Historial> optional = historialService.obtenerPorId(idHistorial);
        if (optional.isPresent()) {
            Historial historial = optional.get();
            historial.setEstado(estado);
            historial.setObservaciones(observaciones);
            historialService.guardarHistorial(historial);
        }
        return "redirect:/historial/admin";
    }


}




