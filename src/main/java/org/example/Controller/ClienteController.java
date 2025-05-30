package org.example.Controller;

import org.example.Models.Cliente;
import org.example.DataAccessObject.ClienteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {

    @Autowired
    private ClienteDAO clienteDAO;

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteDAO.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{nome}")
    public ResponseEntity<Cliente> buscarPorNome(@PathVariable String nome) {
        Cliente cliente = clienteDAO.buscarPorNome(nome);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente) {
        Cliente novoCliente = clienteDAO.inserir(cliente);
        if (novoCliente != null) {
            return ResponseEntity.ok(novoCliente);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{nome}")
    public ResponseEntity<String> atualizar(@PathVariable String nome, @RequestBody Cliente cliente) {
        cliente.setNome(nome);
        boolean atualizado = clienteDAO.atualizar(cliente);
        if (atualizado) {
            return ResponseEntity.ok("Cliente atualizado com sucesso.");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> deletar(@PathVariable String nome) {
        boolean removido = clienteDAO.deletarPorNome(nome);
        if (removido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}