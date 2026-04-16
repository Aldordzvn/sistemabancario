package com.rdzvn.banking.views;

import com.rdzvn.banking.controllers.AuthController;
import com.rdzvn.banking.model.Usuario;

public class AuthView {
    private final AuthController authController;

    public AuthView(AuthController authController) {
        this.authController = authController;
    }

    public void mostrarMenuAuth() {
        while (!authController.haySesionActiva()) {
            ConsolaHelper.imprimirTitulo("SISTEMA BANCARIO");
            System.out.println("  1. Iniciar sesión");
            System.out.println("  2. Registrarse");
            System.out.println("  0. Salir");
            ConsolaHelper.imprimirLinea();

            int opcion = ConsolaHelper.leerEntero("  Opción: ");

            switch (opcion) {
                case 1 -> iniciarSesion();
                case 2 -> registrarse();
                case 0 -> {
                    System.out.println("\n  Hasta luego.");
                    System.exit(0);
                }
                default -> ConsolaHelper.imprimirError("Opción no válida");
            }
        }
    }

    private void iniciarSesion() {
        ConsolaHelper.imprimirTitulo("INICIAR SESIÓN");
        String usuario = ConsolaHelper.leerTexto("  Usuario: ");
        String password = ConsolaHelper.leerPassword("  Contraseña: ");

        try {
            boolean exito = authController.iniciarSesion(usuario, password);
            if (exito) {
                ConsolaHelper.imprimirExito("Bienvenido, " +
                        authController.getUsuarioActual().getNombreUsuario());
            } else {
                ConsolaHelper.imprimirError("Usuario o contraseña incorrectos");
            }
        } catch (Exception e) {
            ConsolaHelper.imprimirError(e.getMessage());
        }
        ConsolaHelper.presionaEnter();
    }

    private void registrarse() {
        ConsolaHelper.imprimirTitulo("CREAR CUENTA DE USUARIO");
        String usuario  = ConsolaHelper.leerTexto("  Nombre de usuario: ");
        String correo   = ConsolaHelper.leerTexto("  Correo electrónico: ");
        String password = ConsolaHelper.leerPassword("  Contraseña (mín. 6 caracteres): ");

        try {
            Usuario nuevo = authController.registrar(usuario, correo, password);
            ConsolaHelper.imprimirExito("Usuario creado con id: " + nuevo.getId());
        } catch (Exception e) {
            ConsolaHelper.imprimirError(e.getMessage());
        }
        ConsolaHelper.presionaEnter();
    }
}
