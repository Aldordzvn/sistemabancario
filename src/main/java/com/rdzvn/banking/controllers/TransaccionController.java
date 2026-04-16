package com.rdzvn.banking.controllers;

import com.rdzvn.banking.model.Transaccion;
import com.rdzvn.banking.services.TransaccionService;

import java.math.BigDecimal;
import java.util.List;

public class TransaccionController {
    private final TransaccionService transaccionService;

    public TransaccionController(){
        this.transaccionService = new TransaccionService();
    }

    public Transaccion depositar(Long cuentaId, BigDecimal monto, String descripcion){
        return transaccionService.depositar(cuentaId, monto, descripcion);
    }

    public Transaccion retirar(Long cuentaId, BigDecimal monto, String descripcion){
        return transaccionService.retirar(cuentaId, monto, descripcion);
    }

    public Transaccion transferir(Long origenId, Long destinoId, BigDecimal monto, String descripcion){
        return transaccionService.transferir(origenId, destinoId, monto, descripcion);
    }

    public List<Transaccion> verHistorial(Long cuentaId){
        return transaccionService.obtenerHistorial(cuentaId);
    }
}
