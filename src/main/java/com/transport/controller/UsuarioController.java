package com.transport.controller;

import com.transport.dto.PerfilResponse;
import com.transport.dto.UsuarioCreateRequest;
import com.transport.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioCreateRequest request) {
        try {
            PerfilResponse response = usuarioService.crearUsuario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> obtenerPerfil(@PathVariable String codigo) {
        try {
            PerfilResponse perfil = usuarioService.obtenerPerfilPorCodigo(codigo);
            return ResponseEntity.ok(perfil);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> actualizarPerfil(@PathVariable String codigo,
                                              @Valid @RequestBody PerfilResponse perfilData) {
        try {
            PerfilResponse perfilActualizado = usuarioService.actualizarPerfil(codigo, perfilData);
            return ResponseEntity.ok(perfilActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }
}
