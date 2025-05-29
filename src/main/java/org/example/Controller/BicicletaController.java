package org.example.Controller;

import org.example.Models.Bicicleta;
import org.example.DataAccessObject.BicicletaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bicicletas")
@CrossOrigin(origins = "http://localhost:5173") // Porta padrão do Vite
public class BicicletaController {

    @Autowired
    private BicicletaDAO bicicletaDAO;

    @GetMapping
    public ResponseEntity<List<Bicicleta>> listarTodas() {
        List<Bicicleta> bicicletas = bicicletaDAO.listarTodas();
        return ResponseEntity.ok(bicicletas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bicicleta> buscarPorId(@PathVariable int id) {
        Bicicleta bicicleta = bicicletaDAO.buscarPorNumero(id);
        if (bicicleta != null) {
            return ResponseEntity.ok(bicicleta);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Bicicleta> criar(@RequestBody Bicicleta bicicleta) {
        Bicicleta novaBicicleta = bicicletaDAO.inserir(bicicleta);
        return ResponseEntity.ok(novaBicicleta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bicicleta> atualizar(@PathVariable int id, @RequestBody Bicicleta bicicleta) {
        bicicleta.setNumero(id);
        Bicicleta bicicletaAtualizada = bicicletaDAO.atualizar(bicicleta);
        if (bicicletaAtualizada != null) {
            return ResponseEntity.ok(bicicletaAtualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        boolean removido = bicicletaDAO.deletarPorNumero(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}