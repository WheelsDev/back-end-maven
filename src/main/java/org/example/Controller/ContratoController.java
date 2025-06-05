package org.example.Controller;

import org.example.DataAccessObject.BicicletaDAO;
import org.example.DataAccessObject.ContratoDAO;
import org.example.Integration.MercadoPagoService;
import org.example.Models.Bicicleta;
import org.example.Models.Cliente;
import org.example.Models.Contrato;
import org.example.Models.StatusContrato;
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

    @PostMapping
    public ResponseEntity<String> criarContrato(@RequestBody Contrato contrato) {
        BicicletaDAO bicicletaDAO = new BicicletaDAO();
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
                throw new RuntimeException(e);
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

    @Autowired
    private MercadoPagoService mercadoPagoService;

    public void visualizarContrato () {

    }

    /*@GetMapping("/teste-pagamento")
    public ResponseEntity<Map<String, Object>> testePagamento() {
        try {
            Cliente clienteTeste = new Cliente(
                    "João Teste",
                    "Rua Fictícia, 123",
                    "(21) 99999-9999",
                    "joao@teste.com"
            );

            Bicicleta bicicletaTeste = new Bicicleta(
                    "Bike Teste",
                    "Caloi",
                    "Explorer",
                    150.0,
                    "Montanha",
                    20.0,
                    true
            );

            Contrato contratoFalso = new Contrato(

                    clienteTeste,
                    bicicletaTeste,
                    LocalDate.now(),
                    5
            );

            double valorTotal = contratoFalso.getNumeroDias() * contratoFalso.getBicicleta().getDiariaTaxaAluguel();

            String paymentUrl = mercadoPagoService.criarPagamento(contratoFalso, valorTotal);

            Map<String, Object> response = new HashMap<>();
            response.put("identificadorContrato", contratoFalso.getIdentificador());
            response.put("valorTotal", valorTotal);
            response.put("cliente", Map.of(
                    "nome", clienteTeste.getNome(),
                    "email", clienteTeste.getEmail()
            ));
            response.put("bicicleta", Map.of(
                    "nome", bicicletaTeste.getNome(),
                    "valorDiaria", bicicletaTeste.getDiariaTaxaAluguel()
            ));
            response.put("pagamento", Map.of(
                    "url", paymentUrl,
                    "diasAluguel", contratoFalso.getNumeroDias()
            ));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("erro", "Erro ao gerar pagamento");
            error.put("mensagem", e.getMessage());
            error.put("causa", e.getCause() != null ? e.getCause().getMessage() : "Desconhecida");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }*/

}

