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
    private HistorialRepository repo;
    @Autowired
    private HistorialRepository historialRepository;

    public List<Historial> obtenerPorUsuario(Usuario usuario) {
        return historialRepository.findByUsuarioIdUsuario(usuario.getIdUsuario());
    }

    public void guardarHistorial(Historial historial) {
        repo.save(historial);
    }

    public void eliminarHistorial(int idHistorial) {
        repo.deleteById(idHistorial);
    }
}


