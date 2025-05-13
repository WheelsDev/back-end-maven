package org.example.DataAccessObject;

import org.example.Util.GerenciadorBancoDados;

import java.sql.Connection;
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
}
