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

    public Pagamento(Contrato contrato){
        this.contrato = contrato;
        this.valorTotal = contrato.getNumeroDias() * contrato.getBicicleta().getdiariaTaxaAluguel() + contrato.getBicicleta().getDeposito();
        this.valorPago = pagarAluguel();
        this.pagamentoEmFalta = valorTotal - valorPago;
        this.dataPagamento = LocalDate.now();
        status = StatusPagamento.PENDENTE;
    }

    private double pagarAluguel() {
        System.out.print("Coloque o valor do pagamento: ");
//        Scanner leitor = new Scanner(System.in);
        return 100;
    }

    public void calcularPagamentoTotal(Contrato contrato){
        emitirRecibo(contrato);
    }

    private void emitirRecibo(Contrato contrato){

        String client = contrato.getCliente().getNome();
        String endereco = contrato.getCliente().getEndereco();
        System.out.println("Imprimindo comprovante para: '" + client + "' ......");
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
