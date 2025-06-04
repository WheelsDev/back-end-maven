package org.example.DataAccessObject;

import org.example.Models.Pagamento;
import org.example.Models.StatusPagamento;
import org.example.Util.GerenciadorBancoDados;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {

    public PagamentoDAO() {
        try (Connection conn = GerenciadorBancoDados.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS pagamentos ("+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "contrato_id TEXT UNIQUE NOT NULL, " +
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

    public List<Pagamento> listarParaGrafico() {

        List<Pagamento> lista = new ArrayList<>();
        String sql = "SELECT contrato_id, cliente, valor_total, valor_pago, valor_em_aberto, data_pagamento, status FROM pagamentos";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Pagamento p = new Pagamento();
                p.setContratoId(rs.getString("contrato_id"));
                p.setClienteNome(rs.getString("cliente"));
                p.setValorTotal(rs.getDouble("valor_total"));
                p.setPagamentoEmFalta(rs.getDouble("valor_em_aberto"));
                p.setDataPagamento(LocalDate.parse(rs.getString("data_pagamento")));
                p.setStatus(StatusPagamento.valueOf(rs.getString("status")));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar pagamentos para gráfico: " + e.getMessage());
        }

        return lista;
    }

    public Pagamento buscarPorContratoId(String contratoId) {

        String sql = "SELECT * FROM pagamentos WHERE contrato_id = ?";
        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contratoId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Pagamento p = new Pagamento();
                p.setContratoId(rs.getString("contrato_id"));
                p.setClienteNome(rs.getString("cliente"));
                p.setValorTotal(rs.getDouble("valor_total"));
                p.setValorPago(rs.getDouble("valor_pago"));
                p.setDataPagamento(LocalDate.parse(rs.getString("data_pagamento")));
                p.setPagamentoEmFalta(rs.getDouble("valor_em_aberto"));
                p.setStatus(StatusPagamento.valueOf(rs.getString("status")));
                return p;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar pagamento por contrato: " + e.getMessage());
        }
        return null;
    }

    public void atualizarStatus(Pagamento pagamento) {
        String sql = "UPDATE pagamentos SET status = ? WHERE contrato_id = ?";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pagamento.getStatus().toString());
            pstmt.setString(2, pagamento.getContratoId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status do pagamento: " + e.getMessage());
        }
    }
}
