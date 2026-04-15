package com.rdzvn.banking;

import com.rdzvn.banking.dao.impl.CuentaDaoImpl;
import com.rdzvn.banking.dao.impl.UsuarioDaoImpl;
import com.rdzvn.banking.dao.interfaces.CuentaDAO;
import com.rdzvn.banking.dao.interfaces.UsuarioDAO;
import com.rdzvn.banking.db.ConexionDB;
import com.rdzvn.banking.model.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        System.out.println("Prueba fase 1: Conexión singleton");
//
//        ConexionDB instancia1 = ConexionDB.getInstance();
//        ConexionDB instancia2 = ConexionDB.getInstance();
//
//        System.out.println("¿Son la misma instancia?: " + (instancia1 == instancia2));
//
//        try(Connection conn = ConexionDB.getInstance().getConnection()){
//            System.out.println("Conexión exitosa " + !conn.isClosed() );
//            System.out.println("Base de datos: " + conn.getCatalog());
//        }catch (SQLException e){
//            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
//        }
//
//        System.out.println("==================== Fase 1 COMPLETADA ====================");

//        System.out.println("Prueba fase 2: Modelos y Enums");
//
//        for (TipoCuenta tipo: TipoCuenta.values()){
//            System.out.println("Tipo: " + tipo + "-> minimo apertura: $" + tipo.montoMinimoApertura());
//        }
//
//        Usuario usuario = new Usuario("Aldo", "aldo@gmail.com", "has_temporal");
//        System.out.println("\n" + usuario);
//
//        Cuenta cuenta = new Cuenta(1L, "AH-0001", TipoCuenta.AHORRO, new BigDecimal("1500.00"));
//        System.out.println(cuenta);
//
//        Transaccion tx = new Transaccion(1L, 2L, TipoTransaccion.TRANSFERENCIA, new BigDecimal("200.00"), "Pago de servicios");
//        System.out.println(tx);
//
//        System.out.println("\n==================== Fase 2 COMPLETADA ====================");

//        // === Prueba Fase 4: Implementaciones DAO ===
//        System.out.println("\n=== Prueba Fase 4: Implementaciones DAO ===\n");
//
//        UsuarioDAO usuarioDAO = new UsuarioDaoImpl();
//        CuentaDAO cuentaDAO   = new CuentaDaoImpl();
//
//// 1. Guardar usuario
//        Usuario nuevo = new Usuario("jperez", "jperez@mail.com", "hash_temporal");
//        nuevo = usuarioDAO.guardar(nuevo);
//        System.out.println("Usuario guardado con id: " + nuevo.getId());
//
//// 2. Buscar por nombre de usuario
//        Optional<Usuario> encontrado = usuarioDAO.buscarPorNombreUsuario("jperez");
//        encontrado.ifPresent(u -> System.out.println("Encontrado: " + u));
//
//// 3. Guardar cuenta
//        Cuenta cuenta = new Cuenta(nuevo.getId(), "AH-00001",
//                TipoCuenta.AHORRO, new BigDecimal("1500.00"));
//        cuenta = cuentaDAO.guardar(cuenta);
//        System.out.println("Cuenta guardada con id: " + cuenta.getId());
//
//// 4. Buscar cuentas del usuario
//        List<Cuenta> cuentas = cuentaDAO.buscarPorUsuarioId(nuevo.getId());
//        System.out.println("Cuentas del usuario: " + cuentas.size());
//
//// 5. Soft delete
//        boolean desactivado = usuarioDAO.desactivar(nuevo.getId());
//        System.out.println("Usuario desactivado: " + desactivado);
//
//// 6. Verificar que ya no aparece en búsqueda normal
//        Optional<Usuario> despues = usuarioDAO.buscarPorId(nuevo.getId());
//        System.out.println("¿Aparece después del soft delete? " + despues.isPresent());
//
//        System.out.println("\n=== Fase 4 completada ===");
    }
}