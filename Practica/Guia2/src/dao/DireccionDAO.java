package dao;

import conexion.ConexionBD;
import modelo.Direccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DireccionDAO {

    public void insertarDireccion(Direccion d) {
        String sql = "INSERT INTO direcciones (calle, altura, alumno_id) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.getInstancia().getConexion()) {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, d.getCalle());
            st.setInt(2, d.getAltura());
            st.setInt(3, d.getAlumno_id());

            st.executeUpdate();
            System.out.println("Direccion insertada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al insertar dirección: " + e.getMessage());
        }
    }

    public List<Direccion> listarDireccionesPorAlumno(int alumnoId) {

        List<Direccion> lista = new ArrayList<>();

        String sql = "SELECT * FROM direcciones WHERE alumno_id = ?";

        try (Connection con = ConexionBD.getInstancia().getConexion();

             PreparedStatement st = con.prepareStatement(sql)) {


            st.setInt(1, alumnoId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                lista.add(new Direccion(rs.getInt("id"), rs.getString("calle"), rs.getInt("altura"), rs.getInt("alumno_id")));

            }

        } catch (SQLException e) {

            System.out.println("Error al listar direcciones: " + e.getMessage());

        }
        return lista;
    }
}
