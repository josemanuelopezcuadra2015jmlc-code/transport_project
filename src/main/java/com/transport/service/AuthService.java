package com.transport.service;

import com.transport.dto.LoginRequest;
import com.transport.dto.LoginResponse;
import com.transport.entity.Usuario;
import com.transport.repository.UsuarioRepository;
import com.transport.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getCodigo(), loginRequest.getPassword())
            );

            Usuario usuario = usuarioRepository.findByCodigoWithPerfil(loginRequest.getCodigo())
                    .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

            String token = jwtUtil.generateToken(usuario.getCodigo());

            return new LoginResponse(
                    token,
                    usuario.getCodigo(),
                    usuario.getPerfil().getNombre(),
                    usuario.getPerfil().getCarrera()
            );

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Credenciales inválidas");
        }
    }
}