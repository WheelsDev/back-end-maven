package org.example.Models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class Contrato {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    Scanner leitor = new Scanner(System.in);
    private String identificador;
    private Cliente cliente = null;
    private Bicicleta bicicleta = null;
    private LocalDate dataInicial = null;
    private int numeroDias = 0;
    private LocalDate dataRetorno = null;
    private double taxaAtraso = 0;
    private double taxaDano = 0;
    private StatusContrato status;

    public Contrato(Cliente cliente, Bicicleta bicicleta, LocalDate dataParaAlugar, int numDiasParaAlugar){
        identificador = gerarIdentificador();
        this.cliente = cliente;
        this.bicicleta = bicicleta;
        dataInicial = dataParaAlugar;
        numeroDias = numDiasParaAlugar;
        dataRetorno = dataParaAlugar.plusDays(numDiasParaAlugar);
        status = StatusContrato.ATIVO;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Bicicleta getBicicleta() {
        return bicicleta;
    }

    public int getNumeroDias() {
        return numeroDias;
    }

    public double getTaxaAtraso() {
        return taxaAtraso;
    }

    public double getTaxaDano() {
        return taxaDano;
    }

    public StatusContrato getStatus() {
        return status;
    }

    public LocalDate getDataRetorno() {
        return dataRetorno;
    }

    public LocalDate getDataInicial(){
        return dataInicial;
    }

    public String getIdentificador() {
        return identificador;
    }

    private String gerarIdentificador() {
        return "CON-" + new Random().nextInt(10000);
    }

    public void danoCausadoABicicleta() {
        System.out.println("Dano causado à bicicleta.");
        double desconto = bicicleta.getDeposito()*15;
        taxaDano = desconto;
    }

    public void atrasoAoDevolverBicicleta() {
        System.out.println("Dias alugados após os dias de contrato: ");
        int dias = leitor.nextInt();
        double desconto = 1.20 * (bicicleta.getDiariaTaxaAluguel() * dias);
        taxaAtraso = desconto;
    }

    public void finalizacaoDoContrato() {
        status = StatusContrato.FINALIZADO;
    }

    public void exibirDetalhes() {
        System.out.println("DETALHES DO CONTRATO");
        System.out.println("\nIdentificador: " + getIdentificador());
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Bicicleta: " + bicicleta.getNome());
        System.out.println("Data do aluguel: " + dataInicial);
        System.out.println("Números de dias de aluguel: " + numeroDias);
        System.out.println("Data de retorno da bicicleta: " + dataRetorno);
        System.out.println("Status do Contrato: " + status);
    }
}

