package com.rdzvn.banking.controllers;

import com.rdzvn.banking.model.Cuenta;
import com.rdzvn.banking.model.TipoCuenta;
import com.rdzvn.banking.services.CuentaService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CuentaController {
    private final CuentaService cuentaService;

    public CuentaController(){
        this.cuentaService = new CuentaService();
    }

    public Cuenta abrirCuenta(Long usuarioId, TipoCuenta tipo, BigDecimal montoInicial){
        return cuentaService.abrir(usuarioId, tipo, montoInicial);
    }

    public List<Cuenta> listarMisCuentas(Long usuarioId){
        return cuentaService.listarPorUsuario(usuarioId);
    }

    public Optional<Cuenta> buscarCuenta(Long cuentaId){
        return cuentaService.buscarPorId(cuentaId);
    }

    public boolean cerrarCuenta(Long cuentaId, Long usuarioId){
        return cuentaService.cerrar(cuentaId, usuarioId);
    }
}
