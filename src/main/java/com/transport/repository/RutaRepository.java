package com.transport.repository;

import com.transport.entity.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {
    List<Ruta> findByOrigenContainingIgnoreCase(String origen);
    List<Ruta> findByDestinoContainingIgnoreCase(String destino);
    List<Ruta> findByNombreContainingIgnoreCase(String nombre);
}