package org.example.Controller;

import org.example.DataAccessObject.ContratoDAO;
import org.example.Integration.MercadoPagoService;
import org.example.Models.Bicicleta;
import org.example.Models.Cliente;
import org.example.Models.Contrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        boolean sucesso = contratoDAO.inserir(contrato);
        if (sucesso) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Contrato criado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar contrato.");
        }
    }

    @PutMapping("/{identificador}")
    public ResponseEntity<String> atualizarContrato(@PathVariable String identificador, @RequestBody Contrato contratoAtualizado) {
        // Certifique-se de que o identificador do contratoAtualizado está correto
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
        // Como sua DAO atual não tem método para listar todos, vou criar uma lista vazia por enquanto
        List<Contrato> contratos = new ArrayList<>();
        // Aqui você poderia implementar um método contratoDAO.listarTodos()
        return ResponseEntity.ok(contratos);
    }

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @GetMapping("/teste-pagamento")
    public ResponseEntity<Map<String, Object>> testePagamento() {
        try {
            // Criar cliente de teste
            Cliente clienteTeste = new Cliente(
                    "João Teste",
                    "Rua Fictícia, 123",
                    "(21) 99999-9999",
                    "joao@teste.com"
            );

            // Criar bicicleta de teste
            Bicicleta bicicletaTeste = new Bicicleta(
                    "Bike Teste",
                    "Caloi",
                    "Explorer",
                    150.0,
                    "Montanha",
                    20.0,
                    true
            );

            // Criar contrato de teste
            Contrato contratoFalso = new Contrato(
                    clienteTeste,
                    bicicletaTeste,
                    LocalDate.now(),
                    5
            );

            // Calcular valor total
            double valorTotal = contratoFalso.getNumeroDias() * contratoFalso.getBicicleta().getDiariaTaxaAluguel();

            String paymentUrl = mercadoPagoService.criarPagamento(contratoFalso, valorTotal);


            // Criar resposta com informações detalhadas
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
            e.printStackTrace(); // Adicione esta linha para ver o stack trace completo
            Map<String, Object> error = new HashMap<>();
            error.put("erro", "Erro ao gerar pagamento");
            error.put("mensagem", e.getMessage());
            error.put("causa", e.getCause() != null ? e.getCause().getMessage() : "Desconhecida");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

}

