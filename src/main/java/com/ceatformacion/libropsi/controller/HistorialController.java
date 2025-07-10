package com.ceatformacion.libropsi.controller;


import com.ceatformacion.libropsi.modell.Historial;
import com.ceatformacion.libropsi.modell.Libro;
import com.ceatformacion.libropsi.modell.Usuario;

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

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;


@Controller
@RequestMapping("/historial")
public class HistorialController {
    @Autowired
    private HistorialService historialService;

    @GetMapping("/usuario")
    public String historialUsuario(@AuthenticationPrincipal UsuarioDetails usuarioDetails, Model model) {
        int idUsuario = usuarioDetails.getUsuario().getIdUsuario();
        model.addAttribute("historial", historialService.obtenerPorUsuario(idUsuario));
        return "historial_usuario";
    }

    @PostMapping("/reservar/{idLibro}")
    public String reservarLibro(@PathVariable int idLibro, @AuthenticationPrincipal UsuarioDetails usuarioDetails) {
        Historial h = new Historial();
        h.setLibro(new Libro());
        h.getLibro().setIdLibro(idLibro);
        h.setUsuario(usuarioDetails.getUsuario());
        h.setEstado("RESERVADO");
        h.setFechaReserva(LocalDate.now());
        historialService.guardarHistorial(h);
        return "redirect:/historial/usuario";
    }

    @GetMapping("/eliminar/{idHistorial}")
    public String eliminarHistorial(@PathVariable int idHistorial, @AuthenticationPrincipal UsuarioDetails usuarioDetails) {
        historialService.eliminarHistorial(idHistorial);
        return "redirect:/historial/usuario";
    }
}




