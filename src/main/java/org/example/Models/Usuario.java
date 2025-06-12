package org.example.Models;

public class Usuario {

    private String email;
    private String senha;
    private TipoUsuario tipo;

    public Usuario(String email, String senha, TipoUsuario tipo) {
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }
}


