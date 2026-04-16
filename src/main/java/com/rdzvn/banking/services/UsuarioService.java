package com.rdzvn.banking.services;

import com.rdzvn.banking.dao.impl.UsuarioDaoImpl;
import com.rdzvn.banking.dao.interfaces.UsuarioDAO;
import com.rdzvn.banking.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class UsuarioService {

    private final UsuarioDAO usuarioDao;

    public UsuarioService(){
        this.usuarioDao = new UsuarioDaoImpl();
    }

    public Usuario registrar(String nombreUsuario, String correo, String password){
        if(nombreUsuario == null || nombreUsuario.isBlank()){
            throw new IllegalArgumentException("El nombre de usuario no puede estar en blanco");
        }
        if(correo == null || !correo.contains("@")){
            throw new IllegalArgumentException("El correo no es valido");
        }
        if(password == null || password.length() < 6){
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }

        if(usuarioDao.buscarPorNombreUsuario(nombreUsuario).isPresent()){
            throw new IllegalArgumentException("El nombre de usuario ya esta en uso");
        }
        if(usuarioDao.buscarPorCorreo(correo).isPresent()){
            throw new IllegalArgumentException("El correo ya esta registrado");
        }

        String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        Usuario usuario = new Usuario(nombreUsuario, correo, hash);
        return usuarioDao.guardar(usuario);
    }

    public Optional<Usuario> iniciarSesion(String nombreUsuario, String password){
        Optional<Usuario> opt = usuarioDao.buscarPorNombreUsuario(nombreUsuario);

        if(opt.isEmpty()) return Optional.empty();
        Usuario usuario = opt.get();

        if(!usuario.isActive()) return Optional.empty();

        if(!BCrypt.checkpw(password, usuario.getPasswordHash())){
            return Optional.empty();
        }

        return Optional.of(usuario);
    }

    public Optional<Usuario> buscarPorId(Long id){
        return usuarioDao.buscarPorId(id);
    }
}
