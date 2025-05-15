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
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoDAO pagamentoDAO = new PagamentoDAO();
    @GetMapping("/grafico")
    public List<Pagamento> listarPagamentosParaGrafico() {
        return pagamentoDAO.listarParaGrafico();
    }

    @GetMapping("/{contratoId}")
    public ResponseEntity<Pagamento> buscarPorContrato(@PathVariable String contratoId) {
        Pagamento pagamento = pagamentoDAO.buscarPorContratoId(contratoId);
        if (pagamento != null) {
            return ResponseEntity.ok(pagamento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/finalizar/{contratoId}")
    public ResponseEntity<Void> finalizarPagamento(@PathVariable String contratoId) {
        ContratoDAO contratoDAO = new ContratoDAO(); // CORREÇÃO
        Contrato contrato = contratoDAO.buscarPorIdentificador(contratoId);
        if (contrato == null) return ResponseEntity.notFound().build();

        contrato.setStatus(StatusContrato.FINALIZADO);
        contratoDAO.atualizar(contrato);

        Pagamento pagamento = pagamentoDAO.buscarPorContratoId(contratoId);
        if (pagamento != null) {
            pagamento.setStatus(StatusPagamento.PAGO);
            pagamentoDAO.atualizarStatus(pagamento);
        }

        return ResponseEntity.ok().build();
    }



}
