package com.rdzvn.banking.dao.impl;

import com.rdzvn.banking.dao.interfaces.TransaccionDAO;
import com.rdzvn.banking.db.ConexionDB;
import com.rdzvn.banking.model.TipoTransaccion;
import com.rdzvn.banking.model.Transaccion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransaccionDaoImpl implements TransaccionDAO {
    private final ConexionDB db = ConexionDB.getInstance();

    @Override
    public Transaccion guardar(Transaccion transaccion) {
        String sql = "INSERT INTO transacciones (cuenta_origen_id, cuenta_destino_id, tipo, monto, descripcion) VALUES (?,?,?,?,?)";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (transaccion.getCuentaOrigenId() != null) {
                ps.setLong(1, transaccion.getCuentaOrigenId());
            } else {
                ps.setNull(1, Types.BIGINT);
            }

            if (transaccion.getCuentaDestinoId() != null) {
                ps.setLong(2, transaccion.getCuentaDestinoId());
            } else {
                ps.setNull(2, Types.BIGINT);
            }

            ps.setString(3, transaccion.getTipo().name());
            ps.setBigDecimal(4, transaccion.getMonto());
            ps.setString(5, transaccion.getDescripcion());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) transaccion.setId(keys.getLong(1));
            }
            return transaccion;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar transacción: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Transaccion> buscarPorId(Long id) {
        String sql = "SELECT * FROM transacciones WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapearFilas(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar transacción: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Transaccion> buscarPorCuentaId(Long cuentaId) {
        String sql = " SELECT * FROM transacciones WHERE cuenta_origen_id = ? OR cuenta_destino_id = ? ORDER BY creado_en DESC ";
        List<Transaccion> lista = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, cuentaId);
            ps.setLong(2, cuentaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapearFilas(rs));
            }
            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar transacciones por cuenta: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Transaccion> buscarTodas() {
        String sql = "SELECT * FROM transacciones ORDER BY creado_en DESC";
        List<Transaccion> lista = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapearFilas(rs));
            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar todas las transacciones: " + e.getMessage(), e);
        }
    }

    private Transaccion mapearFilas(ResultSet rs) throws SQLException{
        Transaccion t = new Transaccion();
        t.setId(rs.getLong("id"));

        long origen = rs.getLong("cuenta_origen_id");
        t.setCuentaOrigenId(rs.wasNull() ? null : origen);

        long destino = rs.getLong("cuenta_destino_id");
        t.setCuentaDestinoId(rs.wasNull() ? null : destino);

        t.setTipo(TipoTransaccion.valueOf(rs.getString("tipo")));
        t.setMonto(rs.getBigDecimal("monto"));
        t.setDescripcion(rs.getString("descripcion"));
        t.setCreadoEn(rs.getTimestamp("creado_en").toLocalDateTime());
        return t;
    }
}
