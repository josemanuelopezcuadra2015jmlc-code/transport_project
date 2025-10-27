package com.transport.repository;

import com.transport.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);

    @Query("SELECT u FROM Usuario u JOIN FETCH u.perfil WHERE u.codigo = :codigo")
    Optional<Usuario> findByCodigoWithPerfil(@Param("codigo") String codigo);
}