package org.example.Controller;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import org.example.DataAccessObject.ContratoDAO;
import org.example.DataAccessObject.PagamentoDAO;
import org.example.DataAccessObject.BicicletaDAO; // Supondo que você tenha este DAO
import org.example.Integration.MercadoPagoService;
import org.example.Models.*; // Supondo que Bicicleta esteja neste pacote
import org.example.Util.GerarEmail;
import org.example.Util.GerarPDF;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class MercadoPagoWebhookController {
    private final GerarPDF gerarPDF = new GerarPDF();
    private final GerarEmail gerarEmail = new GerarEmail();
    private final ContratoDAO contratoDAO = new ContratoDAO();
    private final PagamentoDAO pagamentoDAO = new PagamentoDAO();
    private final BicicletaDAO bicicletaDAO = new BicicletaDAO();

    @PostMapping("/mercadopago")
    public ResponseEntity<Void> receberNotificacao(@RequestBody Map<String, Object> payload) {
        System.out.println("Webhook recebido:");
        payload.forEach((k, v) -> System.out.println(k + ": " + v));


        try {
            if (payload.containsKey("type") && "payment".equals(payload.get("type"))) {
                Map<String, Object> data = (Map<String, Object>) payload.get("data");
                String paymentIdStr = String.valueOf(data.get("id"));
                Long paymentId = Long.parseLong(paymentIdStr);

                System.out.println("Webhook: ID do Pagamento recebido: " + paymentId);


                MercadoPagoConfig.setAccessToken("APP_USR-2421671847330837-061114-41589928f405d84bd0eed67fb59dbae4-1980586377");

                PaymentClient client = new PaymentClient();
                Payment payment = client.get(paymentId);
                String status = payment.getStatus();
                System.out.println("Webhook: Status do pagamento na API: " + status);

                if ("approved".equalsIgnoreCase(status)) {
                    String contratoId = payment.getExternalReference();
                    System.out.println("Webhook: Pagamento APROVADO para o Contrato ID: " + contratoId);

                    if (contratoId != null && !contratoId.isEmpty()) {
                        Contrato contrato = contratoDAO.buscarPorIdentificador(contratoId);
                        if (contrato != null) {

                            contrato.setStatus(StatusContrato.FINALIZADO);
                            contratoDAO.atualizar(contrato);
                            System.out.println("Webhook: Contrato " + contratoId + " atualizado para FINALIZADO.");

                            Bicicleta bicicletaDoContrato = contrato.getBicicleta();
                            if (bicicletaDoContrato != null) {
                                bicicletaDoContrato.setDisponibilidade(true);
                                bicicletaDAO.atualizar(bicicletaDoContrato);
                                System.out.println("Webhook: Bicicleta ID " + bicicletaDoContrato.getNumero() + " atualizada para disponível.");
                            } else {
                                System.err.println("Webhook AVISO: Contrato " + contratoId + " não possuía uma bicicleta associada para atualizar.");
                            }

                            Pagamento pagamentoDoContrato = pagamentoDAO.buscarPorContratoId(contrato.getIdentificador());
                            if (pagamentoDoContrato != null) {
                                pagamentoDoContrato.setStatus(StatusPagamento.PAGO);
                                pagamentoDAO.atualizarStatus(pagamentoDoContrato);
                                gerarPDF.gerarComprovantePagamento(contrato);
                                gerarEmail.enviarComprovantePagamento(contrato.getCliente(),contrato);
                                System.out.println("Webhook: Pagamento associado ao contrato " + contratoId + " atualizado para PAGO.");
                            } else {
                                System.err.println("Webhook AVISO: Não foi encontrado um registro de pagamento associado ao contrato " + contratoId);
                            }
                        } else {
                            System.err.println("Webhook ERRO: Contrato com ID " + contratoId + " não encontrado no banco de dados.");
                        }
                    } else {
                        System.err.println("Webhook AVISO: Pagamento " + paymentId + " aprovado, mas sem external_reference (ID do contrato).");
                    }
                }
            }
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            System.err.println("Webhook ERRO: Falha ao processar notificação.");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}