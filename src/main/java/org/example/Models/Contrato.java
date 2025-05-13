package org.example.Models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class Contrato {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    Scanner leitor = new Scanner(System.in);
    private int contratoID = 0;
    private Cliente cliente = null;
    private Bicicleta bicicleta = null;
    private LocalDate dataInicial = null;
    private int numeroDias = 0;
    private LocalDate dataRetorno = null;
    private double descontoAtraso = 0;
    private double descontoDano = 0;
    private StatusContrato status;

    public Contrato(Cliente cliente, Bicicleta bicicleta, LocalDate dataParaAlugar, int numDiasParaAlugar){
        contratoID = new Random().nextInt(1,10000);
        this.cliente = cliente;
        this.bicicleta = bicicleta;
        dataInicial = dataParaAlugar;
        numeroDias = numDiasParaAlugar;
        dataRetorno = dataParaAlugar.plusDays(numDiasParaAlugar);
        status = StatusContrato.ATIVO;
    }

    private String nomeCliente;
    private String nomeBicicleta;
    private String dataAluguelCSV;
    private String dataRetornoCSV;
    private String statusContrato;
    public Contrato(int id, String cliente, String bicicleta,String dataParaAlugar, int numDiasAluguel,String dataRetorno,String statusContrato){
        contratoID = id;
        this.nomeCliente = cliente;
        this.nomeBicicleta = bicicleta;
        dataAluguelCSV = dataParaAlugar;
        numeroDias = numDiasAluguel;
        dataRetornoCSV = dataRetorno;
        this.statusContrato = statusContrato;
    }

    public int getContratoID() {
        return contratoID;
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

    public double getDescontoAtraso() {
        return descontoAtraso;
    }

    public double getDescontoDano() {
        return descontoDano;
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

    public void danoCausadoABicicleta() {
        System.out.println("Dano causado à bicicleta.");
        double desconto = bicicleta.deposito*15;
        descontoDano = desconto;
    }

    public void atrasoAoDevolverBicicleta() {
        System.out.println("Dias alugados após os dias de contrato: ");
        int dias = leitor.nextInt();
        double desconto = 1.20 * (bicicleta.diariaTaxaAluguel * dias);
        descontoAtraso = desconto;
    }

    public void finalizacaoDoContrato() {
        status = StatusContrato.FINALIZADO;
    }

    public void exibirDetalhes() {
        System.out.println("Identificador do Contrato: " + contratoID);
        System.out.println("Cliente: " + nomeCliente);
        System.out.println("Bicicleta: " + nomeBicicleta);
        System.out.println("Data do aluguel: " + dataAluguelCSV);
        System.out.println("Números de dias de aluguel: " + numeroDias);
        System.out.println("Data de retorno da bicicleta: " + dataRetornoCSV);
        System.out.println("Status do Contrato: " + statusContrato);
    }

    @Override
    public String toString() {
        return "\n" + contratoID + "," + cliente.getNome() + "," + bicicleta.getNome() + "," + dataInicial.format(formatter) + "," + numeroDias + "," + dataRetorno.format(formatter)+","+status.toString();
    }
}

