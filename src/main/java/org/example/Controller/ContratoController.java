package org.example.Controller;

import org.example.DataAccessObject.BicicletaDAO;
import org.example.DataAccessObject.ContratoDAO;
import org.example.DataAccessObject.PagamentoDAO;
import org.example.Integration.MercadoPagoService;
import org.example.Models.*;
import org.example.Util.GerarEmail;
import org.example.Util.GerarPDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contratos")
@CrossOrigin(origins = "http://localhost:3000")
public class ContratoController {

    private final ContratoDAO contratoDAO = new ContratoDAO();
    private final BicicletaDAO bicicletaDAO = new BicicletaDAO();
    private final PagamentoDAO pagamentoDAO = new PagamentoDAO();

    @PostMapping
    public ResponseEntity<String> criarContrato(@RequestBody Contrato contrato) {
        Bicicleta bicicletaNoBanco = bicicletaDAO.buscarPorNumero(contrato.getBicicleta().getNumero());
        GerarPDF gerarPDF = new GerarPDF();
        GerarEmail gerarEmail = new GerarEmail();

        if (contrato.getCliente() == null || contrato.getCliente().getNome() == null) {
            System.err.println("Cliente não pode ser nulo");
        }

        if (bicicletaNoBanco == null) {
            return ResponseEntity.badRequest().body("Bicicleta não encontrada.");
        }

        if (!bicicletaNoBanco.isDisponibilidade()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Bicicleta indisponível para aluguel.");
        }

        contrato.setStatus(StatusContrato.ATIVO);
        boolean sucesso = contratoDAO.inserir(contrato);

        if (sucesso) {

            gerarPDF.gerarContratoAluguel(contrato);
            gerarEmail.enviarContratoDeAluguel(contrato.getCliente(),contrato);
            try {
                Files.delete(Paths.get("src", "main", "java", "org", "example", "Util", contrato.getIdentificador() + ".pdf"));
            } catch (Exception e) {
                System.err.println("Falha ao deletar PDF: " + e.getMessage());
            }
            bicicletaNoBanco.setDisponibilidade(false);
            bicicletaDAO.atualizar(bicicletaNoBanco);

            return ResponseEntity.status(HttpStatus.CREATED).body("Contrato criado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar contrato.");
        }
    }

    @PutMapping("/{identificador}")
    public ResponseEntity<String> atualizarContrato(@PathVariable String identificador, @RequestBody Contrato contratoAtualizado) {
        contratoAtualizado.setIdentificador(identificador);
        boolean sucesso = contratoDAO.atualizar(contratoAtualizado);
        if (sucesso) {
            return ResponseEntity.ok("Contrato atualizado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contrato não encontrado.");
        }
    }

    @DeleteMapping("/{identificador}")
    public ResponseEntity<String> deletarContrato(@PathVariable String identificador) {
        boolean sucesso = contratoDAO.deletarPorIdentificador(identificador);
        if (sucesso) {
            return ResponseEntity.ok("Contrato deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contrato não encontrado.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Contrato>> listarContratos() {
        List<Contrato> contratos = contratoDAO.listarTodos();
        return ResponseEntity.ok(contratos);
    }

    @PostMapping("/{identificador}/finalizar-dinheiro")
    public ResponseEntity<String> finalizarContratoPagoEmDinheiro(@PathVariable String identificador) {
        System.out.println("Controller: Recebida requisição para finalizar contrato (pago em dinheiro): " + identificador);
        try {
            Contrato contrato = contratoDAO.buscarPorIdentificador(identificador);

            if (contrato == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contrato com identificador '" + identificador + "' não encontrado.");
            }
            contrato.setStatus(StatusContrato.FINALIZADO);
            boolean sucessoContrato = contratoDAO.atualizar(contrato);
            if (!sucessoContrato) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar status do contrato.");
            }
            System.out.println("Controller: Contrato " + identificador + " atualizado para FINALIZADO.");

            Bicicleta bicicleta = contrato.getBicicleta();
            if (bicicleta != null) {
                bicicleta.setDisponibilidade(true);
                boolean sucessoBicicleta = bicicletaDAO.atualizar(bicicleta);
                if (!sucessoBicicleta) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar disponibilidade da bicicleta.");
                }
                System.out.println("Controller: Bicicleta ID " + bicicleta.getNumero() + " (do contrato " + identificador + ") atualizada para disponível.");
            } else {
                System.err.println("Controller AVISO: Contrato " + identificador + " não possui bicicleta associada.");
            }
            Pagamento pagamento = pagamentoDAO.buscarPorContratoId(contrato.getIdentificador());
            if (pagamento != null) {
                pagamento.setStatus(StatusPagamento.PAGO);
                pagamentoDAO.atualizarStatus(pagamento);
                System.out.println("Controller: Pagamento do contrato " + identificador + " atualizado para PAGO.");
            } else {
                System.err.println("Controller AVISO: Não foi encontrado registro de pagamento para o contrato " + identificador + ". Criando um novo registro PAGO.");
            }

            return ResponseEntity.ok("Contrato finalizado (pago em dinheiro) com sucesso.");

        } catch (Exception e) {
            System.err.println("Controller ERRO: Falha ao finalizar contrato (pago em dinheiro) para o identificador: " + identificador);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao finalizar contrato: " + e.getMessage());
        }
    }
}
