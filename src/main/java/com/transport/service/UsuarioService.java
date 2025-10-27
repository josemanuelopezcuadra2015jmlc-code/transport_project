package com.transport.service;

import com.transport.dto.PerfilResponse;
import com.transport.dto.UsuarioCreateRequest;
import com.transport.entity.Perfil;
import com.transport.entity.Usuario;
import com.transport.repository.PerfilRepository;
import com.transport.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    // Inyección por constructor para evitar dependencia circular
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String codigo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCodigo(codigo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + codigo));

        return new User(usuario.getCodigo(), usuario.getPassword(), new ArrayList<>());
    }

    @Transactional
    public PerfilResponse crearUsuario(UsuarioCreateRequest request) {
        if (usuarioRepository.existsByCodigo(request.getCodigo())) {
            throw new RuntimeException("El código ya está en uso");
        }

        Usuario usuario = new Usuario();
        usuario.setCodigo(request.getCodigo());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        Perfil perfil = new Perfil();
        perfil.setNombre(request.getNombre());
        perfil.setCarrera(request.getCarrera());
        perfil.setUsuario(usuarioGuardado);

        perfilRepository.save(perfil);

        return new PerfilResponse(
                usuarioGuardado.getCodigo(),
                perfil.getNombre(),
                perfil.getCarrera()
        );
    }

    public PerfilResponse obtenerPerfilPorCodigo(String codigo) {
        Perfil perfil = perfilRepository.findByUsuarioCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        return new PerfilResponse(
                perfil.getUsuario().getCodigo(),
                perfil.getNombre(),
                perfil.getCarrera()
        );
    }

    @Transactional
    public PerfilResponse actualizarPerfil(String codigo, PerfilResponse perfilData) {
        Perfil perfil = perfilRepository.findByUsuarioCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        perfil.setNombre(perfilData.getNombre());
        perfil.setCarrera(perfilData.getCarrera());

        Perfil perfilActualizado = perfilRepository.save(perfil);

        return new PerfilResponse(
                perfilActualizado.getUsuario().getCodigo(),
                perfilActualizado.getNombre(),
                perfilActualizado.getCarrera()
        );
    }
}