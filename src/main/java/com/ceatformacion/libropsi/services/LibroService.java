package com.ceatformacion.libropsi.services;


import com.ceatformacion.libropsi.modell.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroService {
    List<Libro> obtenerTodos();
    Optional<Libro> obtenerPorId(int id);
    Libro guardarLibro(Libro libro);
    void eliminarLibro(int id);
}
