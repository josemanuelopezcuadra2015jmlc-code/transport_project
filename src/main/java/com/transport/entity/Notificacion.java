package com.transport.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "El mensaje es requerido")
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private Boolean leida = false;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER en lugar de LAZY
    @JoinColumn(name = "ruta_id", nullable = false)
    @JsonIgnoreProperties({"notificaciones"})
    private Ruta ruta;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER en lugar de LAZY
    @JoinColumn(name = "usuario_id", nullable = true)
    @JsonIgnoreProperties({"notificaciones", "password", "perfil"})
    private Usuario usuario;

    // Constructores
    public Notificacion() {
        this.fecha = LocalDateTime.now();
    }

    public Notificacion(String mensaje, Ruta ruta, Usuario usuario) {
        this.mensaje = mensaje;
        this.ruta = ruta;
        this.usuario = usuario;
        this.fecha = LocalDateTime.now();
        this.leida = false;
    }

    // Getters y Setters (los que ya tienes)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public Boolean getLeida() { return leida; }
    public void setLeida(Boolean leida) { this.leida = leida; }

    public Ruta getRuta() { return ruta; }
    public void setRuta(Ruta ruta) { this.ruta = ruta; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}