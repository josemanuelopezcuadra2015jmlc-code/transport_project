package com.transport.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificacionRequest {
    @NotBlank(message = "El mensaje es requerido")
    private String mensaje;

    @NotNull(message = "El ID de la ruta es requerido")
    private Long rutaId;

    private String usuarioCodigo; // null para notificaciones globales

    // Constructores
    public NotificacionRequest() {}

    public NotificacionRequest(String mensaje, Long rutaId, String usuarioCodigo) {
        this.mensaje = mensaje;
        this.rutaId = rutaId;
        this.usuarioCodigo = usuarioCodigo;
    }

    // Getters y Setters
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Long getRutaId() { return rutaId; }
    public void setRutaId(Long rutaId) { this.rutaId = rutaId; }

    public String getUsuarioCodigo() { return usuarioCodigo; }
    public void setUsuarioCodigo(String usuarioCodigo) { this.usuarioCodigo = usuarioCodigo; }
}