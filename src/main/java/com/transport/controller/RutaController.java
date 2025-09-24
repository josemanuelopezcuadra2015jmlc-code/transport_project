package com.transport.controller;

import com.transport.entity.Ruta;
import com.transport.service.RutaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rutas")
@CrossOrigin(origins = "*")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    @PostMapping
    public ResponseEntity<?> crearRuta(@Valid @RequestBody Ruta ruta) {
        try {
            Ruta rutaCreada = rutaService.crearRuta(ruta);
            return ResponseEntity.status(HttpStatus.CREATED).body(rutaCreada);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @GetMapping
    public ResponseEntity<List<Ruta>> obtenerTodasLasRutas() {
        List<Ruta> rutas = rutaService.obtenerTodasLasRutas();
        return ResponseEntity.ok(rutas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerRutaPorId(@PathVariable Long id) {
        try {
            Ruta ruta = rutaService.obtenerRutaPorId(id);
            return ResponseEntity.ok(ruta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRuta(@PathVariable Long id, @Valid @RequestBody Ruta rutaData) {
        try {
            Ruta rutaActualizada = rutaService.actualizarRuta(id, rutaData);
            return ResponseEntity.ok(rutaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRuta(@PathVariable Long id) {
        try {
            rutaService.eliminarRuta(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }
}