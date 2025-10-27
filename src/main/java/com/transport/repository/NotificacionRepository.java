package com.transport.repository;

import com.transport.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    // Notificaciones globales (usuario_id es null)
    List<Notificacion> findByUsuarioIsNullOrderByFechaDesc();

    // Notificaciones específicas de un usuario
    List<Notificacion> findByUsuarioCodigoOrderByFechaDesc(String codigo);

    // Notificaciones de una ruta específica
    List<Notificacion> findByRutaIdOrderByFechaDesc(Long rutaId);

    // CORREGIDO: Notificaciones combinadas para un usuario
    @Query("SELECT DISTINCT n FROM Notificacion n " +
            "LEFT JOIN FETCH n.ruta r " +
            "LEFT JOIN FETCH n.usuario u " +
            "WHERE n.usuario IS NULL OR n.usuario.codigo = :codigo " +
            "ORDER BY n.fecha DESC")
    List<Notificacion> findNotificacionesForUser(@Param("codigo") String codigo);

    // Notificaciones no leídas
    @Query("SELECT DISTINCT n FROM Notificacion n " +
            "LEFT JOIN FETCH n.ruta r " +
            "LEFT JOIN FETCH n.usuario u " +
            "WHERE (n.usuario IS NULL OR n.usuario.codigo = :codigo) " +
            "AND n.leida = false " +
            "ORDER BY n.fecha DESC")
    List<Notificacion> findUnreadNotificacionesForUser(@Param("codigo") String codigo);
}