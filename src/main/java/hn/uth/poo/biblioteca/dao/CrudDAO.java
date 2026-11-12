
/**
 *
 * @author Edwin
 */


package hn.uth.poo.biblioteca.dao;

import java.util.List;

public interface CrudDAO<T, ID> {

    void insertar(T entidad);

    T buscarPorId(ID id);

    List<T> listar();

    void actualizar(T entidad);

    void eliminar(ID id);

}
