package com.nexpoint.demo.model;

import java.util.Date;

public class Usuario {
    private Integer idUsuario;
    private Rol rol;
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String contrasena;
    private Date fechaRegistro;
    private String estado;

    public Usuario() {}

    public Usuario(Integer idUsuario, Rol rol, String nombre, String apellido, String correoElectronico, String contrasena, Date fechaRegistro, String estado) {
        this.idUsuario = idUsuario;
        this.rol = rol;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}