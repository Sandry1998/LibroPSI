package com.ceatformacion.libropsi.services;

import com.ceatformacion.libropsi.modell.Historial;
import com.ceatformacion.libropsi.repository.HistorialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialService {

   private HistorialRepository historialRepository;

   public HistorialService(HistorialRepository historialRepository) {
       this.historialRepository = historialRepository;
   }

   public List<Historial> obtenerHistorialLibro(int id) {
       return historialRepository.findByLibroId(id);
   }

   public void borrarHistorialLibro(Integer id_historial) {
       historialRepository.deleteById(id_historial);
   }


}
