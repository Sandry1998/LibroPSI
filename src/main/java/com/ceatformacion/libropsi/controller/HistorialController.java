package com.ceatformacion.libropsi.controller;


import com.ceatformacion.libropsi.modell.Historial;
import com.ceatformacion.libropsi.modell.Libro;
import com.ceatformacion.libropsi.repository.HistorialRepository;
import com.ceatformacion.libropsi.repository.LibroRepository;
import com.ceatformacion.libropsi.services.HistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HistorialController {



@Autowired
private HistorialService historialService;
    @Autowired
    private HistorialRepository historialRepository;

    @Autowired
    private LibroRepository libroRepository;


    @GetMapping("/historial/{id}")
    public String gethistorial(@PathVariable int id, Model model) {
        Historial historial = historialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));
        model.addAttribute("historial", historial);
        return "historial";
    }

    @ResponseBody
    @PostMapping("/api/historial")
    public Historial saveHistorial(@RequestBody Historial historial) {
        return historialRepository.save(historial);
    }

    @ResponseBody
    @GetMapping("/libro/{id}")
    public List<Historial> findByLibroId(@PathVariable int id) {
        return historialService.obtenerHistorialLibro(id);
    }

    @GetMapping("/consulta/{id}")
    public String getHistorialById(@PathVariable int id, Model model) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        List<Historial> historial = historialRepository.findByLibroId(id);
        model.addAttribute("historial", historial);
        model.addAttribute("libro", libro);
        model.addAttribute("nuevoHistorial", new Historial()); // ✅ Corregido
        return "historial";
    }

    @PostMapping("/consulta/{id}")
    public String registrarHistorial(@PathVariable int id,
                                     @ModelAttribute("nuevoHistorial") Historial nuevoHistorial) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        nuevoHistorial.setLibro(libro);
        historialRepository.save(nuevoHistorial);
        return "redirect:/consulta/" + id;
    }



}
