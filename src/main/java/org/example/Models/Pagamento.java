package org.example.Models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pagamento {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Contrato contrato;
    private double valorTotal;
    private double valorPago;
    private double pagamentoEmFalta;
    private LocalDate dataPagamento;
    private StatusPagamento status;
    private String contratoId;
    private String clienteNome;

    public Pagamento() {}

    public Pagamento(Contrato contrato) {
        this.contrato = contrato;
        this.contratoId = String.valueOf(contrato.getIdentificador());
        this.clienteNome = contrato.getCliente().getNome();
        this.dataPagamento = LocalDate.now();
        calcularValores();
    }

    private void calcularValores() {
        double diaria = contrato.getBicicleta().getDiariaTaxaAluguel();
        double deposito = contrato.getBicicleta().getDeposito();
        int dias = contrato.getNumeroDias();
        this.valorTotal = (diaria * dias) + deposito;
        if (contrato.getStatus() == StatusContrato.FINALIZADO) {
            this.valorPago = diaria * dias;
        } else if (contrato.getStatus() == StatusContrato.ATIVO) {
            this.valorPago = contrato.getValorDeposito(contrato.getBicicleta());
        } else {
            this.valorPago = 0;
        }
        this.pagamentoEmFalta = valorTotal - valorPago;
        this.status = (pagamentoEmFalta <= 0) ? StatusPagamento.PAGO : StatusPagamento.PENDENTE;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
        calcularValores();
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setContratoId(String contratoId) {
        this.contratoId = contratoId;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public void setPagamentoEmFalta(double pagamentoEmFalta) {
        this.pagamentoEmFalta = pagamentoEmFalta;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getValorPago() {
        return valorPago;
    }

    public double getPagamentoEmFalta() {
        return pagamentoEmFalta;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public String getContratoId() {
        return contratoId;
    }

}
