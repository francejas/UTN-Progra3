import conexion.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection conexion = ConexionBD.getInstancia().getConexion();

        try {
            // Consulta SQL
            String sql = "SELECT id, nombre FROM alumnos";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nombre: " + rs.getString("nombre"));
            }

            // Cerrar ResultSet y Statement
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar conexión
            ConexionBD.getInstancia().cerrarConexion();
        }

    }
}