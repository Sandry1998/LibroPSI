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
    private HistorialRepository repo;

    public List<Historial> obtenerPorUsuario(int idUsuario) {
        return repo.findByUsuarioIdUsuario(idUsuario);
    }

    public void guardarHistorial(Historial historial) {
        repo.save(historial);
    }

    public void eliminarHistorial(int idHistorial) {
        repo.deleteById(idHistorial);
    }
}


