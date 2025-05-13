package org.example.DataAccessObject;

import org.example.Util.GerenciadorBancoDados;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteDAO {

    public ClienteDAO() {
        try (Connection conn = GerenciadorBancoDados.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS clientes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL, " +
                    "endereço TEXT NOT NULL, " +
                    "telefone TEXT NOT NULL" +
                    "email TEXT NOT NULL" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
