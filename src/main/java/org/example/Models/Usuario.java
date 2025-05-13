package org.example.Models;

public class Usuario {

    private String Email;
    private String Senha;
    private TipoUsuario tipo;

    public Usuario(String email, String senha, TipoUsuario tipo) {
        Email = email;
        Senha = senha;
        this.tipo = tipo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
}


