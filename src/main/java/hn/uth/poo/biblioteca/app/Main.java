

/**
 *
 * @author Edwin
 */



package hn.uth.poo.biblioteca.app;

import hn.uth.poo.biblioteca.dao.impl.AutorDAOImpl;
import hn.uth.poo.biblioteca.dao.impl.LibroDAOImpl;
import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;
import hn.uth.poo.biblioteca.servicio.ServicioAutores;
import hn.uth.poo.biblioteca.servicio.ServicioLibros;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(
    System.in, java.nio.charset.StandardCharsets.UTF_8);

    private static ServicioAutores servicioAutores;
    private static ServicioLibros servicioLibros;

    public static void main(String[] args) {
        System.setOut(new java.io.PrintStream(
    System.out, true, java.nio.charset.StandardCharsets.UTF_8));

    System.setErr(new java.io.PrintStream(
    System.err, true, java.nio.charset.StandardCharsets.UTF_8));

        try {
            AutorDAO autorDAO = new AutorDAOImpl();
            LibroDAO libroDAO = new LibroDAOImpl();

            servicioAutores = new ServicioAutores(autorDAO, libroDAO);
            servicioLibros = new ServicioLibros(libroDAO, autorDAO);

            hn.uth.poo.biblioteca.dao.Conexion.inicializar();

            ejecutarMenu();

        } catch (Exception e) {
            System.out.println("Error al iniciar la aplicación: "
                    + e.getMessage());
        }
    }

    private static void ejecutarMenu() {

        int opcion;

        do {
            System.out.println("\n================================");
            System.out.println("       SISTEMA DE BIBLIOTECA");
            System.out.println("================================");
            System.out.println("1. Administrar autores");
            System.out.println("2. Administrar libros");
            System.out.println("3. Listar libros por autor");
            System.out.println("0. Salir");
            System.out.println("================================");

            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> menuAutores();
                    case 2 -> menuLibros();
                    case 3 -> listarLibrosPorAutor();
                    case 0 -> System.out.println(
                            "Gracias por utilizar el sistema.");
                    default -> System.out.println(
                            "Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    // =========================
    // MENU DE AUTORES
    // =========================

    private static void menuAutores() {

        int opcion;

        do {
            System.out.println("\n------ ADMINISTRAR AUTORES ------");
            System.out.println("1. Registrar autor");
            System.out.println("2. Listar autores");
            System.out.println("3. Buscar autor por ID");
            System.out.println("4. Actualizar autor");
            System.out.println("5. Eliminar autor");
            System.out.println("0. Regresar");

            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> registrarAutor();
                    case 2 -> listarAutores();
                    case 3 -> buscarAutor();
                    case 4 -> actualizarAutor();
                    case 5 -> eliminarAutor();
                    case 0 -> System.out.println("Regresando...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private static void registrarAutor() {

        System.out.println("\n--- REGISTRAR AUTOR ---");

        String nombre = leerTexto("Nombre: ");
        String nacionalidad = leerTexto("Nacionalidad: ");

        Autor autor = new Autor(nombre, nacionalidad);

        servicioAutores.crear(autor);

        System.out.println("Autor registrado correctamente.");
        System.out.println("ID asignado: " + autor.getId());
    }

    private static void listarAutores() {

        System.out.println("\n--- LISTA DE AUTORES ---");

        List<Autor> autores = servicioAutores.listar();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        for (Autor autor : autores) {
            System.out.println(autor);
        }
    }

    private static void buscarAutor() {

        int id = leerEntero("Ingrese el ID del autor: ");

        Autor autor = servicioAutores.buscarPorId(id);

        System.out.println("Autor encontrado:");
        System.out.println(autor);
    }

    private static void actualizarAutor() {

        int id = leerEntero("ID del autor que desea actualizar: ");

        Autor autor = servicioAutores.buscarPorId(id);

        System.out.println("Autor actual: " + autor);

        String nombre = leerTexto("Nuevo nombre: ");
        String nacionalidad = leerTexto("Nueva nacionalidad: ");

        autor.setNombre(nombre);
        autor.setNacionalidad(nacionalidad);

        servicioAutores.actualizar(autor);

        System.out.println("Autor actualizado correctamente.");
    }

    private static void eliminarAutor() {

        int id = leerEntero("ID del autor que desea eliminar: ");

        servicioAutores.eliminar(id);

        System.out.println("Autor eliminado correctamente.");
    }

    // =========================
    // MENU DE LIBROS
    // =========================

    private static void menuLibros() {

        int opcion;

        do {
            System.out.println("\n------ ADMINISTRAR LIBROS ------");
            System.out.println("1. Registrar libro");
            System.out.println("2. Listar libros");
            System.out.println("3. Buscar libro por ID");
            System.out.println("4. Actualizar libro");
            System.out.println("5. Eliminar libro");
            System.out.println("0. Regresar");

            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> registrarLibro();
                    case 2 -> listarLibros();
                    case 3 -> buscarLibro();
                    case 4 -> actualizarLibro();
                    case 5 -> eliminarLibro();
                    case 0 -> System.out.println("Regresando...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private static void registrarLibro() {

        System.out.println("\n--- REGISTRAR LIBRO ---");

        listarAutores();

        String isbn = leerTexto("ISBN: ");
        String titulo = leerTexto("Título: ");
        Integer anio = leerAnio();
        double precio = leerDecimal("Precio: ");
        int autorId = leerEntero("ID del autor: ");

        Autor autor = servicioAutores.buscarPorId(autorId);

        Libro libro = new Libro(
                isbn, titulo, anio, precio, autor);

        servicioLibros.crear(libro);

        System.out.println("Libro registrado correctamente.");
        System.out.println("ID asignado: " + libro.getId());
    }

    private static void listarLibros() {

        System.out.println("\n--- LISTA DE LIBROS ---");

        List<Libro> libros = servicioLibros.listar();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        for (Libro libro : libros) {
            System.out.println(libro);
        }
    }

    private static void buscarLibro() {

        int id = leerEntero("Ingrese el ID del libro: ");

        Libro libro = servicioLibros.buscarPorId(id);

        System.out.println("Libro encontrado:");
        System.out.println(libro);
    }

    private static void actualizarLibro() {

        int id = leerEntero("ID del libro que desea actualizar: ");

        Libro libro = servicioLibros.buscarPorId(id);

        System.out.println("Libro actual: " + libro);

        listarAutores();

        String isbn = leerTexto("Nuevo ISBN: ");
        String titulo = leerTexto("Nuevo título: ");
        Integer anio = leerAnio();
        double precio = leerDecimal("Nuevo precio: ");
        int autorId = leerEntero("Nuevo ID del autor: ");

        Autor autor = servicioAutores.buscarPorId(autorId);

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setPrecio(precio);
        libro.setAutor(autor);

        servicioLibros.actualizar(libro);

        System.out.println("Libro actualizado correctamente.");
    }

    private static void eliminarLibro() {

        int id = leerEntero("ID del libro que desea eliminar: ");

        servicioLibros.eliminar(id);

        System.out.println("Libro eliminado correctamente.");
    }

    // =========================
    // LIBROS POR AUTOR
    // =========================

    private static void listarLibrosPorAutor() {

        System.out.println("\n--- LIBROS POR AUTOR ---");

        listarAutores();

        int autorId = leerEntero("Ingrese el ID del autor: ");

        List<Libro> libros =
                servicioLibros.listarPorAutor(autorId);

        if (libros.isEmpty()) {
            System.out.println("Este autor no tiene libros registrados.");
            return;
        }

        System.out.println("\nLibros del autor:");

        for (Libro libro : libros) {
            System.out.println(libro);
        }
    }

    // =========================
    // METODOS PARA LEER DATOS
    // =========================

    private static String leerTexto(String mensaje) {

        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private static double leerDecimal(String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);

                String valor = scanner.nextLine().trim()
                        .replace(',', '.');

                return Double.parseDouble(valor);

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número decimal válido.");
            }
        }
    }

    private static Integer leerAnio() {

        while (true) {
            System.out.print(
                    "Año de publicación (Enter si lo desconoce): ");

            String valor = scanner.nextLine().trim();

            if (valor.isEmpty()) {
                return null;
            }

            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un año válido.");
            }
        }
    }
}

