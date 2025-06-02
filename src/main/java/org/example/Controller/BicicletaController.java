package org.example.Controller;

import org.example.Models.Bicicleta;
import org.example.DataAccessObject.BicicletaDAO;
import org.example.DataAccessObject.ClienteDAO;
import org.example.DataAccessObject.ContratoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bicicletas")
@CrossOrigin(origins = "http://localhost:3000")
public class BicicletaController {

    @Autowired
    private BicicletaDAO bicicletaDAO;

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private ContratoDAO contratoDAO;

    @GetMapping
    public ResponseEntity<List<Bicicleta>> listarTodas() {
        List<Bicicleta> bicicletas = bicicletaDAO.listarTodas();
        return ResponseEntity.ok(bicicletas);
    }

    @PostMapping
    public ResponseEntity<Bicicleta> criar(@RequestBody Bicicleta bicicleta) {
        Bicicleta novaBicicleta = bicicletaDAO.inserir(bicicleta);
        return ResponseEntity.ok(novaBicicleta);
    }

    @PutMapping("/{numero}")
    public ResponseEntity<String> atualizar(@PathVariable int numero, @RequestBody Bicicleta bicicleta) {
        bicicleta.setNumero(numero);
        boolean bicicletaAtualizada = bicicletaDAO.atualizar(bicicleta);
        if (bicicletaAtualizada) {
            return ResponseEntity.ok("Bicicleta atualizada com sucesso.");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> deletar(@PathVariable int numero) {
        boolean removido = bicicletaDAO.deletarPorNumero(numero);
        if (removido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/estatisticas")
    public Map<String, Integer> getEstatisticas() {
        int bicicletas = bicicletaDAO.contar();
        int clientes = clienteDAO.contar();
        int contratos = contratoDAO.contar();

        Map<String, Integer> response = new HashMap<>();
        response.put("bicicletas", bicicletas);
        response.put("clientes", clientes);
        response.put("contratos", contratos);

        return response;
    }
}
