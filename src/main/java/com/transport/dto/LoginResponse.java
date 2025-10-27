package com.transport.dto;

public class LoginResponse {
    private String token;
    private String codigo;
    private String nombre;
    private String carrera;

    // Constructores
    public LoginResponse() {}

    public LoginResponse(String token, String codigo, String nombre, String carrera) {
        this.token = token;
        this.codigo = codigo;
        this.nombre = nombre;
        this.carrera = carrera;
    }

    // Getters y Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
}