package com.rdzvn.banking.views;

import com.rdzvn.banking.controllers.CuentaController;
import com.rdzvn.banking.controllers.TransaccionController;
import com.rdzvn.banking.model.Cuenta;
import com.rdzvn.banking.model.Transaccion;

import java.math.BigDecimal;
import java.util.List;

public class TransaccionView {
    private final TransaccionController transaccionController;
    private final CuentaController cuentaController;

    public TransaccionView(TransaccionController transaccionController,
                           CuentaController cuentaController) {
        this.transaccionController = transaccionController;
        this.cuentaController      = cuentaController;
    }

    public void mostrarMenu(Long usuarioId) {
        boolean continuar = true;
        while (continuar) {
            ConsolaHelper.imprimirTitulo("OPERACIONES");
            System.out.println("  1. Depositar");
            System.out.println("  2. Retirar");
            System.out.println("  3. Transferir");
            System.out.println("  4. Ver historial de cuenta");
            System.out.println("  0. Volver");
            ConsolaHelper.imprimirLinea();

            int opcion = ConsolaHelper.leerEntero("  Opción: ");
            switch (opcion) {
                case 1 -> depositar(usuarioId);
                case 2 -> retirar(usuarioId);
                case 3 -> transferir(usuarioId);
                case 4 -> verHistorial(usuarioId);
                case 0 -> continuar = false;
                default -> ConsolaHelper.imprimirError("Opción no válida");
            }
        }
    }

    private void depositar(Long usuarioId) {
        ConsolaHelper.imprimirTitulo("DEPOSITAR");
        Long cuentaId = seleccionarCuenta(usuarioId);
        if (cuentaId == null) return;

        BigDecimal monto = ConsolaHelper.leerMonto("  Monto a depositar: $");
        String desc = ConsolaHelper.leerTexto("  Descripción (opcional): ");

        try {
            transaccionController.depositar(cuentaId, monto, desc);
            ConsolaHelper.imprimirExito("Depósito realizado por $" + monto);
        } catch (Exception e) {
            ConsolaHelper.imprimirError(e.getMessage());
        }
        ConsolaHelper.presionaEnter();
    }

    private void retirar(Long usuarioId) {
        ConsolaHelper.imprimirTitulo("RETIRAR");
        Long cuentaId = seleccionarCuenta(usuarioId);
        if (cuentaId == null) return;

        BigDecimal monto = ConsolaHelper.leerMonto("  Monto a retirar: $");
        String desc = ConsolaHelper.leerTexto("  Descripción (opcional): ");

        try {
            transaccionController.retirar(cuentaId, monto, desc);
            ConsolaHelper.imprimirExito("Retiro realizado por $" + monto);
        } catch (Exception e) {
            ConsolaHelper.imprimirError(e.getMessage());
        }
        ConsolaHelper.presionaEnter();
    }

    private void transferir(Long usuarioId) {
        ConsolaHelper.imprimirTitulo("TRANSFERIR");
        System.out.println("  Cuenta origen:");
        Long origenId = seleccionarCuenta(usuarioId);
        if (origenId == null) return;

        Long destinoId = (long) ConsolaHelper.leerEntero("  ID de cuenta destino: ");
        BigDecimal monto = ConsolaHelper.leerMonto("  Monto a transferir: $");
        String desc = ConsolaHelper.leerTexto("  Descripción: ");

        try {
            transaccionController.transferir(origenId, destinoId, monto, desc);
            ConsolaHelper.imprimirExito("Transferencia realizada por $" + monto);
        } catch (Exception e) {
            ConsolaHelper.imprimirError(e.getMessage());
        }
        ConsolaHelper.presionaEnter();
    }

    private void verHistorial(Long usuarioId) {
        ConsolaHelper.imprimirTitulo("HISTORIAL DE TRANSACCIONES");
        Long cuentaId = seleccionarCuenta(usuarioId);
        if (cuentaId == null) return;

        List<Transaccion> historial = transaccionController.verHistorial(cuentaId);

        if (historial.isEmpty()) {
            System.out.println("  No hay transacciones para esta cuenta.");
        } else {
            historial.forEach(t -> System.out.printf(
                    "  [%d] %s | $%.2f | %s | %s%n",
                    t.getId(), t.getTipo(), t.getMonto(),
                    t.getDescripcion(), t.getCreadoEn()
            ));
        }
        ConsolaHelper.presionaEnter();
    }

    // Muestra las cuentas del usuario y devuelve el id seleccionado
    private Long seleccionarCuenta(Long usuarioId) {
        List<Cuenta> cuentas = cuentaController.listarMisCuentas(usuarioId);
        if (cuentas.isEmpty()) {
            ConsolaHelper.imprimirError("No tienes cuentas activas");
            ConsolaHelper.presionaEnter();
            return null;
        }
        cuentas.forEach(c -> System.out.printf(
                "  [%d] %s | Saldo: $%.2f%n",
                c.getId(), c.getNumeroCuenta(), c.getSaldo()
        ));
        return (long) ConsolaHelper.leerEntero("  Selecciona el ID de la cuenta: ");
    }
}
