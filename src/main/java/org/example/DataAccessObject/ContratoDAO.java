package org.example.DataAccessObject;

import org.example.Util.GerenciadorBancoDados;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ContratoDAO {

    public ContratoDAO() {
        try (Connection conn = GerenciadorBancoDados.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS contratos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
}
