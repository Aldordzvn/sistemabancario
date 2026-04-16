package com.rdzvn.banking.views;

import com.rdzvn.banking.controllers.AuthController;
import com.rdzvn.banking.controllers.CuentaController;
import com.rdzvn.banking.controllers.TransaccionController;

public class MainMenuView {
    private final AuthController authController;
    private final AccountView accountView;
    private final TransaccionView transactionView;

    public MainMenuView(AuthController authController,
                        CuentaController cuentaController,
                        TransaccionController transaccionController) {
        this.authController  = authController;
        this.accountView     = new AccountView(cuentaController);
        this.transactionView = new TransaccionView(transaccionController, cuentaController);
    }

    public void mostrar() {
        boolean continuar = true;
        while (continuar) {
            String usuario = authController.getUsuarioActual().getNombreUsuario();
            ConsolaHelper.imprimirTitulo("MENÚ PRINCIPAL — " + usuario.toUpperCase());
            System.out.println("  1. Mis cuentas");
            System.out.println("  2. Operaciones");
            System.out.println("  3. Cerrar sesión");
            System.out.println("  0. Salir");
            ConsolaHelper.imprimirLinea();

            int opcion = ConsolaHelper.leerEntero("  Opción: ");
            Long usuarioId = authController.getUsuarioActual().getId();

            switch (opcion) {
                case 1 -> accountView.mostrarMenu(usuarioId);
                case 2 -> transactionView.mostrarMenu(usuarioId);
                case 3 -> {
                    authController.cerrarSesion();
                    continuar = false;
                }
                case 0 -> {
                    System.out.println("\n  Hasta luego.");
                    System.exit(0);
                }
                default -> ConsolaHelper.imprimirError("Opción no válida");
            }
        }
    }
}
