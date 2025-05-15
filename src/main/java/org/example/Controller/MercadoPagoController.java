package org.example.Controller;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.example.DataAccessObject.ContratoDAO;
import org.example.Integration.MercadoPagoService;
import org.example.Models.Contrato;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamentos")
public class MercadoPagoController {

    private final ContratoDAO contratoDAO = new ContratoDAO();
    private final MercadoPagoService mercadoPagoService = new MercadoPagoService();

    @GetMapping("/gerar/{contratoId}")
    public ResponseEntity<String> gerarLinkDePagamento(@PathVariable String contratoId) {
        Contrato contrato = contratoDAO.buscarPorIdentificador(contratoId);
        if (contrato == null) {
            return ResponseEntity.notFound().build();
        }

        double valorTotal = contrato.getNumeroDias() * contrato.getBicicleta().getDiariaTaxaAluguel()
                + contrato.getBicicleta().getDeposito();

        try {
            String link = mercadoPagoService.criarPagamento(contrato, valorTotal);
            return ResponseEntity.ok(link);
        } catch (MPException | MPApiException e) {
            return ResponseEntity.status(500).body("Erro ao gerar link de pagamento: " + e.getMessage());
        }
    }
}

