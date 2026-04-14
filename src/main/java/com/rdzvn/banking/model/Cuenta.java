package com.rdzvn.banking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Cuenta {
    private Long id;
    private Long usuarioId;
    private String nombreCuenta;
    private TipoCuenta tipo;
    private BigDecimal saldo;
    private EstadoCuenta estado;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;

    public Cuenta() {
    }

    public Cuenta(Long usuarioId, String nombreCuenta, TipoCuenta tipo, BigDecimal saldo) {
        this.usuarioId = usuarioId;
        this.nombreCuenta = nombreCuenta;
        this.tipo = tipo;
        this.saldo = saldo;
        this.estado = EstadoCuenta.ACTIVA;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public TipoCuenta getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuenta tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public EstadoCuenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuenta estado) {
        this.estado = estado;
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
        return "Cuenta{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", nombreCuenta='" + nombreCuenta + '\'' +
                ", tipo=" + tipo +
                ", saldo=" + saldo +
                ", estado=" + estado +
                ", creadoEn=" + creadoEn +
                ", actualizadoEn=" + actualizadoEn +
                '}';
    }
}
