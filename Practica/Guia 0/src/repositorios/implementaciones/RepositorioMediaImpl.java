package repositorios.implementaciones;

import excepciones.IdentificadorDuplicadoException;
import modelo.interfaces.Media;
import repositorios.interfaces.RepositorioGenerico;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RepositorioMediaImpl<T extends Media> implements RepositorioGenerico<T> {
    // Usamos HashSet para evitar duplicados de forma eficiente
    private Set<T> coleccion;

    public RepositorioMediaImpl() {
        this.coleccion = new HashSet<>();
    }

    @Override
    public void agregar(T item) throws IdentificadorDuplicadoException {
        // Verificar duplicado por ID
        if (buscarPorId(item.getId()) != null) {
            throw new IdentificadorDuplicadoException("El identificador '" + item.getId() + "' ya existe en la colección.");
        }
        coleccion.add(item);
    }

    @Override
    public void eliminarPorId(String id) {
        T item = buscarPorId(id);
        if (item != null) {
            coleccion.remove(item);
        } else {
            throw new IllegalArgumentException("No se encontró ningún elemento con el ID especificado.");
        }
    }

    @Override
    public T buscarPorId(String id) {
        for (T item : coleccion) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public List<T> obtenerTodosOrdenadosPorTitulo() {
        List<T> listaOrdenada = new ArrayList<>(coleccion);
        // Uso de Comparator para ordenar por título
        listaOrdenada.sort(Comparator.comparing(Media::getTitulo, String.CASE_INSENSITIVE_ORDER));
        return listaOrdenada;
    }

    @Override
    public List<T> filtrarPorGenero(String genero) {
        return coleccion.stream()
                .filter(item -> item.getGenero().equalsIgnoreCase(genero))
                .collect(Collectors.toList());
    }
}