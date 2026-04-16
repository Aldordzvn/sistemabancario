package com.rdzvn.banking.dao.interfaces;

import com.rdzvn.banking.model.Transaccion;

import java.util.List;
import java.util.Optional;

public interface TransaccionDAO {
    Transaccion guardar(Transaccion transaccion);
    Optional<Transaccion> buscarPorId(Long id);
    List<Transaccion> buscarPorCuentaId(Long cuentaId);
    List<Transaccion> buscarTodas();
}
