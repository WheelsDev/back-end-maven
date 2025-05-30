package org.example.Controller;

import org.example.DataAccessObject.ContratoDAO;
import org.example.Models.Contrato;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/contratos")
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

    // Buscar contrato pelo identificador
    @GetMapping("/{identificador}")
    public ResponseEntity<Contrato> buscarContrato(@PathVariable String identificador) {
        Contrato contrato = contratoDAO.buscarPorIdentificador(identificador);
        if (contrato != null) {
            return ResponseEntity.ok(contrato);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Atualizar contrato
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

    // Deletar contrato pelo identificador
    @DeleteMapping("/{identificador}")
    public ResponseEntity<String> deletarContrato(@PathVariable String identificador) {
        boolean sucesso = contratoDAO.deletarPorIdentificador(identificador);
        if (sucesso) {
            return ResponseEntity.ok("Contrato deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contrato não encontrado.");
        }
    }

    // Listar todos contratos (opcional)
    @GetMapping
    public ResponseEntity<List<Contrato>> listarContratos() {
        // Como sua DAO atual não tem método para listar todos, vou criar uma lista vazia por enquanto
        List<Contrato> contratos = new ArrayList<>();
        // Aqui você poderia implementar um método contratoDAO.listarTodos()
        return ResponseEntity.ok(contratos);
    }
}

