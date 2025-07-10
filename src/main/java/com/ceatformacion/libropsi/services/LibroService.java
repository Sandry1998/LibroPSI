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
    private LibroRepository repo;

    public List<Libro> obtenerTodos() {
        return repo.findAll();
    }

    public Optional<Libro> obtenerPorId(int id) {
        return repo.findById(id);
    }

    public void guardarLibro(Libro libro) {
        repo.save(libro);
    }

    public void eliminarLibro(int id) {
        repo.deleteById(id);
    }
}
