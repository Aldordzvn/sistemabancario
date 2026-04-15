package com.rdzvn.banking.dao.impl;

import com.rdzvn.banking.dao.interfaces.CuentaDAO;
import com.rdzvn.banking.db.ConexionDB;
import com.rdzvn.banking.model.Cuenta;
import com.rdzvn.banking.model.EstadoCuenta;
import com.rdzvn.banking.model.TipoCuenta;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CuentaDaoImpl implements CuentaDAO {

    private final ConexionDB db = ConexionDB.getInstance();

    @Override
    public Cuenta guardar(Cuenta cuenta) {
        String sql = "INSERT INTO cuentas (usuario_id, numero_cuenta, tipo, saldo, estado) VALUES (?,?,?,?,?)";

        try(Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){
            ps.setLong(1, cuenta.getUsuarioId());
            ps.setString(2, cuenta.getNumeroCuenta());
            ps.setString(3, cuenta.getTipo().name());
            ps.setBigDecimal(4, cuenta.getSaldo());
            ps.setString(5, cuenta.getEstado().name());
            ps.executeUpdate();
            try(ResultSet keys = ps.getGeneratedKeys()){
                if(keys.next()){
                    cuenta.setId(keys.getLong(1));
                }
            }
            return cuenta;
        }catch (SQLException e){
            throw new RuntimeException("Error al guardar cuenta: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Cuenta> buscarPorId(Long id) {
        String sql = "SELECT * FROM cuentas WHERE id = ? AND estado = 'ACTIVA'";

        try(Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.of(mapearFilas(rs));
            }
            return Optional.empty();
        }catch (SQLException e){
            throw new RuntimeException("Error al buscar cuenta por id: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Cuenta> buscarPorNumeroCuenta(String numeroCuenta) {
        String sql = "SELECT * FROM cuentas WHERE numero_cuenta = ? AND estado = 'ACTIVA'";

        try(Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setString(1, numeroCuenta);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.of(mapearFilas(rs));
            }
            return Optional.empty();
        }catch (SQLException e){
            throw new RuntimeException("Error al buscar cuenta por número: " + e.getMessage());
        }
    }

    @Override
    public List<Cuenta> buscarPorUsuarioId(Long usuarioId) {
        String sql = "SELECT * FROM cuentas WHERE usuario_id = ? AND estado = 'ACTIVA'";
        List<Cuenta> lista = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapearFilas(rs));
            }
            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cuentas por usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Cuenta> buscarTodas() {
        String sql = "SELECT * FROM cuentas WHERE estado = 'ACTIVA'";
        List<Cuenta> lista = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapearFilas(rs));
            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar todas las cuentas: " + e.getMessage(), e);
        }
    }

    @Override
    public Cuenta actualizar(Cuenta cuenta) {
        String sql = "UPDATE cuentas SET saldo = ?, estado = ? WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, cuenta.getSaldo());
            ps.setString(2, cuenta.getEstado().name());
            ps.setLong(3, cuenta.getId());
            ps.executeUpdate();
            return cuenta;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar cuenta: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean desactivar(Long id) {
        String sql = "UPDATE cuentas SET estado = 'INACTIVA' WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al desactivar cuenta: " + e.getMessage(), e);
        }
    }

    private Cuenta mapearFilas(ResultSet rs) throws SQLException{
        Cuenta c = new Cuenta();
        c.setId(rs.getLong("id"));
        c.setUsuarioId(rs.getLong("usuario_id"));
        c.setNumeroCuenta(rs.getString("numero_cuenta"));
        c.setTipo(TipoCuenta.valueOf(rs.getString("tipo")));
        c.setSaldo(rs.getBigDecimal("saldo"));
        c.setEstado(EstadoCuenta.valueOf(rs.getString("estado")));
        c.setCreadoEn(rs.getTimestamp("creado_en").toLocalDateTime());
        c.setActualizadoEn(rs.getTimestamp("actualizado_en").toLocalDateTime());
        return c;
    }
}
