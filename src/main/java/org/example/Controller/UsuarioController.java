package org.example.Controller;

import org.example.DataAccessObject.UsuarioDAO;
import org.example.Models.Usuario;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:3000")
public class UsuarioController {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @GetMapping("/autenticação")
    public Usuario autenticar(String email, String senha) {
        return usuarioDAO.autenticarUsuario(email, senha);
    }
}