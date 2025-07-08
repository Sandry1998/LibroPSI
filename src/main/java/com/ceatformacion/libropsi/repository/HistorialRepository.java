package com.ceatformacion.libropsi.repository;

import com.ceatformacion.libropsi.modell.Historial;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface HistorialRepository extends JpaRepository<Historial, Integer> {
    List<Historial> findByLibro_Id_libro(int libroId);
    List<Historial> findByUsuario_Id_usuario(int usuarioId);
}
