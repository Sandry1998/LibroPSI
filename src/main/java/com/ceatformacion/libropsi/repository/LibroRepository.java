package com.ceatformacion.libropsi.repository;


import com.ceatformacion.libropsi.modell.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
}
