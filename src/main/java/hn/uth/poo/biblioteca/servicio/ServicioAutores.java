

/**
 *
 * @author Edwin
 */



package hn.uth.poo.biblioteca.servicio;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.modelo.Autor;

import java.util.List;

public class ServicioAutores {

    private final AutorDAO autorDAO;
    private final LibroDAO libroDAO;

    public ServicioAutores(AutorDAO autorDAO, LibroDAO libroDAO) {
        this.autorDAO = autorDAO;
        this.libroDAO = libroDAO;
    }

    public void crear(Autor autor) {
        validarAutor(autor);
        autorDAO.insertar(autor);
    }

    public Autor buscarPorId(int id) {
        if (id <= 0) {
            throw new DatoInvalidoException(
                    "El ID del autor debe ser mayor que cero.");
        }

        Autor autor = autorDAO.buscarPorId(id);

        if (autor == null) {
            throw new DatoInvalidoException(
                    "No se encontró un autor con ese ID.");
        }

        return autor;
    }

    public List<Autor> listar() {
        return autorDAO.listar();
    }

    public void actualizar(Autor autor) {
        if (autor == null || autor.getId() <= 0) {
            throw new DatoInvalidoException(
                    "Debe indicar un autor válido para actualizar.");
        }

        buscarPorId(autor.getId());
        validarAutor(autor);

        autorDAO.actualizar(autor);
    }

    public void eliminar(int id) {
        Autor autor = buscarPorId(id);

        if (!libroDAO.listarPorAutor(autor.getId()).isEmpty()) {
            throw new DatoInvalidoException(
                    "No se puede eliminar el autor porque tiene libros registrados.");
        }

        autorDAO.eliminar(id);
    }

    private void validarAutor(Autor autor) {
        if (autor == null) {
            throw new DatoInvalidoException(
                    "Los datos del autor son obligatorios.");
        }

        if (autor.getNombre() == null ||
                autor.getNombre().trim().isEmpty()) {
            throw new DatoInvalidoException(
                    "El nombre del autor es obligatorio.");
        }
    }
}

