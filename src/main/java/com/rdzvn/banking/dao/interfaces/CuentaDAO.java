package com.rdzvn.banking.dao.interfaces;

import com.rdzvn.banking.model.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaDAO {
    Cuenta guardar(Cuenta cuenta);
    Optional<Cuenta> buscarPorId(Long id);
    Optional<Cuenta> buscarPorNumeroCuenta(String numeroCuenta);
    List<Cuenta> buscarPorUsuarioId(Long usuarioId);
    List<Cuenta> buscarTodas();
    Cuenta actualizar(Cuenta cuenta);
    boolean desactivar(Long id);
}
