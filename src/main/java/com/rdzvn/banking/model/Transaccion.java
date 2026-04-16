package com.rdzvn.banking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaccion {
    private Long id;
    private Long cuentaOrigenId;
    private Long cuentaDestinoId;
    private TipoTransaccion tipo;
    private BigDecimal monto;
    private String descripcion;
    private LocalDateTime creadoEn;

    public Transaccion() {
    }

    public Transaccion(Long cuentaOrigenId, Long cuentaDestinoId, TipoTransaccion tipo, BigDecimal monto, String descripcion) {
        this.cuentaOrigenId = cuentaOrigenId;
        this.cuentaDestinoId = cuentaDestinoId;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCuentaOrigenId() {
        return cuentaOrigenId;
    }

    public void setCuentaOrigenId(Long cuentaOrigenId) {
        this.cuentaOrigenId = cuentaOrigenId;
    }

    public Long getCuentaDestinoId() {
        return cuentaDestinoId;
    }

    public void setCuentaDestinoId(Long cuentaDestinoId) {
        this.cuentaDestinoId = cuentaDestinoId;
    }

    public TipoTransaccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransaccion tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "id=" + id +
                ", cuentaOrigenId=" + cuentaOrigenId +
                ", cuentaDestinoId=" + cuentaDestinoId +
                ", tipo=" + tipo +
                ", monto=" + monto +
                ", descripcion='" + descripcion + '\'' +
                ", creadoEn=" + creadoEn +
                '}';
    }
}
