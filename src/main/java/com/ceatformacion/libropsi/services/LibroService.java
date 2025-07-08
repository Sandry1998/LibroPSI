package com.ceatformacion.libropsi.services;


import com.ceatformacion.libropsi.modell.Libro;
import com.ceatformacion.libropsi.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    // Obtener todos los libros
    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }

    // Obtener libro por ID
    public Optional<Libro> obtenerPorId(int id) {
        return libroRepository.findById(id);
    }

    // Guardar libro (nuevo o editar existente)
    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    // Eliminar libro por ID
    public void eliminarLibro(int id) {
        libroRepository.deleteById(id);
    }

    // Buscar libros por título (contiene texto, case insensitive)
    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }
}

