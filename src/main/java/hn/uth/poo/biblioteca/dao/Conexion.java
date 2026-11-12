

/**
 *
 * @author Edwin
 */


package hn.uth.poo.biblioteca.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    private static final String URL = "jdbc:sqlite:biblioteca.db";

    public static Connection obtenerConexion() throws SQLException {

        Connection conexion = DriverManager.getConnection(URL);

        try (Statement st = conexion.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
        }

        return conexion;
    }

    public static void inicializar() {

        String sqlAutores = """
            CREATE TABLE IF NOT EXISTS autores (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                nacionalidad TEXT
            )
            """;

        String sqlLibros = """
            CREATE TABLE IF NOT EXISTS libros (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                isbn TEXT NOT NULL UNIQUE,
                titulo TEXT NOT NULL,
                anio INTEGER,
                precio REAL NOT NULL,
                autor_id INTEGER NOT NULL,
                FOREIGN KEY (autor_id) REFERENCES autores(id)
            )
            """;

        try (Connection con = obtenerConexion();
             Statement st = con.createStatement()) {

            st.execute(sqlAutores);
            st.execute(sqlLibros);

            System.out.println(
                "Base de datos inicializada correctamente."
            );

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error al inicializar la base de datos", e
            );
        }
    }
}
