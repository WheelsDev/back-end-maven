package org.example.Models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.Scanner;

public class Contrato {

    private String identificador;
    private Cliente cliente = null;
    private Bicicleta bicicleta = null;
    private LocalDate dataInicial = LocalDate.now();
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataRetorno;
    private int numeroDias;
    double valorDeposito = 0;
    private double taxaAtraso = 0;
    private double taxaDano = 0;
    private StatusContrato status = StatusContrato.ATIVO;

    public Contrato() {}

    public Contrato(Cliente cliente, Bicicleta bicicleta, LocalDate dataRetorno){
        identificador = gerarIdentificador();
        this.cliente = cliente;
        this.bicicleta = bicicleta;
        this.dataInicial = LocalDate.now();
        this.valorDeposito = bicicleta.getDeposito();
        this.dataRetorno = dataRetorno;
        numeroDias = (int) ChronoUnit.DAYS.between(dataInicial, dataRetorno);
        status = StatusContrato.ATIVO;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setBicicleta(Bicicleta bicicleta) {
        this.bicicleta = bicicleta;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public void setNumeroDias(int numeroDias) {
        this.numeroDias = numeroDias;
    }

    public void setDataRetorno(LocalDate dataRetorno) {
        this.dataRetorno = dataRetorno;

        if (dataInicial != null && this.dataRetorno != null) {
            this.numeroDias = (int) ChronoUnit.DAYS.between(dataInicial, this.dataRetorno);
        }
    }

    public void setTaxaAtraso(double taxaAtraso) {
        this.taxaAtraso = taxaAtraso;
    }

    public void setTaxaDano(double taxaDano) {
        this.taxaDano = taxaDano;
    }

    public void setStatus(StatusContrato status) {
        this.status = status;
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

    public String gerarIdentificador() {
        return "CON-" + new Random().nextInt(10000);
    }

    public void danoCausadoABicicleta() {
        System.out.println("Dano causado à bicicleta.");
        double multa = bicicleta.getDeposito()*15;
        taxaDano = multa;
    }

    public void atrasoAoDevolverBicicleta() {
        double multa = 1.20 * (bicicleta.getDiariaTaxaAluguel() * numeroDias);
        taxaAtraso = multa;
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

