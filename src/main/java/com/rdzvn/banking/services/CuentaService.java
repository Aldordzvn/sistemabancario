package com.rdzvn.banking.services;

import com.rdzvn.banking.dao.impl.CuentaDaoImpl;
import com.rdzvn.banking.dao.interfaces.CuentaDAO;
import com.rdzvn.banking.model.Cuenta;
import com.rdzvn.banking.model.TipoCuenta;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CuentaService {

    private final CuentaDAO cuentaDAO;

    public CuentaService(){
        this.cuentaDAO = new CuentaDaoImpl();
    }

    public Cuenta abrir(Long usuarioId, TipoCuenta tipo, BigDecimal montoInicial){
        BigDecimal minimo = tipo.montoMinimoApertura();
        if(montoInicial.compareTo(minimo) < 0){
            throw new IllegalArgumentException("El monto mínimo de apertura para " + tipo + " es $" + minimo);
        }

        String numeroCuenta = generarNumeroCuenta(tipo);

        Cuenta cuenta = new Cuenta(usuarioId, numeroCuenta, tipo, montoInicial);
        return cuentaDAO.guardar(cuenta);
    }

    public List<Cuenta> listarPorUsuario(Long usuarioId){
        return cuentaDAO.buscarPorUsuarioId(usuarioId);
    }

    public Optional<Cuenta> buscarPorId(Long id){
        return cuentaDAO.buscarPorId(id);
    }

    public Optional<Cuenta> buscarPorNumeroCuenta(String numeroCuenta){
        return cuentaDAO.buscarPorNumeroCuenta(numeroCuenta);
    }

    public boolean cerrar(Long cuentaId, Long usuarioId){
        Optional<Cuenta> opt = cuentaDAO.buscarPorId(cuentaId);

        if(opt.isEmpty()){
            throw new IllegalArgumentException("La cuenta no existe o ya esta inactiva");
        }

        Cuenta cuenta = opt.get();

        if(!cuenta.getUsuarioId().equals(usuarioId)){
            throw new IllegalArgumentException("No tienes permiso para cerrar esta cuenta");
        }

        if(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0){
            throw new IllegalArgumentException("No se puede cerrar una cuenta con saldo. Saldo Actual: $" + cuenta.getSaldo());
        }

        return cuentaDAO.desactivar(cuentaId);
    }

    public String generarNumeroCuenta(TipoCuenta tipo){
        String prefijo = switch (tipo){
            case AHORRO -> "AH";
            case CORRIENTE -> "CO";
            case NOMINA -> "NM";
        };
        return prefijo + "-" + System.currentTimeMillis();
    }
}
