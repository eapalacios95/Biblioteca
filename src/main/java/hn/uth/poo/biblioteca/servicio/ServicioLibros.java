

/**
 *
 * @author Edwin
 */


package hn.uth.poo.biblioteca.servicio;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;

import java.time.Year;
import java.util.List;

public class ServicioLibros {

    private final LibroDAO libroDAO;
    private final AutorDAO autorDAO;

    public ServicioLibros(LibroDAO libroDAO, AutorDAO autorDAO) {
        this.libroDAO = libroDAO;
        this.autorDAO = autorDAO;
    }

    public void crear(Libro libro) {
        validarLibro(libro);
        validarISBNUnico(libro.getIsbn(), 0);
        validarAutor(libro.getAutor());

        libroDAO.insertar(libro);
    }

    public Libro buscarPorId(int id) {
        if (id <= 0) {
            throw new DatoInvalidoException(
                    "El ID del libro debe ser mayor que cero.");
        }

        Libro libro = libroDAO.buscarPorId(id);

        if (libro == null) {
            throw new DatoInvalidoException(
                    "No se encontró un libro con ese ID.");
        }

        return libro;
    }

    public List<Libro> listar() {
        return libroDAO.listar();
    }

    public List<Libro> listarPorAutor(int autorId) {
        if (autorId <= 0) {
            throw new DatoInvalidoException(
                    "El ID del autor debe ser mayor que cero.");
        }

        if (autorDAO.buscarPorId(autorId) == null) {
            throw new DatoInvalidoException(
                    "El autor indicado no existe.");
        }

        return libroDAO.listarPorAutor(autorId);
    }

    public void actualizar(Libro libro) {
        if (libro == null || libro.getId() <= 0) {
            throw new DatoInvalidoException(
                    "Debe indicar un libro válido para actualizar.");
        }

        buscarPorId(libro.getId());
        validarLibro(libro);
        validarISBNUnico(libro.getIsbn(), libro.getId());
        validarAutor(libro.getAutor());

        libroDAO.actualizar(libro);
    }

    public void eliminar(int id) {
        buscarPorId(id);
        libroDAO.eliminar(id);
    }

    private void validarLibro(Libro libro) {
        if (libro == null) {
            throw new DatoInvalidoException(
                    "Los datos del libro son obligatorios.");
        }

        if (libro.getIsbn() == null ||
                libro.getIsbn().trim().isEmpty()) {
            throw new DatoInvalidoException(
                    "El ISBN es obligatorio.");
        }

        if (libro.getTitulo() == null ||
                libro.getTitulo().trim().isEmpty()) {
            throw new DatoInvalidoException(
                    "El título del libro es obligatorio.");
        }

        if (libro.getAnio() != null) {
            int anioActual = Year.now().getValue();

            if (libro.getAnio() < 1450 ||
                    libro.getAnio() > anioActual) {
                throw new DatoInvalidoException(
                        "El año debe estar entre 1450 y " + anioActual + ".");
            }
        }

        if (libro.getPrecio() < 0) {
            throw new DatoInvalidoException(
                    "El precio no puede ser negativo.");
        }
    }

    private void validarISBNUnico(String isbn, int idActual) {
        for (Libro existente : libroDAO.listar()) {

            if (existente.getIsbn().equalsIgnoreCase(isbn.trim())
                    && existente.getId() != idActual) {

                throw new DatoInvalidoException(
                        "Ya existe un libro registrado con ese ISBN.");
            }
        }
    }

    private void validarAutor(Autor autor) {
        if (autor == null || autor.getId() <= 0) {
            throw new DatoInvalidoException(
                    "Debe seleccionar un autor válido.");
        }

        if (autorDAO.buscarPorId(autor.getId()) == null) {
            throw new DatoInvalidoException(
                    "El autor seleccionado no existe.");
        }
    }
}
