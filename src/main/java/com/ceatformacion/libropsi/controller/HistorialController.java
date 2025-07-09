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
import java.util.Optional;


@Controller
@RequestMapping("/historial")
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    @GetMapping("/usuario")
    public String historialUsuario(@AuthenticationPrincipal UsuarioDetails usuarioDetails, Model model) {
        model.addAttribute("historiales", historialService.obtenerPorUsuario(usuarioDetails.getUsuario().getIdUsuario()));
        return "historial_usuario"; // usuario.html
    }

    @GetMapping("/admin")
    public String historialAdmin(Model model) {
        model.addAttribute("historiales", historialService.obtenerTodos());
        return "historial_admin"; // admin.html
    }

    @PostMapping("/modificar-estado/{id}")
    public String modificarEstado(@PathVariable int id, @RequestParam String estado) {
        Optional<Historial> optional = historialService.obtenerPorId(id);
        if (optional.isPresent()) {
            Historial historial = optional.get();
            historial.setEstado(estado);
            historialService.guardarHistorial(historial);
        }
        return "redirect:/historial_admin";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarHistorial(@PathVariable int id) {
        historialService.eliminarHistorial(id);
        return "redirect:/historial_admin";
    }
}




