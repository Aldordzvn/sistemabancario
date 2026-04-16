package com.rdzvn.banking.model;

import java.time.LocalDateTime;

public class Usuario {
    private Long id;
    private String nombreUsuario;
    private String correo;
    private String passwordHash;
    private boolean isActive;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;

    public Usuario() {
    }

    public Usuario(String nombreUsuario, String correo, String passwordHash) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.isActive = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", correo='" + correo + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", isActive=" + isActive +
                ", creadoEn=" + creadoEn +
                ", actualizadoEn=" + actualizadoEn +
                '}';
    }
}
