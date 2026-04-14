package com.rdzvn.banking;

import com.rdzvn.banking.db.ConexionDB;

import java.sql.Connection;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Prueba fase 1: Conexión singleton");

        ConexionDB instancia1 = ConexionDB.getInstance();
        ConexionDB instancia2 = ConexionDB.getInstance();

        System.out.println("¿Son la misma instancia?: " + (instancia1 == instancia2));

        try(Connection conn = ConexionDB.getInstance().getConnection()){
            System.out.println("Conexión exitosa " + !conn.isClosed() );
            System.out.println("Base de datos: " + conn.getCatalog());
        }catch (SQLException e){
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }

        System.out.println("==================== Fase 1 COMPLETADA ====================");


    }
}