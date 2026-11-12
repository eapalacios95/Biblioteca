# Sistema de Biblioteca

## 1. Información del estudiante

- **Nombre completo:** Edwin Alexis Palacios
- **Número de cuenta:** 202310060406
- **Institución:** UTH
- **Proyecto:** Sistema de Biblioteca (Examen 2do Parcial
- **Lenguaje:** Java

## 2. Descripción del proyecto

Sistema de gestión de biblioteca desarrollado en Java, utilizando Maven y SQLite. Permite administrar autores y libros mediante operaciones CRUD (crear, consultar, actualizar y eliminar).

El sistema utiliza una relación de uno a muchos: un autor puede tener varios libros y cada libro pertenece a un autor.

## 3. Tecnologías utilizadas

- Java JDK 26.
- Apache Maven 3.9.16.
- SQLite.
- SQLite JDBC.
- Apache NetBeans IDE 31.
- Git y GitHub.

## 4. Funcionalidades

### Administración de autores
- Registrar autores.
- Listar autores.
- Buscar autores por ID.
- Actualizar autores.
- Eliminar autores.
- Evitar eliminar autores que tengan libros asociados.

### Administración de libros
- Registrar libros.
- Listar libros.
- Buscar libros por ID.
- Actualizar libros.
- Eliminar libros.
- Consultar libros por autor.

### Validaciones
- Validación de campos obligatorios.
- Validación de ISBN único.
- Validación del año de publicación.
- Validación de precios.
- Comprobación de existencia del autor.
- Manejo de excepciones.

## 5. Estructura del proyecto

```text
Biblioteca/
├── src/
│   └── main/
│       └── java/
│           └── hn/uth/poo/biblioteca/
│               ├── app/
│               ├── dao/
│               │   └── impl/
│               ├── excepciones/
│               ├── modelo/
│               └── servicio/
├── biblioteca.sql
├── .gitignore
├── pom.xml
└── README.md
```

## 6. Base de datos

La aplicación utiliza SQLite y crea automáticamente las tablas al iniciar.

Las tablas principales son:

- **autores:** almacena la información de los autores.
- **libros:** almacena los libros y su relación con los autores.

El archivo `biblioteca.sql` contiene las instrucciones SQL para crear la estructura de la base de datos.

## 7. Ejecución del proyecto

### Desde Apache NetBeans

1. Abrir el proyecto Biblioteca.
2. Verificar las dependencias Maven.
3. Ejecutar el proyecto utilizando la clase principal `hn.uth.poo.biblioteca.app.Main`.

### Desde Windows CMD

También se incluye el archivo `EjecutarBiblioteca.bat`, que permite iniciar el sistema desde una ventana independiente de Windows.

1. Ubicar el archivo `EjecutarBiblioteca.bat`.
2. Ejecutarlo con doble clic.
3. Esperar a que Maven inicie la aplicación.

## 8. Evidencias

Se incluyen capturas de pantalla que demuestran:

- Registro y consulta de autores.
- Registro y consulta de libros.
- Actualización de libros.
- Búsqueda de libros por ID.
- Consulta de libros por autor.
- Validación al eliminar autores con libros asociados.
- Eliminación de libros y autores.
- Ejecución del sistema desde CMD.

## 9. Autor

Proyecto académico desarrollado como práctica de programación orientada a objetos, acceso a bases de datos SQLite y patrón DAO.
