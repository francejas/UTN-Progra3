import modelo.interfaces.Media;
import repositorios.implementaciones.RepositorioMediaImpl;
import repositorios.interfaces.RepositorioGenerico;
import ui.MenuConsola;

public class Main {
    public static void main(String[] args) {
        // Inicializamos las dependencias
        RepositorioGenerico<Media> repositorio = new RepositorioMediaImpl<>();
        MenuConsola menu = new MenuConsola(repositorio);

        // Arrancamos la UI
        menu.iniciar();
    }
}