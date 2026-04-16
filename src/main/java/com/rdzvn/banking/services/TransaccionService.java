package com.rdzvn.banking.services;

import com.rdzvn.banking.dao.impl.CuentaDaoImpl;
import com.rdzvn.banking.dao.impl.TransaccionDaoImpl;
import com.rdzvn.banking.dao.interfaces.CuentaDAO;
import com.rdzvn.banking.dao.interfaces.TransaccionDAO;
import com.rdzvn.banking.db.ConexionDB;
import com.rdzvn.banking.model.Cuenta;
import com.rdzvn.banking.model.TipoTransaccion;
import com.rdzvn.banking.model.Transaccion;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TransaccionService {
    private final CuentaDAO cuentaDAO;
    private final TransaccionDAO transaccionDAO;
    private final ConexionDB db;

    public TransaccionService(){
        this.cuentaDAO = new CuentaDaoImpl();
        this.transaccionDAO = new TransaccionDaoImpl();
        this.db = ConexionDB.getInstance();
    }

    public Transaccion depositar(Long cuentaId, BigDecimal monto, String descripcion){
        if(monto.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("El monto del depósito debe ser mayor a cero");
        }

        Cuenta cuenta = cuentaDAO.buscarPorId(cuentaId)
                .orElseThrow(()-> new IllegalArgumentException("Cuenta no encontrada"));

        cuenta.setSaldo(cuenta.getSaldo().add(monto));
        cuentaDAO.actualizar(cuenta);

        Transaccion tx = new Transaccion(null, cuentaId, TipoTransaccion.DEPOSITO, monto, descripcion);
        return transaccionDAO.guardar(tx);
    }

    public Transaccion retirar(Long cuentaId, BigDecimal monto, String descripcion){
        if(monto.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("El monto del retiro debe ser mayor a cero");
        }

        Cuenta cuenta = cuentaDAO.buscarPorId(cuentaId)
                .orElseThrow(()-> new IllegalArgumentException("Cuenta no encontrada"));

        if(cuenta.getSaldo().compareTo(monto) < 0){
            throw new IllegalArgumentException("Saldo insuficiente. Disponible: $" + cuenta.getSaldo());
        }

        cuenta.setSaldo(cuenta.getSaldo().subtract(monto));
        cuentaDAO.actualizar(cuenta);

        Transaccion tx = new Transaccion(cuentaId, null, TipoTransaccion.RETIRO, monto, descripcion);
        return transaccionDAO.guardar(tx);
    }

    public Transaccion transferir(Long cuentaOrigenId, Long cuentaDestinoId, BigDecimal monto, String descripcion){
        if(monto.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        if(cuentaOrigenId.equals(cuentaDestinoId)){
            throw new IllegalArgumentException("No puedes transferir a la misma cuenta");
        }

        Cuenta origen = cuentaDAO.buscarPorId(cuentaOrigenId).orElseThrow(()-> new IllegalArgumentException("Cuenta origen no encontrada"));

        Cuenta destino = cuentaDAO.buscarPorId(cuentaDestinoId).orElseThrow(()-> new IllegalArgumentException("Cuenta destino no encontrada"));

        if(origen.getSaldo().compareTo(monto) < 0){
            throw new IllegalArgumentException("Saldo insuficiente. Disponible: $" + origen.getSaldo());
        }

        try(Connection conn = db.getConnection()){
            conn.setAutoCommit(false);

            try{
                origen.setSaldo(origen.getSaldo().subtract(monto));
                actualizarCuentaConConexion(conn, origen);

                destino.setSaldo(destino.getSaldo().add(monto));
                actualizarCuentaConConexion(conn, destino);

                Transaccion tx = new Transaccion(cuentaOrigenId, cuentaDestinoId, TipoTransaccion.TRANSFERENCIA, monto, descripcion);
                guardarTransaccionConConexion(conn,tx);

                conn.commit();
                return tx;
            }catch (Exception e){
                conn.rollback();
                throw new RuntimeException("Transferencia fallida, se revirtio: " + e.getMessage(), e);
            }
        }catch (SQLException e){
            throw new RuntimeException("Error de conexión en transferencia: " + e.getMessage());
        }
    }

    public List<Transaccion> obtenerHistorial(Long cuentaId){
        return transaccionDAO.buscarPorCuentaId(cuentaId);
    }

    // Métodos auxiliares para ejecutar SQL dentro de una conexión externa
    // Necesarios para que la transferencia use UNA SOLA conexión transaccional
    private void actualizarCuentaConConexion(Connection conn, Cuenta cuenta) throws SQLException {
        String sql = "UPDATE cuentas SET saldo = ? WHERE id = ?";
        try(var ps = conn.prepareStatement(sql)){
            ps.setBigDecimal(1, cuenta.getSaldo());
            ps.setLong(2, cuenta.getId());
            ps.executeUpdate();
        }
    }

    private void guardarTransaccionConConexion(Connection conn, Transaccion tx) throws SQLException{
        String sql = "INSERT INTO transacciones (cuenta_origen_id, cuenta_destino_id, tipo, monto, descripcion) VALUES (?,?,?,?,?)";

        try(var ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setLong(1, tx.getCuentaOrigenId());
            ps.setLong(2, tx.getCuentaDestinoId());
            ps.setString(3, tx.getTipo().name());
            ps.setBigDecimal(4, tx.getMonto());
            ps.setString(5, tx.getDescripcion());
            ps.executeUpdate();

            try(var keys = ps.getGeneratedKeys()){
                if(keys.next()) tx.setId(keys.getLong(1));
            }
        }
    }
}
