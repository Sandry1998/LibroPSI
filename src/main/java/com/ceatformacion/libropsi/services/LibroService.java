package com.ceatformacion.libropsi.services;


import com.ceatformacion.libropsi.modell.Libro;
import com.ceatformacion.libropsi.repository.HistorialRepository;
import com.ceatformacion.libropsi.repository.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    @Autowired
    private LibroRepository repo;

    @Autowired
    private HistorialRepository histRepo;

    @Autowired
    private LibroRepository libroRepository;


    public List<Libro> obtenerTodos() {
        return repo.findAll();
    }

    public Optional<Libro> obtenerPorId(int id) {
        return repo.findById(id);
    }

    public void guardarLibro(Libro libro) {
        repo.save(libro);
    }

    @Transactional
    public void eliminarLibro(int idLibro) {
        histRepo.deleteByLibroIdLibro(idLibro);
        repo.deleteById(idLibro);
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }



}
