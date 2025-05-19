package org.example.Controller;

import org.example.Models.*;
import org.example.Util.GerarPDF;
import org.example.Util.GerarEmail;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        inicializarUsuarios();
    }

    public static void inicializarUsuarios() {
//        UsuarioDAO usuarioDAO = new UsuarioDAO();
//
//        usuarioDAO.inserir(new Usuario("admin@wheels.com", "admin123", TipoUsuario.ADMIN));
//        usuarioDAO.inserir(new Usuario("recep@wheels.com", "recep123", TipoUsuario.RECEPCIONISTA));

        //(String nome, String marca, String modelo, double deposito, String tipo, double diariaTaxaAluguel , boolean disponibilidade)
        Cliente novoCliente = new Cliente("Richard","Rua oduvaldu Cozzi","21998228014","richard.alves@al.infnet.edu.br");
        Bicicleta novaBicicleta = new Bicicleta("Bicicleta Anos 2000","Volkswagem","Grande",15,"Esportiva",5,true);
        Contrato novoContrato = new Contrato(novoCliente,novaBicicleta, LocalDate.now(),10);
        GerarPDF gerarPDF = new GerarPDF();
//        GerarEmail email = new GerarEmail();
//        gerarPDF.gerarContratoAluguel(novoContrato);
//        if (novoCliente.getEmail() != null) {
//            email.enviarContratoDeAluguel(novoCliente, novoContrato);
//        }
        gerarPDF.gerarComprovantePagamento(novoContrato);

    }
}