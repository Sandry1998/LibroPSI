package com.ceatformacion.libropsi.controller;

import com.ceatformacion.libropsi.modell.Libro;
import com.ceatformacion.libropsi.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    /**
     * Mostrar página principal con todos los libros
     */
    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("libros", libroRepository.findAll());
        return "index";
    }

    /**
     * Mostrar formulario para nuevo libro
     */
    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("libro", new Libro());
        return "formulario";
    }

    /**
     * Guardar libro (nuevo o editado)
     */
    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute("libro") Libro libro, Model model) {
        try {
            libroRepository.save(libro);
            return "redirect:/crud";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el libro: " + e.getMessage());
            return "formulario";
        }
    }

    /**
     * Mostrar listado de libros para CRUD
     */
    @GetMapping("/crud")
    public String mostrarLibros(Model model) {
        List<Libro> libros = libroRepository.findAll();
        model.addAttribute("librosParaCrud", libros);
        return "crud";
    }

    /**
     * Mostrar formulario para editar libro
     */
    @GetMapping("/editar/{id}")
    public String editarLibro(@PathVariable int id, Model model) {
        try {
            Libro libro = libroRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
            model.addAttribute("libro", libro);
            return "formulario";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/crud";
        }
    }

    /**
     * Ver detalles de un libro
     */
    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable int id, Model model) {
        try {
            Libro libro = libroRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
            model.addAttribute("libro", libro);
            return "detalle";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/crud";
        }
    }

    /**
     * Eliminar libro
     */
    @GetMapping("/borrar/{id}")
    public String eliminarLibro(@PathVariable int id, Model model) {
        try {
            if (libroRepository.existsById(id)) {
                libroRepository.deleteById(id);
            } else {
                throw new RuntimeException("Libro no encontrado con ID: " + id);
            }
            return "redirect:/crud";
        } catch (Exception e) {
            model.addAttribute("error", "Error al eliminar el libro: " + e.getMessage());
            return "redirect:/crud";
        }
    }

    /**
     * Buscar libros por título
     */
    @GetMapping("/buscar")
    public String buscarPorTitulo(@RequestParam(required = false) String titulo, Model model) {
        List<Libro> resultado;

        if (titulo != null && !titulo.trim().isEmpty()) {
            resultado = libroRepository.findByTituloContainingIgnoreCase(titulo.trim());
        } else {
            resultado = libroRepository.findAll();
        }

        model.addAttribute("librosParaCrud", resultado);
        model.addAttribute("terminoBusqueda", titulo);
        return "crud";
    }

    /**
     * API REST - Obtener todos los libros (JSON)
     */
    @ResponseBody
    @GetMapping("/api/libros")
    public List<Libro> obtenerLibrosJson() {
        return libroRepository.findAll();
    }

    /**
     * API REST - Obtener libro por ID (JSON)
     */
    @ResponseBody
    @GetMapping("/api/libros/{id}")
    public Libro obtenerLibroPorId(@PathVariable int id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    /**
     * API REST - Crear nuevo libro (JSON)
     */
    @ResponseBody
    @PostMapping("/api/libros")
    public Libro crearLibro(@RequestBody Libro libro) {
        return libroRepository.save(libro);
    }

    /**
     * API REST - Actualizar libro (JSON)
     */
    @ResponseBody
    @PutMapping("/api/libros/{id}")
    public Libro actualizarLibro(@PathVariable int id, @RequestBody Libro libroActualizado) {
        return libroRepository.findById(id)
                .map(libro -> {
                    libro.setTitulo(libroActualizado.getTitulo());
                    libro.setAutor(libroActualizado.getAutor());
                    libro.setGenero(libroActualizado.getGenero());
                    libro.setEditorial(libroActualizado.getEditorial());
                    libro.setPaginas(libroActualizado.getPaginas());
                    return libroRepository.save(libro);
                })
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    /**
     * API REST - Eliminar libro (JSON)
     */
    @ResponseBody
    @DeleteMapping("/api/libros/{id}")
    public void eliminarLibroApi(@PathVariable int id) {
        if (libroRepository.existsById(id)) {
            libroRepository.deleteById(id);
        } else {
            throw new RuntimeException("Libro no encontrado");
        }
    }
}