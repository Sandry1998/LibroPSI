package com.ceatformacion.libropsi.controller;


import com.ceatformacion.libropsi.modell.Historial;
import com.ceatformacion.libropsi.modell.Libro;


import com.ceatformacion.libropsi.services.HistorialService;
import com.ceatformacion.libropsi.services.LibroService;
import com.ceatformacion.libropsi.services.UsuarioDetails;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
    public String historialUsuario(@AuthenticationPrincipal UsuarioDetails usuarioDetails, Model model) {
        List<Historial> historial = historialService
                .obtenerPorUsuario(usuarioDetails.getUsuario())
                .stream()
                .filter(h -> h.getLibro() != null)
                .toList();

        model.addAttribute("historial", historial);
        return "historial_usuario";
    }


    @PostMapping("/reservar/{idLibro}")
    public String reservarLibro(@PathVariable int idLibro, @AuthenticationPrincipal UsuarioDetails usuarioDetails) {
        Optional<Libro> libroOpt = libroService.obtenerPorId(idLibro);
        if (libroOpt.isEmpty() ) {
            return "redirect:/libros/todos?error=No se pudo reservar";
        }
        if (usuarioDetails == null || usuarioDetails.getUsuario() == null) {
            return "redirect:/libros/todos?error=No se pudo identificar al usuario";
        }

        Historial historial = new Historial();
        historial.setLibro(libroOpt.get());
        historial.setUsuario(usuarioDetails.getUsuario());
        historial.setEstado("RESERVADO");
        historial.setFechaReserva(LocalDate.now());
        historial.setFechaDevolucion(LocalDate.now().plusDays(7));
        historial.setObservaciones("");

        historialService.guardarHistorial(historial);
        return "redirect:/historial/usuario?exito=Libro reservado correctamente";
    }

    @GetMapping("/eliminar/{idHistorial}")
    public String eliminarHistorial(@PathVariable int idHistorial,
                                    @AuthenticationPrincipal UsuarioDetails usuarioDetails) {

        historialService.eliminarHistorial(idHistorial);

        // Redirige según el rol
        if (usuarioDetails != null && usuarioDetails.getUsuario().getRol().equals("ADMIN")) {
            return "redirect:/libros/todos";
        }

        return "redirect:/historial/usuario";
    }


    @GetMapping("/admin")
    public String historialAdmin(Model model) {
        List<Historial> historial = historialService.obtenerTodos()
                        .stream().filter(h->h.getLibro() != null).toList();
        model.addAttribute("historial", historial);
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




