package org.example.DataAccessObject;

import org.example.Models.Bicicleta;
import org.example.Util.GerenciadorBancoDados;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class BicicletaDAO {

    public BicicletaDAO() {
        try (Connection conn = GerenciadorBancoDados.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS bicicletas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
        String sql = "INSERT INTO bicicletas (nome, marca, modelo, deposito, tipo, diaria, disponibilidade) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bicicleta.getNome());
            pstmt.setString(2, bicicleta.getMarca());
            pstmt.setString(3, bicicleta.getModelo());
            pstmt.setDouble(4, bicicleta.getDeposito());
            pstmt.setString(5,bicicleta.getTipo());
            pstmt.setDouble(6, bicicleta.getDiariaTaxaAluguel());
            pstmt.setBoolean(7, bicicleta.isDisponibilidade());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir bicicleta\n" + e.getMessage());
        }
    }
}
