package org.example.DataAccessObject;

import org.example.Models.*;
import org.example.Util.GerenciadorBancoDados;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContratoDAO {

    public ContratoDAO() {
        try (Connection conn = GerenciadorBancoDados.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS contratos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "identificador TEXT UNIQUE NOT NULL, " +
                    "cliente TEXT NOT NULL, " +
                    "bicicleta_numero INTEGER NOT NULL, " +
                    "data_inicio DATE NOT NULL, " +
                    "data_retorno DATE NOT NULL, " +
                    "deposito REAL NOT NULL, " +
                    "taxa_atraso REAL NOT NULL, " +
                    "taxa_dano REAL NOT NULL, " +
                    "dias INTEGER NOT NULL, " +
                    "status TEXT NOT NULL" +
                    ")");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela contratos: " + e.getMessage());
        }
    }

    public boolean inserir(Contrato contrato) {
        String sql = "INSERT INTO contratos (identificador, cliente, bicicleta_numero, data_inicio, " +
                "data_retorno, deposito, taxa_atraso, taxa_dano, dias, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String identificador = contrato.gerarIdentificador();
            contrato.setIdentificador(identificador);

            pstmt.setString(1, contrato.getIdentificador());
            pstmt.setString(2, contrato.getCliente().getNome());
            pstmt.setInt(3, contrato.getBicicleta().getNumero());
            pstmt.setString(4, contrato.getDataInicial().toString());
            pstmt.setString(5, contrato.getDataRetorno().toString());
            pstmt.setDouble(6, contrato.getValorDeposito(contrato.getBicicleta()));
            pstmt.setDouble(7, contrato.getTaxaAtraso());
            pstmt.setDouble(8, contrato.getTaxaDano());
            pstmt.setInt(9, contrato.getNumeroDias());
            if (contrato.getStatus() == null) {
                contrato.setStatus(StatusContrato.ATIVO);
            }
            pstmt.setString(10, contrato.getStatus().toString());
            pstmt.executeUpdate();

            Pagamento pagamento = new Pagamento(contrato);
            pagamento.setStatus(contrato.getStatus() == StatusContrato.FINALIZADO
                    ? StatusPagamento.PAGO : StatusPagamento.PENDENTE);

            new PagamentoDAO().inserir(pagamento);

            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao inserir contrato: " + e.getMessage());
            return false;
        }
    }

    public Contrato buscarPorIdentificador(String identificador) {
        String sql = "SELECT c.*, b.*, cl.* FROM contratos c " +
                "JOIN bicicletas b ON c.bicicleta_numero = b.numero " +
                "JOIN clientes cl ON c.cliente = cl.nome " +
                "WHERE c.identificador = ?";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, identificador);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Contrato contrato = new Contrato();
                contrato.setIdentificador(rs.getString("identificador"));

                Cliente cliente = new Cliente();
                cliente.setNome(rs.getString("cliente"));
                cliente.setEmail(rs.getString("email"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getString("telefone"));
                contrato.setCliente(cliente);

                Bicicleta bicicleta = new Bicicleta();
                bicicleta.setNumero(rs.getInt("numero"));
                bicicleta.setNome(rs.getString("nome"));
                bicicleta.setMarca(rs.getString("marca"));
                bicicleta.setModelo(rs.getString("modelo"));
                bicicleta.setDiariaTaxaAluguel(rs.getDouble("diaria"));
                bicicleta.setTipo(rs.getString("tipo"));
                bicicleta.setDeposito(rs.getDouble("deposito"));
                bicicleta.setDisponibilidade(rs.getBoolean("disponibilidade"));
                contrato.setBicicleta(bicicleta);

                contrato.setDataInicial(LocalDate.parse(rs.getString("data_inicio")));
                contrato.setDataRetorno(LocalDate.parse(rs.getString("data_retorno")));
                contrato.setTaxaAtraso(rs.getDouble("taxa_atraso"));
                contrato.setTaxaDano(rs.getDouble("taxa_dano"));
                contrato.setNumeroDias(rs.getInt("dias"));
                contrato.setStatus(StatusContrato.valueOf(rs.getString("status")));

                return contrato;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar contrato por identificador: " + e.getMessage());
        }

        return null;
    }

    public boolean atualizar(Contrato contrato) {
        String sql = "UPDATE contratos SET cliente = ?, bicicleta_numero = ?, data_inicio = ?, data_retorno = ?, " +
                "taxa_atraso = ?, taxa_dano = ?, dias = ?, status = ? WHERE identificador = ?";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contrato.getCliente().getNome());
            pstmt.setInt(2, contrato.getBicicleta().getNumero());
            pstmt.setString(3, contrato.getDataInicial().toString());
            pstmt.setString(4, contrato.getDataRetorno().toString());
            pstmt.setDouble(5, contrato.getTaxaAtraso());
            pstmt.setDouble(6, contrato.getTaxaDano());
            pstmt.setInt(7, contrato.getNumeroDias());
            pstmt.setString(8, contrato.getStatus().toString());
            pstmt.setString(9, contrato.getIdentificador());

            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar contrato: " + e.getMessage());
            return false;
        }
    }

    //não implementado no front-end
    public boolean deletarPorIdentificador(String identificador) {
        String sql = "DELETE FROM contratos WHERE identificador = ?";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, identificador);
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar contrato: " + e.getMessage());
            return false;
        }
    }

    public List<Contrato> listarTodos() {
        List<Contrato> contratos = new ArrayList<>();
        String sql = "SELECT c.*, b.*, cl.* FROM contratos c " +
                "JOIN bicicletas b ON c.bicicleta_numero = b.numero " +
                "JOIN clientes cl ON c.cliente = cl.nome";

        try (Connection conn = GerenciadorBancoDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Contrato contrato = new Contrato();
                contrato.setIdentificador(rs.getString("identificador"));

                Cliente cliente = new Cliente();
                cliente.setNome(rs.getString("cliente"));
                cliente.setEmail(rs.getString("email"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getString("telefone"));
                contrato.setCliente(cliente);

                Bicicleta bicicleta = new Bicicleta();
                bicicleta.setNumero(rs.getInt("numero"));
                bicicleta.setNome(rs.getString("nome"));
                bicicleta.setMarca(rs.getString("marca"));
                bicicleta.setModelo(rs.getString("modelo"));
                bicicleta.setTipo(rs.getString("tipo"));
                bicicleta.setDeposito(rs.getDouble("deposito"));
                bicicleta.setDisponibilidade(rs.getBoolean("disponibilidade"));
                contrato.setBicicleta(bicicleta);

                contrato.setDataInicial(LocalDate.parse(rs.getString("data_inicio")));
                contrato.setDataRetorno(LocalDate.parse(rs.getString("data_retorno")));
                contrato.setTaxaAtraso(rs.getDouble("taxa_atraso"));
                contrato.setTaxaDano(rs.getDouble("taxa_dano"));
                contrato.setNumeroDias(rs.getInt("dias"));
                contrato.setStatus(StatusContrato.valueOf(rs.getString("status")));

                contratos.add(contrato);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar contratos: " + e.getMessage());
        }

        return contratos;
    }

    public int contar() {
        String sql = "SELECT COUNT(*) FROM contratos";
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
