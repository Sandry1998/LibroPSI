package com.ceatformacion.libropsi.services;

import com.ceatformacion.libropsi.modell.Historial;
import com.ceatformacion.libropsi.modell.Usuario;
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

    public void guardarHistorial(Historial historial) {
        historialRepository.save(historial);
    }

    public void eliminarHistorial(int idHistorial) {
        historialRepository.deleteById(idHistorial);
    }

    public List<Historial> obtenerPorUsuario(Usuario usuario) {
        return historialRepository.findByUsuario(usuario);
    }

    public List<Historial> obtenerTodos() {
        return historialRepository.findAll();
    }

    public Optional<Historial> obtenerPorId(int idHistorial) {
        return historialRepository.findById(idHistorial);
    }

    public List<Historial> obtenerPorNombreUsuario(String nombre) {
        return historialRepository.findByUsuario_UsernameContainingIgnoreCase(nombre);
    }


}


