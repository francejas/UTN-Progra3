package repositorios.interfaces;

import excepciones.IdentificadorDuplicadoException;
import java.util.List;

public interface RepositorioGenerico<T> {
    void agregar(T item) throws IdentificadorDuplicadoException;
    void eliminarPorId(String id);
    T buscarPorId(String id);
    List<T> obtenerTodosOrdenadosPorTitulo();
    List<T> filtrarPorGenero(String genero);
}