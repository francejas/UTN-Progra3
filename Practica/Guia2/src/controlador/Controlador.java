package controlador;

import dao.AlumnoDAO;
import dao.DireccionDAO;
import modelo.Alumno;
import modelo.Direccion;
import vista.MenuVista;

import java.util.List;

public class Controlador {
    private MenuVista vista;
    private AlumnoDAO alumnoDAO;
    private DireccionDAO direccionDAO;

    public Controlador() {
        this.vista = new MenuVista();
        this.alumnoDAO = new AlumnoDAO();
        this.direccionDAO = new DireccionDAO();
    }

    public void iniciar() {
        int opcion;
        do {
            opcion = vista.mostrarMenuPrincipal();
            procesarOpcion(opcion);
        } while (opcion != 0);
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                String nombre = vista.pedirString("Nombre: ");
                String apellido = vista.pedirString("Apellido: ");
                int edad = vista.pedirInt("Edad: ");
                String email = vista.pedirString("Email: ");
                alumnoDAO.insertarAlumno(new Alumno(nombre, apellido, edad, email));
                break;
            case 2:
                int idAlu = vista.pedirInt("ID del Alumno: ");
                if (alumnoDAO.existeAlumno(idAlu)) {
                    String calle = vista.pedirString("Calle: ");
                    int altura = vista.pedirInt("Altura: ");
                    direccionDAO.insertarDireccion(new Direccion(calle, altura, idAlu));
                } else {
                    vista.mostrarMensaje("El alumno no existe.");
                }
                break;
            case 3:
                List<Alumno> alumnos = alumnoDAO.listarAlumnos();
                alumnos.forEach(a -> vista.mostrarMensaje(a.toString()));
                break;
            case 4:
                int idBusqueda = vista.pedirInt("ID del Alumno: ");
                List<Direccion> dirs = direccionDAO.listarDireccionesPorAlumno(idBusqueda);
                if(dirs.isEmpty()) vista.mostrarMensaje("No tiene direcciones.");
                else dirs.forEach(d -> vista.mostrarMensaje(d.toString()));
                break;
            case 5:
                int idAct = vista.pedirInt("ID del Alumno a actualizar: ");
                int nuevaEdad = vista.pedirInt("Nueva Edad: ");
                alumnoDAO.actualizarEdad(idAct, nuevaEdad);
                break;
            case 6:
                int idEliminar = vista.pedirInt("ID del Alumno a eliminar: ");
                alumnoDAO.eliminarAlumno(idEliminar);
                break;
            case 7:
                int idDirEliminar = vista.pedirInt("ID de la Dirección a eliminar: ");
                direccionDAO.eliminarDireccion(idDirEliminar);
                break;
            case 0:
                conexion.ConexionBD.getInstancia().cerrarConexion();
                vista.mostrarMensaje("Saliendo del sistema...");
                break;
            default:
                vista.mostrarMensaje("Opción no válida.");
        }
    }
}