package org.example;

import org.example.DataAccessObject.UsuarioDAO;
import org.example.Models.*;
import org.example.Util.GerarEmail;
import org.example.Util.GerarPDF;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        //inicializarUsuarios();
    }

    /*public static void inicializarUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.inserir(new Usuario("admin@wheels.com", "admin123", TipoUsuario.ADMIN));
        usuarioDAO.inserir(new Usuario("recep@wheels.com", "recep123", TipoUsuario.RECEPCIONISTA));
    }*/
}