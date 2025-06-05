package org.example.Controller;

import org.example.DataAccessObject.ContratoDAO;
import org.example.Models.Contrato;
import org.example.Models.StatusContrato;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhook/mercadopago")
public class MercadoPagoWebhookController {

    private final ContratoDAO contratoDAO = new ContratoDAO();

    @PostMapping
    public ResponseEntity<Void> receberNotificacao(@RequestBody Map<String, Object> payload) {
        try {
            if (payload.containsKey("type") && "payment".equals(payload.get("type"))) {
                String externalReference = (String) payload.get("data").toString(); // Ajustar se necessário

                // Aqui você pode consultar a API do Mercado Pago para verificar o status real da transação
                System.out.println("Pagamento aprovado para contrato: " + externalReference);

                Contrato contrato = contratoDAO.buscarPorIdentificador(externalReference);
                if (contrato != null) {
                    contrato.setStatus(StatusContrato.FINALIZADO);
                    contratoDAO.atualizar(contrato);
                }
            }
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
