package com.transport.service;

import com.transport.entity.Ruta;
import com.transport.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRepository;

    @Transactional
    public Ruta crearRuta(Ruta ruta) {
        return rutaRepository.save(ruta);
    }

    public List<Ruta> obtenerTodasLasRutas() {
        return rutaRepository.findAll();
    }

    public Ruta obtenerRutaPorId(Long id) {
        return rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
    }

    @Transactional
    public Ruta actualizarRuta(Long id, Ruta rutaData) {
        Ruta ruta = obtenerRutaPorId(id);

        ruta.setNombre(rutaData.getNombre());
        ruta.setOrigen(rutaData.getOrigen());
        ruta.setDestino(rutaData.getDestino());

        return rutaRepository.save(ruta);
    }

    @Transactional
    public void eliminarRuta(Long id) {
        if (!rutaRepository.existsById(id)) {
            throw new RuntimeException("Ruta no encontrada");
        }
        rutaRepository.deleteById(id);
    }

    public List<Ruta> buscarRutasPorOrigen(String origen) {
        return rutaRepository.findByOrigenContainingIgnoreCase(origen);
    }

    public List<Ruta> buscarRutasPorDestino(String destino) {
        return rutaRepository.findByDestinoContainingIgnoreCase(destino);
    }
}
