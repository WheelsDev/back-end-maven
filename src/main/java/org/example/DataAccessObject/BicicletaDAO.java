package org.example.DataAccessObject;

import org.example.Models.Bicicleta;
import org.example.Util.GerenciadorBancoDados;

import java.sql.*;

public class BicicletaDAO {

    public BicicletaDAO() {
        try (Connection conn = GerenciadorBancoDados.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS bicicletas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "numero INTEGER UNIQUE NOT NULL, " +
                    "nome TEXT NOT NULL, " +
                    "marca TEXT NOT NULL" +
                    "modelo TEXT NOT NULL" +
                    "deposito REAL NOT NULL" +
                    "tipo TEXT NOT NULL" +
                    "diaria REAL NOT NULL" +
                    "disponibilidade BOOLEAN NOT NULL" +
                    ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Bicicleta bicicleta) {

        String sql = "INSERT INTO bicicletas (numero, nome, marca, modelo, deposito, tipo, diaria, disponibilidade) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bicicleta.getNumero());
            pstmt.setString(2, bicicleta.getNome());
            pstmt.setString(3, bicicleta.getMarca());
            pstmt.setString(4, bicicleta.getModelo());
            pstmt.setDouble(5, bicicleta.getDeposito());
            pstmt.setString(6,bicicleta.getTipo());
            pstmt.setDouble(7, bicicleta.getDiariaTaxaAluguel());
            pstmt.setBoolean(8, bicicleta.isDisponibilidade());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir bicicleta\n" + e.getMessage());
        }
    }

    public Bicicleta buscarPorNumero(int numero) {

        String sql = "SELECT * FROM bicicletas WHERE numero = ?";
        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, numero);
            ResultSet rs = pstmt.executeQuery();
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

        } catch (SQLException e) {
            System.err.println("Erro ao buscar bicicleta por numero\n" + e.getMessage());
        } return null;
    }

    public void atualizar(Bicicleta bicicleta) {
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
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar bicicleta: " + e.getMessage());
        }
    }

    public void deletarPorNumero(int numero) {

        String sql = "DELETE FROM bicicletas WHERE numero = ?";
        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, numero);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar bicicleta: " + e.getMessage());
        }
    }
}
