package dao;

import conexion.ConexionBD;
import modelo.Cuenta;
import modelo.Permiso;
import modelo.Usuario;
import modelo.TipoCuenta;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuarioDAO {

    /**
     * Punto 2: Registro completo con Transacción.
     * Inserta Usuario, genera Credenciales automáticas y crea Caja de Ahorro.
     */
    public void registrarUsuarioCompleto(Usuario usuario, String password) {
        String sqlUsuario = "INSERT INTO usuarios (nombre, apellido, dni, email) VALUES (?,?,?,?)";
        String sqlCredencial = "INSERT INTO credenciales (id_usuario, username, password, permiso) VALUES (?, ?, ?, ?)";
        String sqlCuenta = "INSERT INTO cuentas (id_usuario, tipo, saldo) VALUES (?, ?, ?)";

        Connection con = null;
        PreparedStatement stUsuario = null;
        PreparedStatement stCredencial = null;
        PreparedStatement stCuenta = null;
        ResultSet rsKey = null;

        try {
            con = ConexionBD.getInstancia().getConexion();
            con.setAutoCommit(false); // Iniciamos Transacción

            // 1. Insertar Usuario
            stUsuario = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
            stUsuario.setString(1, usuario.getNombre());
            stUsuario.setString(2, usuario.getApellido());
            stUsuario.setString(3, usuario.getDni());
            stUsuario.setString(4, usuario.getEmail());
            stUsuario.executeUpdate();

            // 2. Recuperar ID generado
            rsKey = stUsuario.getGeneratedKeys();
            int idGenerado = -1;
            if (rsKey.next()) {
                idGenerado = rsKey.getInt(1);
            } else {
                throw new SQLException("Error: No se pudo obtener el ID del usuario.");
            }

            // 3. Generar Username automático (Lógica Opción A)
            String inicialNombre = usuario.getNombre().trim().toLowerCase().substring(0, 1);
            String apellidoLimpio = usuario.getApellido().toLowerCase().replaceAll("\\s+", "");
            String dniFull = usuario.getDni().trim();
            String ultimosTresDni = dniFull.length() >= 3 ? dniFull.substring(dniFull.length() - 3) : dniFull;
            String usernameArmado = inicialNombre + apellidoLimpio + ultimosTresDni;

            // 4. Insertar Credencial
            stCredencial = con.prepareStatement(sqlCredencial);
            stCredencial.setInt(1, idGenerado);
            stCredencial.setString(2, usernameArmado);
            stCredencial.setString(3, password);
            stCredencial.setString(4, Permiso.CLIENTE.name());
            stCredencial.executeUpdate();

            // 5. Insertar Cuenta Inicial (CAJA_AHORRO)
            stCuenta = con.prepareStatement(sqlCuenta);
            stCuenta.setInt(1, idGenerado);
            stCuenta.setString(2, TipoCuenta.CAJA_AHORRO.name());
            stCuenta.setBigDecimal(3, BigDecimal.ZERO);
            stCuenta.executeUpdate();

            con.commit(); // Confirmamos todo
            System.out.println("✅ Registro exitoso. Su usuario es: " + usernameArmado);

        } catch (SQLException e) {
            System.err.println("❌ Error en el registro. Deshaciendo cambios...");
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
        } finally {
            try {
                if (rsKey != null) rsKey.close();
                if (stUsuario != null) stUsuario.close();
                if (stCredencial != null) stCredencial.close();
                if (stCuenta != null) stCuenta.close();
                if (con != null) con.setAutoCommit(true);
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    /**
     * Punto 3: Inicio de Sesión.
     * Recupera los datos del usuario y su PERMISO mediante un INNER JOIN.
     */
    public Optional<Usuario> iniciarSesion(String username, String password) {
        // Agregamos c.permiso a la consulta
        String sql = "SELECT u.*, c.permiso FROM usuarios u " +
                "INNER JOIN credenciales c ON u.id_usuario = c.id_usuario " +
                "WHERE c.username = ? AND c.password = ?";

        Connection con = ConexionBD.getInstancia().getConexion();

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            st.setString(2, password);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Usuario usuarioLogeado = new Usuario();
                    usuarioLogeado.setIdUsuario(rs.getInt("id_usuario"));
                    usuarioLogeado.setNombre(rs.getString("nombre"));
                    usuarioLogeado.setApellido(rs.getString("apellido"));
                    usuarioLogeado.setDni(rs.getString("dni"));
                    usuarioLogeado.setEmail(rs.getString("email"));
                    usuarioLogeado.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());

                    // Seteamos el permiso para poder usarlo en las validaciones de otros puntos
                    usuarioLogeado.setPermiso(Permiso.valueOf(rs.getString("permiso")));

                    return Optional.of(usuarioLogeado);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Punto 4: Listar todos los usuarios.
     * El control de permisos se debe hacer antes de llamar a este método.
     */
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        Connection con = ConexionBD.getInstancia().getConexion();

        try (PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setDni(rs.getString("dni"));
                u.setEmail(rs.getString("email"));
                u.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
                usuarios.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public Optional<Usuario> buscarUsuario(String terminoBusqueda) {
        List<Usuario> listaUsuarios = listarTodos();

        return listaUsuarios.stream()
                .filter(u -> u.getDni().equals(terminoBusqueda) || u.getEmail().equals(terminoBusqueda))
                .findFirst();
    }

    public void actualizarDatosPersonales(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre=?, apellido=?, email=? WHERE id_usuario=?";

        // Pedimos la conexión al Singleton pero NO la metemos en el try con recursos
        Connection con = ConexionBD.getInstancia().getConexion();

        // Solo los recursos que queremos que se cierren van aquí adentro
        try (PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, usuario.getNombre());
            st.setString(2, usuario.getApellido());
            st.setString(3, usuario.getEmail());
            st.setInt(4, usuario.getIdUsuario());

            int filas = st.executeUpdate();
            if (filas > 0) {
                System.out.println("✅ Datos del usuario actualizados correctamente.");
            } else {
                System.out.println("❌ No se encontró el usuario solicitado.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    public void eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        Connection con = ConexionBD.getInstancia().getConexion();

        try (PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, idUsuario);
            int filas = st.executeUpdate();

            if (filas > 0) {
                System.out.println("✅ Usuario, credenciales y cuentas eliminados correctamente.");
            } else {
                System.out.println("❌ No se encontró un usuario con ese ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error al intentar eliminar el usuario: " + e.getMessage());
        }
    }

    public List<Cuenta> listarTodasLasCuentas () {
        List<Cuenta> listaCuenta = new ArrayList<>();
        String sql = "SELECT * FROM cuentas";
        Connection con = ConexionBD.getInstancia().getConexion();

        try (PreparedStatement st = con.prepareStatement(sql);
        ResultSet rs = st.executeQuery()){
            while (rs.next()){
                Cuenta cuenta = new Cuenta();
                cuenta.setIdCuenta(rs.getInt("id_cuenta"));
                cuenta.setIdUsuario(rs.getInt("id_usuario"));
                cuenta.setTipo(TipoCuenta.valueOf(rs.getString("tipo")));
                cuenta.setSaldo(rs.getBigDecimal("saldo"));
                cuenta.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
                listaCuenta.add(cuenta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCuenta;

    }

    public List<Cuenta> listarCuentasPorUsuario (int idUsuario){
        return listarTodasLasCuentas().stream().filter(p->p.getIdUsuario()==idUsuario).collect(Collectors.toList());
    }

    



}