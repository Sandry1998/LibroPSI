package com.ceatformacion.libropsi.services;

import com.ceatformacion.libropsi.modell.Historial;
import com.ceatformacion.libropsi.repository.HistorialRepository;
import com.ceatformacion.libropsi.repository.LibroRepository;
import com.ceatformacion.libropsi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialService {

    @Autowired
    private HistorialRepository historialRepository;

    public List<Historial> obtenerTodos() {
        return historialRepository.findAll();
    }

    public List<Historial> obtenerPorUsuario(int usuarioId) {
        return historialRepository.findByUsuarioIdUsuario(usuarioId);
    }

    public Historial guardarHistorial(Historial historial) {
        return historialRepository.save(historial);
    }

    public void eliminarHistorial(int id) {
        historialRepository.deleteById(id);
    }

    public Optional<Historial> obtenerPorId(int id) {
        return historialRepository.findById(id);
    }

    public boolean existeReservaActiva(int idUsuario, int idLibro) {
        // Implementar lógica si quieres verificar reservas activas
        return false;
    }
}



