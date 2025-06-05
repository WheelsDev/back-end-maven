package org.example.Controller;

import org.example.DataAccessObject.ContratoDAO;
import org.example.Integration.MercadoPagoService;
import org.example.Models.Contrato;
import org.example.Models.StatusContrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/checkout")
@CrossOrigin(origins = "http://localhost:3000")
public class MercadoPagoController {

    private final ContratoDAO contratoDAO = new ContratoDAO();

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @GetMapping("/gerar/{identificador}")
    public ResponseEntity<String> gerarLinkDePagamento(
            @PathVariable String identificador,
            @RequestParam double valorTotal) {

        Contrato contrato = contratoDAO.buscarPorIdentificador(identificador);
        if (contrato == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            String link = mercadoPagoService.criarPagamento(contrato, valorTotal);
            return ResponseEntity.ok(link);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao gerar link de pagamento: " + e.getMessage());
        }
    }

}
