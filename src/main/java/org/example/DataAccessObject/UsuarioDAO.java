package org.example.DataAccessObject;

import org.example.Models.TipoUsuario;
import org.example.Models.Usuario;
import org.example.Util.GerenciadorBancoDados;

import java.sql.*;

public class UsuarioDAO {

    public UsuarioDAO() {
        try (Connection conn = GerenciadorBancoDados.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "email TEXT NOT NULL, " +
                    "senha TEXT NOT NULL, " +
                    "tipo TEXT NOT NULL" +
                    ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuarios (email, senha, tipo) VALUES (?, ?, ?)";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getEmail());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setString(3, usuario.getTipo().name());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
        }
    }


    public Usuario autenticarUsuario(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE LOWER(email) = LOWER(?) AND senha = ?";
        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, senha);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getString("email"),
                        rs.getString("senha"),
                        TipoUsuario.fromString(rs.getString("tipo"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
