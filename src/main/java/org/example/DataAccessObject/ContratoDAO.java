package org.example.DataAccessObject;

import org.example.Models.Contrato;
import org.example.Util.GerenciadorBancoDados;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ContratoDAO {

    public ContratoDAO() {
        try (Connection conn = GerenciadorBancoDados.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS contratos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "identificador TEXT UNIQUE NOT NULL" +
                    "cliente TEXT NOT NULL, " +
                    "bicicleta TEXT NOT NULL, " +
                    "data_inicio DATE NOT NULL, " +
                    "data_retorno DATE NOT NULL, " +
                    "taxa_atraso REAL NOT NULL, " +
                    "taxa_dano REAL NOT NULL, " +
                    "dias INTEGER NOT NULL, " +
                    "status TEXT NOT NULL" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Contrato contrato) {
        String sql = "INSERT INTO contratos (identificador, cliente, bicicleta, data_inicio, " +
                "data_retorno, taxa_atraso, taxa_dano, dias, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contrato.getIdentificador());
            pstmt.setString(2, contrato.getCliente().getNome());
            pstmt.setString(3, contrato.getBicicleta().getNome());
            pstmt.setString(4, contrato.getDataInicial().toString());
            pstmt.setString(5, contrato.getDataRetorno().toString());
            pstmt.setDouble(6, contrato.getTaxaAtraso());
            pstmt.setDouble(7, contrato.getTaxaDano());
            pstmt.setInt(8, contrato.getNumeroDias());
            pstmt.setString(9, contrato.getStatus().toString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir contrato " + e.getMessage());
        }
    }
}
