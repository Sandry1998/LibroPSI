package com.ceatformacion.libropsi.services;

import com.ceatformacion.libropsi.modell.Historial;
import com.ceatformacion.libropsi.repository.HistorialRepository;
import com.ceatformacion.libropsi.repository.LibroRepository;
import com.ceatformacion.libropsi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialService {

    @Autowired
    private HistorialRepository historialRepository;

    public List<Historial> obtenerTodos() {
        return historialRepository.findAll();
    }

    public List<Historial> obtenerPorUsuario(int usuarioId) {
        return historialRepository.findByUsuarioId(usuarioId);
    }

    public Historial guardarHistorial(Historial historial) {
        return historialRepository.save(historial);
    }

    public void eliminarHistorial(int id) {
        historialRepository.deleteById(id);
    }

    public Historial obtenerPorId(int id) {
        return historialRepository.findById(id).orElse(null);
    }

    public boolean existeReservaActiva(int idUsuario, int id) {
    }
}


