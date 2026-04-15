package com.rdzvn.banking.dao.impl;

import com.rdzvn.banking.dao.interfaces.UsuarioDAO;
import com.rdzvn.banking.db.ConexionDB;
import com.rdzvn.banking.model.Usuario;

import javax.swing.text.html.Option;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDaoImpl implements UsuarioDAO {

    private final ConexionDB db = ConexionDB.getInstance();

    @Override
    public Usuario guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios(nombre_usuario, correo, password_hash, esta_activo) VALUES (?,?,?,?)";

        try(Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getPasswordHash());
            ps.setBoolean(4, usuario.isActive());
            ps.executeUpdate();

            try(ResultSet keys = ps.getGeneratedKeys()){
                if(keys.next()){
                    usuario.setId(keys.getLong(1));
                }
            }
            return usuario;
        }catch (SQLException e){
            throw new RuntimeException("Error al guardar el usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        String sql = "SELECT * FROM usuarios WHERE id = ? AND esta_activo = true";

        try(Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(mapearFila(rs));
                }
            }
            return Optional.empty();
        }catch (SQLException e){
            throw new RuntimeException("Error al buscar usuario por id: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
        try(Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setString(1, nombreUsuario);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(mapearFila(rs));
                }
            }
            return Optional.empty();
        }catch (SQLException e){
            throw new RuntimeException("Error al buscar por nombre de usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        String sql = "SELECT * FROM usuarios WHERE correo = ?";
        try(Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setString(1, correo);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return Optional.of(mapearFila(rs));
                }
            }
            return Optional.empty();
        }catch (SQLException e){
            throw new RuntimeException("Error el buscar por correo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Usuario> buscarTodo() {
        String sql = "SELECT * FROM usuarios WHERE esta_activo = true";
        List<Usuario> lista = new ArrayList<>();

        try(Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ){
            while(rs.next()){
                lista.add(mapearFila(rs));
            }
            return lista;
        }catch (SQLException e){
            throw new RuntimeException("Error al buscar todos los usuarios: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre_usuario = ?, correo = ?, password_hash = ? WHERE id = ? AND esta_activo = true";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getPasswordHash());
            ps.setLong(4, usuario.getId());
            ps.executeUpdate();
            return usuario;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean desactivar(Long id) {
        String sql = "UPDATE usuarios SET esta_activo = false WHERE id = ?";

        try(Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            throw new RuntimeException("Error al desactivar usuario: " + e.getMessage(), e);
        }
    }

    private Usuario mapearFila(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNombreUsuario(rs.getString("nombre_usuario"));
        usuario.setCorreo(rs.getString("correo"));
        usuario.setPasswordHash(rs.getString("password_hash"));
        usuario.setActive(rs.getBoolean("esta_activo"));
        usuario.setCreadoEn(rs.getTimestamp("creado_en").toLocalDateTime());
        usuario.setActualizadoEn(rs.getTimestamp("actualizado_en").toLocalDateTime());
        return usuario;
    }
}
