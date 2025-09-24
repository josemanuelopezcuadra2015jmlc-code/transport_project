package com.transport.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "El código es requerido")
    private String codigo;

    @NotBlank(message = "La contraseña es requerida")
    private String password;

    // Constructores
    public LoginRequest() {}

    public LoginRequest(String codigo, String password) {
        this.codigo = codigo;
        this.password = password;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
