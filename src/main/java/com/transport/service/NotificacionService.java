package com.transport.service;

import com.transport.dto.NotificacionRequest;
import com.transport.entity.Notificacion;
import com.transport.entity.Ruta;
import com.transport.entity.Usuario;
import com.transport.repository.NotificacionRepository;
import com.transport.repository.RutaRepository;
import com.transport.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Notificacion crearNotificacion(NotificacionRequest request) {
        Ruta ruta = rutaRepository.findById(request.getRutaId())
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

        Usuario usuario = null;
        if (request.getUsuarioCodigo() != null && !request.getUsuarioCodigo().trim().isEmpty()) {
            usuario = usuarioRepository.findByCodigo(request.getUsuarioCodigo())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado" + request.getUsuarioCodigo()));
            System.out.println("Usuario encontrado: " + usuario.getCodigo() );
        } else { System.out.println("Creando notificación GLOBAL (sin usuario)"); }

        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(request.getMensaje());
        notificacion.setRuta(ruta);
        notificacion.setUsuario(usuario);

        return notificacionRepository.save(notificacion);
    }

    public List<Notificacion> obtenerNotificacionesGlobales() {
        return notificacionRepository.findByUsuarioIsNullOrderByFechaDesc();
    }

    public List<Notificacion> obtenerNotificacionesParaUsuario(String codigo) {
        System.out.println("=== BUSCANDO NOTIFICACIONES ===");
        System.out.println("Usuario código: " + codigo);

        List<Notificacion> notificaciones = notificacionRepository.findNotificacionesForUser(codigo);

        System.out.println("Notificaciones encontradas: " + notificaciones.size());
        notificaciones.forEach(n -> {
            System.out.println("- ID: " + n.getId() +
                    " | Mensaje: " + n.getMensaje() +
                    " | Tipo: " + (n.getUsuario() == null ? "GLOBAL" : "PERSONAL"));
        });

        return notificaciones;
    }
    public List<Notificacion> obtenerNotificacionesNoLeidasParaUsuario(String codigo) {
        return notificacionRepository.findUnreadNotificacionesForUser(codigo);
    }

    @Transactional
    public Notificacion marcarComoLeida(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        notificacion.setLeida(true);
        return notificacionRepository.save(notificacion);
    }

    @Transactional
    public void eliminarNotificacion(Long id) {
        if (!notificacionRepository.existsById(id)) {
            throw new RuntimeException("Notificación no encontrada");
        }
        notificacionRepository.deleteById(id);
    }

    public List<Notificacion> obtenerNotificacionesPorRuta(Long rutaId) {
        return notificacionRepository.findByRutaIdOrderByFechaDesc(rutaId);
    }
}