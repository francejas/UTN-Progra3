package dao;

import conexion.ConexionBD;
import modelo.Alumno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {

    public void insertarAlumno(Alumno a) {
        String sql = "INSERT INTO alumnos (nombre, apellido, edad, email) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getInstancia().getConexion();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, a.getNombre());
            st.setString(2, a.getApellido());
            st.setInt(3, a.getEdad());
            st.setString(4, a.getEmail());
            st.executeUpdate();
            System.out.println("Alumno insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar alumno: " + e.getMessage());
        }
    }

    public List<Alumno> listarAlumnos() {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumnos";
        try (Connection con = ConexionBD.getInstancia().getConexion();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                lista.add(new Alumno(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"), rs.getInt("edad"), rs.getString("email")));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }

    public void actualizarEdad(int id, int nuevaEdad) {
        String sql = "UPDATE alumnos SET edad = ? WHERE id = ?";
        try (Connection con = ConexionBD.getInstancia().getConexion();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, nuevaEdad);
            st.setInt(2, id);
            int filas = st.executeUpdate();
            if(filas > 0) System.out.println("Edad actualizada.");
            else System.out.println("No se encontró el alumno.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    public void eliminarAlumno(int id) {
        String sql = "DELETE FROM alumnos WHERE id = ?";
        try (Connection con = ConexionBD.getInstancia().getConexion();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            int filas = st.executeUpdate();
            if(filas > 0) System.out.println("Alumno eliminado.");
            else System.out.println("No se encontró el alumno.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    // Metodo auxiliar para verificar si un alumno existe antes de cargar su dirección
    public boolean existeAlumno(int id) {
        String sql = "SELECT id FROM alumnos WHERE id = ?";
        try (Connection con = ConexionBD.getInstancia().getConexion();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }
}
