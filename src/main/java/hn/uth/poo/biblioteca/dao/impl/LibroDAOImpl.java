

/**
 *
 * @author Edwin
 */


package hn.uth.poo.biblioteca.dao.impl;

import hn.uth.poo.biblioteca.dao.Conexion;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    private static final String SELECT_BASE =
        "SELECT l.id, l.isbn, l.titulo, l.anio, l.precio, " +
        "a.id AS autor_id, a.nombre AS autor_nombre, " +
        "a.nacionalidad AS autor_nacionalidad " +
        "FROM libros l INNER JOIN autores a ON l.autor_id = a.id ";

    @Override
    public void insertar(Libro libro) {
        String sql = "INSERT INTO libros " +
                "(isbn, titulo, anio, precio, autor_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());

            if (libro.getAnio() == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, libro.getAnio());
            }

            ps.setDouble(4, libro.getPrecio());
            ps.setInt(5, libro.getAutor().getId());

            ps.executeUpdate();

            try (PreparedStatement psId =
                    con.prepareStatement("SELECT last_insert_rowid()");
                 ResultSet rs = psId.executeQuery()) {

                if (rs.next()) {
                    libro.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatosException("Error al insertar el libro.", e);
        }
    }

    @Override
    public Libro buscarPorId(Integer id) {
        String sql = SELECT_BASE + "WHERE l.id = ?";

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
            throw new DatosException("Error al buscar el libro.", e);
        }
    }

    @Override
    public List<Libro> listar() {
        String sql = SELECT_BASE + "ORDER BY l.id";

        List<Libro> libros = new ArrayList<>();

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                libros.add(mapear(rs));
            }

            return libros;

        } catch (SQLException e) {
            throw new DatosException("Error al listar los libros.", e);
        }
    }

    @Override
    public void actualizar(Libro libro) {
        String sql = "UPDATE libros SET isbn = ?, titulo = ?, " +
                "anio = ?, precio = ?, autor_id = ? WHERE id = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());

            if (libro.getAnio() == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, libro.getAnio());
            }

            ps.setDouble(4, libro.getPrecio());
            ps.setInt(5, libro.getAutor().getId());
            ps.setInt(6, libro.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatosException("Error al actualizar el libro.", e);
        }
    }

    @Override
    public void eliminar(Integer id) {
        String sql = "DELETE FROM libros WHERE id = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatosException("Error al eliminar el libro.", e);
        }
    }

    @Override
    public List<Libro> listarPorAutor(int autorId) {
        String sql = SELECT_BASE + "WHERE l.autor_id = ? ORDER BY l.id";

        List<Libro> libros = new ArrayList<>();

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, autorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapear(rs));
                }
            }

            return libros;

        } catch (SQLException e) {
            throw new DatosException(
                    "Error al listar los libros del autor.", e);
        }
    }

    private Libro mapear(ResultSet rs) throws SQLException {
        int anio = rs.getInt("anio");
        Integer anioLibro = rs.wasNull() ? null : anio;

        Autor autor = new Autor(
                rs.getInt("autor_id"),
                rs.getString("autor_nombre"),
                rs.getString("autor_nacionalidad")
        );

        return new Libro(
                rs.getInt("id"),
                rs.getString("isbn"),
                rs.getString("titulo"),
                anioLibro,
                rs.getDouble("precio"),
                autor
        );
    }
}
