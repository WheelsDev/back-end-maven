package org.example;

import org.example.DataAccessObject.PagamentoDAO;
import org.example.DataAccessObject.UsuarioDAO;
import org.example.Models.Bicicleta;
import org.example.DataAccessObject.BicicletaDAO;
import org.example.Models.TipoUsuario;
import org.example.Models.Usuario;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        inicializarUsuarios();
        criarTabelaBicicletas();
    }

    public static void inicializarUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.inserir(new Usuario("admin@wheels.com", "admin123", TipoUsuario.ADMIN));
        usuarioDAO.inserir(new Usuario("recep@wheels.com", "recep123", TipoUsuario.RECEPCIONISTA));
    }

    public static void criarTabelaBicicletas() {
        BicicletaDAO bicicletaDAO = new BicicletaDAO();
        bicicletaDAO.inserir(new Bicicleta("Aron 20", "Caloi", "7", 20, "Esportiva", 10, true));
    }
}