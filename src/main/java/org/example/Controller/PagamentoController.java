package org.example.Controller;

import org.example.DataAccessObject.ContratoDAO;
import org.example.DataAccessObject.PagamentoDAO;
import org.example.Models.Contrato;
import org.example.Models.Pagamento;
import org.example.Models.StatusContrato;
import org.example.Models.StatusPagamento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
@CrossOrigin(origins = "http://localhost:3000")
public class PagamentoController {

    private final PagamentoDAO pagamentoDAO = new PagamentoDAO();
    @GetMapping("/grafico")
    public List<Pagamento> listarPagamentosParaGrafico() {
        return pagamentoDAO.listarParaGrafico();
    }

    @PostMapping("/finalizar/{identificador}")
    public ResponseEntity<Void> finalizarPagamento(@PathVariable String identificador) {
        ContratoDAO contratoDAO = new ContratoDAO();
        Contrato contrato = contratoDAO.buscarPorIdentificador(identificador);
        if (contrato == null) return ResponseEntity.notFound().build();

        contrato.setStatus(StatusContrato.FINALIZADO);
        contratoDAO.atualizar(contrato);

        Pagamento pagamento = pagamentoDAO.buscarPorContratoId(identificador);
        if (pagamento != null) {
            pagamento.setStatus(StatusPagamento.PAGO);
            pagamentoDAO.atualizarStatus(pagamento);
        }

        return ResponseEntity.ok().build();
    }
}
