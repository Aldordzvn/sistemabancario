package com.rdzvn.banking.db;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static ConexionDB instancia;

    private final String url;
    private final String user;
    private final String password;

    private ConexionDB(){
        Dotenv dotenv = Dotenv.load();
        this.url = dotenv.get("DB_URL");
        this.user = dotenv.get("DB_USER");
        this.password = dotenv.get("DB_PASSWORD");
    }

    public static synchronized ConexionDB getInstance(){
        if(instancia == null){
            instancia = new ConexionDB();
        }
        return instancia;
    }

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }
}
