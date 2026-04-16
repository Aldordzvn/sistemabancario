package com.rdzvn.banking.controllers;

import com.rdzvn.banking.model.Usuario;
import com.rdzvn.banking.services.UsuarioService;

import java.util.Optional;

public class AuthController {
    private final UsuarioService usuarioService;

    private Usuario usuarioActual;

    public AuthController(){
        this.usuarioService = new UsuarioService();
    }

    public Usuario registrar(String nombreUsuario, String correo, String password){
        return usuarioService.registrar(nombreUsuario, correo, password);
    }

    public boolean iniciarSesion(String nombreUsuario, String password){
        Optional<Usuario> resultado = usuarioService.iniciarSesion(nombreUsuario, password);
        resultado.ifPresent(u -> this.usuarioActual = u);
        return resultado.isPresent();
    }

    public void cerrarSesion(){
        this.usuarioActual = null;
    }

    public boolean haySesionActiva(){
        return usuarioActual != null;
    }

    public Usuario getUsuarioActual(){
        return usuarioActual;
    }
}
