package org.example.DataAccessObject;

import org.example.Models.Bicicleta;
import org.example.Util.GerenciadorBancoDados;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class BicicletaDAO {

    public BicicletaDAO() {
        inicializarTabela();
    }

    private void inicializarTabela() {
        try (Connection conn = GerenciadorBancoDados.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS bicicletas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "numero INTEGER UNIQUE NOT NULL, " +
                    "nome TEXT NOT NULL, " +
                    "marca TEXT NOT NULL, " +
                    "modelo TEXT NOT NULL, " +
                    "deposito REAL NOT NULL, " +
                    "tipo TEXT NOT NULL, " +
                    "diaria REAL NOT NULL, " +
                    "disponibilidade BOOLEAN NOT NULL" +
                    ")");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela bicicletas: " + e.getMessage());
        }
    }

    public Bicicleta inserir(Bicicleta bicicleta) {
        String sql = "INSERT INTO bicicletas (numero, nome, marca, modelo, deposito, tipo, diaria, disponibilidade) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int numero = 10000 + new Random().nextInt(90000);
            bicicleta.setNumero(numero);

            pstmt.setInt(1, bicicleta.getNumero());
            pstmt.setString(2, bicicleta.getNome());
            pstmt.setString(3, bicicleta.getMarca());
            pstmt.setString(4, bicicleta.getModelo());
            pstmt.setDouble(5, bicicleta.getDeposito());
            pstmt.setString(6, bicicleta.getTipo());
            pstmt.setDouble(7, bicicleta.getDiariaTaxaAluguel());
            pstmt.setBoolean(8, bicicleta.isDisponibilidade());

            pstmt.executeUpdate();
            return bicicleta;

        } catch (SQLException e) {
            System.err.println("Erro ao inserir bicicleta: " + e.getMessage());
            return null;
        }
    }

    public boolean atualizar(Bicicleta bicicleta) {
        String sql = "UPDATE bicicletas SET nome = ?, marca = ?, modelo = ?, deposito = ?, tipo = ?, diaria = ?, disponibilidade = ? " +
                "WHERE numero = ?";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bicicleta.getNome());
            pstmt.setString(2, bicicleta.getMarca());
            pstmt.setString(3, bicicleta.getModelo());
            pstmt.setDouble(4, bicicleta.getDeposito());
            pstmt.setString(5, bicicleta.getTipo());
            pstmt.setDouble(6, bicicleta.getDiariaTaxaAluguel());
            pstmt.setBoolean(7, bicicleta.isDisponibilidade());
            pstmt.setInt(8, bicicleta.getNumero());

            int linhasAfetadas = pstmt.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar bicicleta: " + e.getMessage());
            return false;
        }
    }

    public Bicicleta buscarPorNumero(int numero) {
        String sql = "SELECT * FROM bicicletas WHERE numero = ?";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, numero);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Bicicleta bicicleta = new Bicicleta();
                    bicicleta.setNumero(rs.getInt("numero"));
                    bicicleta.setNome(rs.getString("nome"));
                    bicicleta.setMarca(rs.getString("marca"));
                    bicicleta.setModelo(rs.getString("modelo"));
                    bicicleta.setDeposito(rs.getDouble("deposito"));
                    bicicleta.setTipo(rs.getString("tipo"));
                    bicicleta.setDiariaTaxaAluguel(rs.getDouble("diaria"));
                    bicicleta.setDisponibilidade(rs.getBoolean("disponibilidade"));
                    return bicicleta;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar bicicleta por número: " + e.getMessage());
        }
        return null;
    }

    //não implementado no front-end
    public boolean deletarPorNumero(int numero) {
        String sql = "DELETE FROM bicicletas WHERE numero = ?";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, numero);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar bicicleta: " + e.getMessage());
            return false;
        }
    }

    public List<Bicicleta> listarTodas() {
        List<Bicicleta> lista = new ArrayList<>();
        String sql = "SELECT * FROM bicicletas";

        try (Connection conn = GerenciadorBancoDados.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Bicicleta bicicleta = new Bicicleta();
                bicicleta.setNumero(rs.getInt("numero"));
                bicicleta.setNome(rs.getString("nome"));
                bicicleta.setMarca(rs.getString("marca"));
                bicicleta.setModelo(rs.getString("modelo"));
                bicicleta.setDeposito(rs.getDouble("deposito"));
                bicicleta.setTipo(rs.getString("tipo"));
                bicicleta.setDiariaTaxaAluguel(rs.getDouble("diaria"));
                bicicleta.setDisponibilidade(rs.getBoolean("disponibilidade"));
                lista.add(bicicleta);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar bicicletas: " + e.getMessage());
        }

        return lista;
    }

    public int contar() {
        String sql = "SELECT COUNT(*) FROM bicicletas";
        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
