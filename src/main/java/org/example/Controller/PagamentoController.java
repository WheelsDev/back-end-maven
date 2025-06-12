package org.example.Controller;

import org.example.DataAccessObject.ContratoDAO;
import org.example.DataAccessObject.PagamentoDAO;
import org.example.Models.Contrato;
import org.example.Models.Pagamento;
import org.example.Models.StatusContrato;
import org.example.Models.StatusPagamento;
import org.example.Util.GerarEmail;
import org.example.Util.GerarPDF;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Paths;
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
}
