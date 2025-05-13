package org.example.DataAccessObject;

import org.example.Util.GerenciadorBancoDados;
import java.sql.Connection;
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
}
