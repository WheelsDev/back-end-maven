package org.example.Models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Pagamento {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Contrato contrato;
    private double valorTotal = 0;
    private double valorPago = 0;
    private LocalDate dataPagamento = LocalDate.now();
    private double pagamentoEmFalta = 0;
    private StatusPagamento status;
    private String contratoId;
    private String clienteNome;

    public String getContratoId() {
        return contratoId;
    }

    public void setContratoId(String contratoId) {
        this.contratoId = contratoId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public double getPagamentoEmFalta() {
        return pagamentoEmFalta;
    }

    public void setPagamentoEmFalta(double pagamentoEmFalta) {
        this.pagamentoEmFalta = pagamentoEmFalta;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public Pagamento() {}

    public Pagamento(Contrato contrato){
        this.contrato = contrato;
        this.valorTotal = contrato.getNumeroDias() * contrato.getBicicleta().getDiariaTaxaAluguel() + contrato.getBicicleta().getDeposito();
        this.valorPago = pagarAluguel();
        this.pagamentoEmFalta = valorTotal - valorPago;
        this.dataPagamento = LocalDate.now();
        status = StatusPagamento.PENDENTE;
    }

    private double pagarAluguel() {
        System.out.print("Coloque o valor do pagamento: ");
        return 100;
    }

    public void calcularPagamentoTotal(Contrato contrato){
        emitirRecibo(contrato);
    }

    private void emitirRecibo(Contrato contrato){

        String cliente = contrato.getCliente().getNome();
        String endereco = contrato.getCliente().getEndereco();
        System.out.println("Imprimindo comprovante para: '" + cliente + "' ......");
        System.out.println("Endereço: " + endereco + "\n");

        System.out.println("Alugando a bicicleta: '" + contrato.getBicicleta().getNome() + "' para " + contrato.getNumeroDias() + " dias" + "\n");
        contrato.getBicicleta().calcularCusto(contrato.getNumeroDias());
    }

    public void exibirDetalhes() {
        System.out.println("Nome do Cliente: " + contrato.getCliente().getNome());
        System.out.println("Data do Pagamento: " + dataPagamento.format(formatter));
        System.out.println("Valor Total: R$ " + valorTotal);
        System.out.println("Valor pago pelo cliente: R$ " + valorPago);
        if (status.equals(StatusPagamento.PAGO)) {
            System.out.println("PAGO");
        } else System.out.println("Pagamento em falta: R$ " + pagamentoEmFalta);
    }
}
