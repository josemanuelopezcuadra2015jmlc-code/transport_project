package com.transport.controller;

import com.transport.dto.NotificacionRequest;
import com.transport.entity.Notificacion;
import com.transport.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<?> crearNotificacion(@Valid @RequestBody NotificacionRequest request) {
        try {
            Notificacion notificacion = notificacionService.crearNotificacion(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(notificacion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @GetMapping
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesGlobales() {
        List<Notificacion> notificaciones = notificacionService.obtenerNotificacionesGlobales();
        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping("/usuarios/{codigo}/notificaciones")
    public ResponseEntity<?> obtenerNotificacionesUsuario(@PathVariable String codigo) {
        try {
            List<Notificacion> notificaciones = notificacionService.obtenerNotificacionesParaUsuario(codigo);
            return ResponseEntity.ok(notificaciones);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @PutMapping("/{id}/leer")
    public ResponseEntity<?> marcarComoLeida(@PathVariable Long id) {
        try {
            Notificacion notificacion = notificacionService.marcarComoLeida(id);
            return ResponseEntity.ok(notificacion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarNotificacion(@PathVariable Long id) {
        try {
            notificacionService.eliminarNotificacion(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }
}
