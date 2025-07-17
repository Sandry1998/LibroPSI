package com.ceatformacion.libropsi.repository;

import com.ceatformacion.libropsi.modell.Historial;
import com.ceatformacion.libropsi.modell.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface HistorialRepository extends JpaRepository<Historial, Integer> {
    List<Historial> findByUsuario(Usuario usuario);

    List<Historial> findByUsuarioIdUsuario(int usuarioId);

    void deleteByLibroIdLibro(int idLibro);

}
