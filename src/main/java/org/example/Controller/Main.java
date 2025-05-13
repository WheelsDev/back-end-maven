package org.example.Controller;

import org.example.DataAccessObject.UsuarioDAO;
import org.example.Models.Usuario;
import org.example.Models.TipoUsuario;

public class Main {
    public static void main(String[] args) {
        inicializarUsuarios();
    }

    public static void inicializarUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        usuarioDAO.inserir(new Usuario("admin@wheels.com", "admin123", TipoUsuario.ADMIN));
        usuarioDAO.inserir(new Usuario("recep@wheels.com", "recep123", TipoUsuario.RECEPCIONISTA));
    }
}