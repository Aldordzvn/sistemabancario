package com.rdzvn.banking.views;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsolaHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    public static String leerPassword(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    public static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Por favor ingresa un número entero válido.");
            }
        }
    }

    public static BigDecimal leerMonto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                BigDecimal valor = new BigDecimal(scanner.nextLine().trim());
                if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("  El monto debe ser mayor a cero.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("  Por favor ingresa un monto válido (ej: 1500.00).");
            }
        }
    }

    public static void imprimirLinea() {
        System.out.println("─".repeat(50));
    }

    public static void imprimirTitulo(String titulo) {
        System.out.println();
        imprimirLinea();
        System.out.println("  " + titulo);
        imprimirLinea();
    }

    public static void imprimirExito(String mensaje) {
        System.out.println("  ✓ " + mensaje);
    }

    public static void imprimirError(String mensaje) {
        System.out.println("  ✗ Error: " + mensaje);
    }

    public static void presionaEnter() {
        System.out.print("\n  Presiona Enter para continuar...");
        scanner.nextLine();
    }
}
