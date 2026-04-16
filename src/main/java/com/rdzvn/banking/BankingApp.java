package com.rdzvn.banking;

import com.rdzvn.banking.controllers.AuthController;
import com.rdzvn.banking.controllers.CuentaController;
import com.rdzvn.banking.controllers.TransaccionController;
import com.rdzvn.banking.views.AuthView;
import com.rdzvn.banking.views.MainMenuView;

public class BankingApp {
    public static void main(String[] args) {
        // Shutdown hook: se ejecuta aunque el usuario presione Ctrl+C
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n\n  Sistema bancario cerrado. ¡Hasta luego!");
        }));

        // Ensamblar controladores
        AuthController authController           = new AuthController();
        CuentaController cuentaController       = new CuentaController();
        TransaccionController txController      = new TransaccionController();

        // Ensamblar vistas
        AuthView authView       = new AuthView(authController);
        MainMenuView mainMenu   = new MainMenuView(authController, cuentaController, txController);

        // Bucle principal: auth → menú → auth → menú...
        while (true) {
            authView.mostrarMenuAuth();   // bloquea hasta que haya sesión activa
            mainMenu.mostrar();           // bloquea hasta cerrar sesión
        }
    }
}
