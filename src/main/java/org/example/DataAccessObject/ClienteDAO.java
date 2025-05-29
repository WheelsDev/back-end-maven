package org.example.DataAccessObject;

import org.example.Models.Cliente;
import org.example.Util.GerenciadorBancoDados;

import java.sql.*;

public class ClienteDAO {

    public ClienteDAO() {
        try (Connection conn = GerenciadorBancoDados.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS clientes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL, " +
                    "endereco TEXT NOT NULL, " +
                    "telefone TEXT NOT NULL, " +
                    "email TEXT NOT NULL" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Cliente cliente) {

        String sql = "INSERT INTO clientes (nome, endereco, telefone, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEndereco());
            pstmt.setString(3, cliente.getTelefone());
            pstmt.setString(4, cliente.getEmail());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
        }
    }

    public Cliente buscarPorNome(String nome) {

        String sql = "SELECT * FROM clientes WHERE LOWER(nome) = LOWER(?)\n";
        try (Connection conn = GerenciadorBancoDados.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,nome);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setNome(rs.getString("nome"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                return cliente;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por nome\n" + e.getMessage());
        }
        return null;
    }

    public void atualizar(Cliente cliente) {

        String sql = "UPDATE clientes SET endereco = ?, telefone = ?, email = ? WHERE nome = ?";
        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getEndereco());
            pstmt.setString(2, cliente.getTelefone());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, cliente.getNome());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void deletarPorNome(String nome) {

        String sql = "DELETE FROM clientes WHERE nome = ?";
        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }

}
