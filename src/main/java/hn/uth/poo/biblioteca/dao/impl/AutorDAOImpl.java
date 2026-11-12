
/**
 *
 * @author Edwin
 */


package hn.uth.poo.biblioteca.dao.impl;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.Conexion;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutorDAOImpl implements AutorDAO {

    // INSERTAR AUTOR
    @Override
    public void insertar(Autor autor) {

        String sql = "INSERT INTO autores (nombre, nacionalidad) "
                   + "VALUES (?, ?)";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad());

            ps.executeUpdate();

            // Obtener el ID generado por SQLite
            try (PreparedStatement consulta =
                    con.prepareStatement("SELECT last_insert_rowid()");
                 ResultSet rs = consulta.executeQuery()) {

                if (rs.next()) {
                    autor.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatosException("Error al insertar autor", e);
        }
    }

    // BUSCAR AUTOR POR ID
    @Override
    public Autor buscarPorId(Integer id) {

        String sql = "SELECT * FROM autores WHERE id = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapear(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new DatosException("Error al buscar autor", e);
        }
    }

    // LISTAR TODOS LOS AUTORES
    @Override
    public List<Autor> listar() {

        List<Autor> autores = new ArrayList<>();

        String sql = "SELECT * FROM autores ORDER BY id";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                autores.add(mapear(rs));
            }

            return autores;

        } catch (SQLException e) {
            throw new DatosException("Error al listar autores", e);
        }
    }

    // ACTUALIZAR AUTOR
    @Override
    public void actualizar(Autor autor) {

        String sql = "UPDATE autores SET nombre = ?, "
                   + "nacionalidad = ? WHERE id = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad());
            ps.setInt(3, autor.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatosException("Error al actualizar autor", e);
        }
    }

    // ELIMINAR AUTOR
    @Override
    public void eliminar(Integer id) {

        String sql = "DELETE FROM autores WHERE id = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatosException("Error al eliminar autor", e);
        }
    }

    // MAPEAR RESULTSET A OBJETO AUTOR
    private Autor mapear(ResultSet rs) throws SQLException {

        Autor autor = new Autor();

        autor.setId(rs.getInt("id"));
        autor.setNombre(rs.getString("nombre"));
        autor.setNacionalidad(rs.getString("nacionalidad"));

        return autor;
    }

}

