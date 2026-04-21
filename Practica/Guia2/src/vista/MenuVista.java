package vista;

import java.util.Scanner;

public class MenuVista {
    private Scanner scanner;

    public MenuVista() {
        this.scanner = new Scanner(System.in);
    }

    public int mostrarMenuPrincipal() {
        System.out.println("\n--- GESTIÓN DE ALUMNOS ---");
        System.out.println("1. Agregar Alumno");
        System.out.println("2. Agregar Dirección a un Alumno");
        System.out.println("3. Listar Alumnos");
        System.out.println("4. Ver Direcciones de un Alumno");
        System.out.println("5. Actualizar Edad");
        System.out.println("6. Eliminar Alumno");
        System.out.println("7. Eliminar Dirección");
        System.out.println("0. Salir");
        System.out.print("Elija una opción: ");
        return scanner.nextInt();
    }

    public String pedirString(String mensaje) {
        System.out.print(mensaje);
        scanner.nextLine();
        return scanner.nextLine();
    }

    public int pedirInt(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextInt();
    }

    public void mostrarMensaje(String msj) {
        System.out.println(msj);
    }
}