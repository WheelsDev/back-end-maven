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

    /*public static void criarTabelaBicicletas() {
        BicicletaDAO bicicletaDAO = new BicicletaDAO();
        bicicletaDAO.inserir(new Bicicleta("Aron 20", "Caloi", "7", 20, "Esportiva", 10, true));
    }*/

    /*public static void criarTabelaCliente() {
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.inserir(new Cliente("Guilherme Pirozi", "Vila da Penha", "21 99949-2998", "guilherme.pirozi@al.infnet.edu.br"));
    }*/
}