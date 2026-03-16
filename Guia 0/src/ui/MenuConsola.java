package ui;

import excepciones.IdentificadorDuplicadoException;
import modelo.implementaciones.Expansion;
import modelo.implementaciones.Juego;
import modelo.interfaces.Media;
import repositorios.interfaces.RepositorioGenerico;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuConsola {
    private RepositorioGenerico<Media> repositorio;
    private Scanner scanner;

    public MenuConsola(RepositorioGenerico<Media> repositorio) {
        this.repositorio = repositorio;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GESTOR DE BIBLIOTECA DE MEDIA ---");
            System.out.println("1. Agregar Juego");
            System.out.println("2. Agregar Expansión");
            System.out.println("3. Eliminar por ID");
            System.out.println("4. Mostrar todos (Ordenados por Título)");
            System.out.println("5. Filtrar por Género");
            System.out.println("6. Modificar un atributo por ID");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                procesarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1: agregarJuego(); break;
            case 2: agregarExpansion(); break;
            case 3: eliminarObjeto(); break;
            case 4: mostrarTodos(); break;
            case 5: filtrarPorGenero(); break;
            case 6: modificarAtributo(); break;
            case 0: System.out.println("Saliendo del sistema..."); break;
            default: System.out.println("Opción no válida.");
        }
    }

    private void agregarJuego() {
        try {
            System.out.print("ID: "); String id = scanner.nextLine();
            System.out.print("Título: "); String titulo = scanner.nextLine();
            System.out.print("Creador: "); String creador = scanner.nextLine();
            System.out.print("Género: "); String genero = scanner.nextLine();
            System.out.print("Versión (número positivo): "); int version = Integer.parseInt(scanner.nextLine());

            Juego nuevoJuego = new Juego(id, titulo, creador, genero, version);
            repositorio.agregar(nuevoJuego);
            System.out.println("Juego agregado con éxito.");
        } catch (IdentificadorDuplicadoException | IllegalArgumentException e) {
            System.out.println("Error al agregar: " + e.getMessage());
        }
    }

    private void agregarExpansion() {
        try {
            System.out.print("ID: "); String id = scanner.nextLine();
            System.out.print("Título: "); String titulo = scanner.nextLine();
            System.out.print("Creador: "); String creador = scanner.nextLine();
            System.out.print("Género: "); String genero = scanner.nextLine();
            System.out.print("Fecha de lanzamiento (YYYY-MM-DD): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());

            Expansion nuevaExpansión = new Expansion(id, titulo, creador, genero, fecha);
            repositorio.agregar(nuevaExpansión);
            System.out.println("Expansión agregada con éxito.");
        } catch (IdentificadorDuplicadoException | IllegalArgumentException e) {
            System.out.println("Error al agregar: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha inválido.");
        }
    }

    private void eliminarObjeto() {
        System.out.print("Ingrese ID a eliminar: ");
        String id = scanner.nextLine();
        try {
            repositorio.eliminarPorId(id);
            System.out.println("Elemento eliminado.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarTodos() {
        List<Media> lista = repositorio.obtenerTodosOrdenadosPorTitulo();
        if (lista.isEmpty()) {
            System.out.println("La colección está vacía.");
            return;
        }
        for (Media m : lista) {
            System.out.println(m);
        }
    }

    private void filtrarPorGenero() {
        System.out.print("Ingrese el género a filtrar: ");
        String genero = scanner.nextLine();
        List<Media> filtrados = repositorio.filtrarPorGenero(genero);

        if (filtrados.isEmpty()) {
            System.out.println("No se encontraron resultados para ese género.");
        } else {
            for (Media m : filtrados) {
                System.out.println(m);
            }
        }
    }

    private void modificarAtributo() {
        System.out.print("Ingrese el ID del elemento a modificar: ");
        String id = scanner.nextLine();
        Media item = repositorio.buscarPorId(id);

        if (item == null) {
            System.out.println("Elemento no encontrado.");
            return;
        }

        System.out.println("Atributos: 1. Título | 2. Creador | 3. Género");
        System.out.print("¿Qué atributo desea modificar? ");
        try {
            int opc = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese el nuevo valor: ");
            String nuevoValor = scanner.nextLine();

            switch (opc) {
                case 1: item.setTitulo(nuevoValor); break;
                case 2: item.setCreador(nuevoValor); break;
                case 3: item.setGenero(nuevoValor); break;
                default: System.out.println("Opción no válida."); return;
            }
            System.out.println("Atributo modificado exitosamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un número válido.");
        }
    }
}