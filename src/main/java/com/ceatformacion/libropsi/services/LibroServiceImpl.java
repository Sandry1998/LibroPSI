package com.ceatformacion.libropsi.services;

import com.ceatformacion.libropsi.modell.Libro;
import com.ceatformacion.libropsi.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Override
    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }

    @Override
    public Optional<Libro> obtenerPorId(int id) {
        return libroRepository.findById(id);
    }

    @Override
    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    public void eliminarLibro(int id) {
        libroRepository.deleteById(id);

    }
}
