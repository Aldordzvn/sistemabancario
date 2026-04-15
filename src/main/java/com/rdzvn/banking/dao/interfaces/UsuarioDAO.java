package com.rdzvn.banking.dao.interfaces;

import com.rdzvn.banking.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {
    Usuario guardar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario);
    Optional<Usuario> buscarPorCorreo(String correo);
    List<Usuario> buscarTodo();
    Usuario actualizar(Usuario usuario);
    boolean desactivar(Long id);
}
