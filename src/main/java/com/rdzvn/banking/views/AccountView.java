package com.rdzvn.banking.views;

import com.rdzvn.banking.controllers.CuentaController;
import com.rdzvn.banking.model.Cuenta;
import com.rdzvn.banking.model.TipoCuenta;

import java.math.BigDecimal;
import java.util.List;

public class AccountView {
    private final CuentaController cuentaController;

    public AccountView(CuentaController cuentaController) {
        this.cuentaController = cuentaController;
    }

    public void mostrarMenu(Long usuarioId) {
        boolean continuar = true;
        while (continuar) {
            ConsolaHelper.imprimirTitulo("GESTIÓN DE CUENTAS");
            System.out.println("  1. Abrir nueva cuenta");
            System.out.println("  2. Ver mis cuentas");
            System.out.println("  3. Cerrar una cuenta");
            System.out.println("  0. Volver");
            ConsolaHelper.imprimirLinea();

            int opcion = ConsolaHelper.leerEntero("  Opción: ");
            switch (opcion) {
                case 1 -> abrirCuenta(usuarioId);
                case 2 -> verCuentas(usuarioId);
                case 3 -> cerrarCuenta(usuarioId);
                case 0 -> continuar = false;
                default -> ConsolaHelper.imprimirError("Opción no válida");
            }
        }
    }

    private void abrirCuenta(Long usuarioId) {
        ConsolaHelper.imprimirTitulo("ABRIR NUEVA CUENTA");
        System.out.println("  Tipos disponibles:");
        System.out.println("  1. AHORRO    (mín. $500.00)");
        System.out.println("  2. CORRIENTE (mín. $1,000.00)");
        System.out.println("  3. NÓMINA    (mín. $0.00)");

        int opcion = ConsolaHelper.leerEntero("  Tipo de cuenta: ");
        TipoCuenta tipo = switch (opcion) {
            case 1 -> TipoCuenta.AHORRO;
            case 2 -> TipoCuenta.CORRIENTE;
            case 3 -> TipoCuenta.NOMINA;
            default -> null;
        };

        if (tipo == null) {
            ConsolaHelper.imprimirError("Tipo de cuenta no válido");
            ConsolaHelper.presionaEnter();
            return;
        }

        BigDecimal monto = ConsolaHelper.leerMonto("  Monto inicial: $");

        try {
            Cuenta cuenta = cuentaController.abrirCuenta(usuarioId, tipo, monto);
            ConsolaHelper.imprimirExito("Cuenta creada: " + cuenta.getNumeroCuenta());
        } catch (Exception e) {
            ConsolaHelper.imprimirError(e.getMessage());
        }
        ConsolaHelper.presionaEnter();
    }

    private void verCuentas(Long usuarioId) {
        ConsolaHelper.imprimirTitulo("MIS CUENTAS");
        List<Cuenta> cuentas = cuentaController.listarMisCuentas(usuarioId);

        if (cuentas.isEmpty()) {
            System.out.println("  No tienes cuentas activas.");
        } else {
            cuentas.forEach(c -> System.out.printf(
                    "  [%d] %s | %s | Saldo: $%.2f%n",
                    c.getId(), c.getNumeroCuenta(), c.getTipo(), c.getSaldo()
            ));
        }
        ConsolaHelper.presionaEnter();
    }

    private void cerrarCuenta(Long usuarioId) {
        ConsolaHelper.imprimirTitulo("CERRAR CUENTA");
        verCuentas(usuarioId);
        Long cuentaId = (long) ConsolaHelper.leerEntero("  ID de la cuenta a cerrar: ");

        try {
            boolean cerrada = cuentaController.cerrarCuenta(cuentaId, usuarioId);
            if (cerrada) {
                ConsolaHelper.imprimirExito("Cuenta cerrada correctamente");
            }
        } catch (Exception e) {
            ConsolaHelper.imprimirError(e.getMessage());
        }
        ConsolaHelper.presionaEnter();
    }
}
