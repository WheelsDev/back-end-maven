package org.example.DataAccessObject;

import org.example.Models.Pagamento;
import org.example.Util.GerenciadorBancoDados;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PagamentoDAO {

    public PagamentoDAO() {
        try (Connection conn = GerenciadorBancoDados.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS pagamentos ("+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "contrato_id INTEGER NOT NULL, " +
                    "cliente TEXT NOT NULL, " +
                    "valor_total REAL NOT NULL, " +
                    "valor_pago REAL NOT NULL, " +
                    "data_pagamento DATE NOT NULL, " +
                    "valor_em_aberto REAL NOT NULL, " +
                    "status TEXT NOT NULL" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Pagamento pagamento) {
        String sql = "INSERT INTO pagamentos (contrato_id, cliente, valor_total, valor_pago, " +
                "data_pagamento, valor_em_aberto, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pagamento.getContrato().getIdentificador());
            pstmt.setString(2, pagamento.getContrato().getCliente().getNome());
            pstmt.setDouble(3, pagamento.getValorTotal());
            pstmt.setDouble(4, pagamento.getValorPago());
            pstmt.setString(5, pagamento.getDataPagamento().toString());
            pstmt.setDouble(6, pagamento.getPagamentoEmFalta());
            pstmt.setString(7, pagamento.getStatus().toString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir pagamento " + e.getMessage());
        }
    }
}
