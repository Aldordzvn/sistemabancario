package com.rdzvn.banking;

import com.rdzvn.banking.dao.impl.CuentaDaoImpl;
import com.rdzvn.banking.dao.impl.UsuarioDaoImpl;
import com.rdzvn.banking.dao.interfaces.CuentaDAO;
import com.rdzvn.banking.dao.interfaces.UsuarioDAO;
import com.rdzvn.banking.db.ConexionDB;
import com.rdzvn.banking.model.*;
import com.rdzvn.banking.services.CuentaService;
import com.rdzvn.banking.services.TransaccionService;
import com.rdzvn.banking.services.UsuarioService;

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

//        System.out.println("\n=== Prueba Fase 5: Servicios ===\n");
//
//        UsuarioService usuarioService       = new UsuarioService();
//        CuentaService cuentaService         = new CuentaService();
//        TransaccionService transaccionService = new TransaccionService();
//
//        // 1. Registrar usuario con BCrypt
//        Usuario u = usuarioService.registrar("mgarcia", "mgarcia@mail.com", "segura123");
//        System.out.println("Registrado: " + u);
//
//        // 2. Login correcto
//        Optional<Usuario> login = usuarioService.iniciarSesion("mgarcia", "segura123");
//        System.out.println("Login exitoso: " + login.isPresent());
//
//        // 3. Login con contraseña incorrecta
//        Optional<Usuario> loginFallido = usuarioService.iniciarSesion("mgarcia", "incorrecta");
//        System.out.println("Login con clave incorrecta: " + loginFallido.isPresent());
//
//        // 4. Abrir cuentas
//        Cuenta cuentaA = cuentaService.abrir(u.getId(), TipoCuenta.AHORRO, new BigDecimal("1000.00"));
//        Cuenta cuentaB = cuentaService.abrir(u.getId(), TipoCuenta.CORRIENTE, new BigDecimal("2000.00"));
//        System.out.println("Cuenta A: " + cuentaA.getNumeroCuenta() + " saldo: $" + cuentaA.getSaldo());
//        System.out.println("Cuenta B: " + cuentaB.getNumeroCuenta() + " saldo: $" + cuentaB.getSaldo());
//
//        // 5. Depósito
//        transaccionService.depositar(cuentaA.getId(), new BigDecimal("500.00"), "Depósito inicial");
//        System.out.println("Saldo A tras depósito: $" +
//                cuentaService.buscarPorId(cuentaA.getId()).get().getSaldo());
//
//        // 6. Transferencia atómica
//        transaccionService.transferir(cuentaA.getId(), cuentaB.getId(),
//                new BigDecimal("300.00"), "Pago de servicios");
//        System.out.println("Saldo A tras transferencia: $" +
//                cuentaService.buscarPorId(cuentaA.getId()).get().getSaldo());
//        System.out.println("Saldo B tras transferencia: $" +
//                cuentaService.buscarPorId(cuentaB.getId()).get().getSaldo());
//
//        // 7. Historial
//        List<Transaccion> historial = transaccionService.obtenerHistorial(cuentaA.getId());
//        System.out.println("Transacciones de cuenta A: " + historial.size());
//
//        // 8. Validación de negocio: retiro sin saldo suficiente
//        try {
//            transaccionService.retirar(cuentaA.getId(), new BigDecimal("99999.00"), "Retiro imposible");
//        } catch (IllegalArgumentException e) {
//            System.out.println("Error esperado: " + e.getMessage());
//        }
//
//        System.out.println("\n=== Fase 5 completada ===");
    }
}