package vista;

import controlador.BancoControlador;
import excepciones.NoAutorizadoException;
import modelo.Usuario;

import java.math.BigDecimal;
import java.util.Scanner;

public class MenuVista {

    private BancoControlador controlador;
    private Scanner scanner;

    public MenuVista() {
        this.controlador = new BancoControlador();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            if (controlador.getUsuarioLogueado() == null) {
                mostrarMenuLogin();
            } else {
                salir = mostrarMenuPrincipal();
            }
        }
        System.out.println("Gracias por utilizar nuestro sistema.");
        scanner.close();
    }

    private void mostrarMenuLogin() {
        System.out.println("\n--- BIENVENIDO AL SISTEMA BANCARIO ---");
        System.out.println("1. Iniciar Sesión");
        System.out.println("2. Registrarse");
        System.out.println("3. Salir del sistema");
        System.out.print("Seleccione una opción: ");

        String opcion = scanner.nextLine();

        switch (opcion) {
            case "1":
                System.out.print("Username: ");
                String user = scanner.nextLine();
                System.out.print("Password: ");
                String pass = scanner.nextLine();
                if (controlador.iniciarSesion(user, pass)) {
                    System.out.println("✅ Sesión iniciada correctamente. Hola, " + controlador.getUsuarioLogueado().getNombre());
                } else {
                    System.out.println("❌ Credenciales incorrectas.");
                }
                break;
            case "2":
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Apellido: ");
                String apellido = scanner.nextLine();
                System.out.print("DNI: ");
                String dni = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Cree un password: ");
                String nuevaPass = scanner.nextLine();

                Usuario nuevo = new Usuario();
                nuevo.setNombre(nombre);
                nuevo.setApellido(apellido);
                nuevo.setDni(dni);
                nuevo.setEmail(email);

                controlador.registrarUsuario(nuevo, nuevaPass);
                break;
            case "3":
                System.exit(0);
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private boolean mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ ÚNICO OPERATIVO ---");
        System.out.println("Rol actual: " + controlador.getUsuarioLogueado().getPermiso());
        System.out.println("1. Listar Usuarios (Punto 4)");
        System.out.println("2. Ver mis cuentas (Punto 8)");
        System.out.println("3. Realizar Depósito (Punto 10)");
        System.out.println("4. Realizar Transferencia (Punto 11)");
        System.out.println("5. Ver Estadísticas del Banco (Puntos 12 y 13)");
        System.out.println("6. Ver Ranking de Saldos (Puntos 14 y 15)");
        System.out.println("9. Cerrar Sesión");
        System.out.print("Seleccione una opción: ");

        String opcion = scanner.nextLine();

        try {
            switch (opcion) {
                case "1":
                    controlador.listarTodosLosUsuarios().forEach(System.out::println);
                    break;
                case "2":
                    controlador.listarCuentas(controlador.getUsuarioLogueado().getIdUsuario()).forEach(System.out::println);
                    break;
                case "3":
                    System.out.print("ID de la cuenta a depositar: ");
                    int idCuentaDep = Integer.parseInt(scanner.nextLine());
                    System.out.print("Monto: ");
                    BigDecimal montoDep = new BigDecimal(scanner.nextLine());
                    controlador.depositar(idCuentaDep, montoDep);
                    break;
                case "4":
                    System.out.print("ID Cuenta Origen: ");
                    int idOrig = Integer.parseInt(scanner.nextLine());
                    System.out.print("ID Cuenta Destino: ");
                    int idDest = Integer.parseInt(scanner.nextLine());
                    System.out.print("Monto a transferir: ");
                    BigDecimal montoTrans = new BigDecimal(scanner.nextLine());
                    controlador.transferir(idOrig, idDest, montoTrans);
                    break;
                case "5":
                    System.out.println("Usuarios por Permiso: " + controlador.obtenerEstadisticasPermisos());
                    System.out.println("Cuentas por Tipo: " + controlador.obtenerEstadisticasCuentas());
                    break;
                case "6":
                    System.out.println("Top Usuario: " + controlador.obtenerUsuarioMayorSaldo());
                    System.out.println("Ranking Completo:");
                    controlador.rankingUsuariosPorSaldo().forEach(System.out::println);
                    break;
                case "9":
                    controlador.cerrarSesion();
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } catch (NoAutorizadoException e) {
            // Acá atrapamos el error de permisos y mostramos el mensaje sin romper el programa
            System.out.println("\n⛔ ERROR DE SEGURIDAD: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("\n❌ Error: Debe ingresar un valor numérico válido.");
        }

        return false;
    }
}