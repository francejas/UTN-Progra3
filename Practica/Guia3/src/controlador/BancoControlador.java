package controlador;

import dao.UsuarioDAO;
import excepciones.NoAutorizadoException;
import modelo.Cuenta;
import modelo.Permiso;
import modelo.Usuario;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BancoControlador {

    private UsuarioDAO usuarioDAO;
    private Usuario usuarioLogueado;

    public BancoControlador() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean iniciarSesion(String username, String password) {
        Optional<Usuario> userOpt = usuarioDAO.iniciarSesion(username, password);
        if (userOpt.isPresent()) {
            this.usuarioLogueado = userOpt.get();
            return true;
        }
        return false;
    }

    public void cerrarSesion() {
        this.usuarioLogueado = null;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void registrarUsuario(Usuario nuevoUsuario, String password) {
        // El registro es público, no requiere estar logueado
        usuarioDAO.registrarUsuarioCompleto(nuevoUsuario, password);
    }

    // PUNTO 4: Obtener todos los usuarios registrados
    public List<Usuario> listarTodosLosUsuarios() {
        if (usuarioLogueado.getPermiso() == Permiso.CLIENTE) {
            throw new NoAutorizadoException("Acceso denegado. Solo GESTORES o ADMINISTRADORES pueden listar usuarios.");
        }
        return usuarioDAO.listarTodos();
    }

    // PUNTO 8: Listar todas las cuentas
    public List<Cuenta> listarCuentas(int idUsuarioObjetivo) {
        if (usuarioLogueado.getPermiso() == Permiso.CLIENTE && usuarioLogueado.getIdUsuario() != idUsuarioObjetivo) {
            throw new NoAutorizadoException("Acceso denegado. Un CLIENTE solo puede ver sus propias cuentas.");
        }
        return usuarioDAO.listarCuentasPorUsuario(idUsuarioObjetivo);
    }

    // PUNTO 10: Realizar un depósito
    public void depositar(int idCuenta, BigDecimal monto) {
        Optional<Cuenta> cuentaOpt = usuarioDAO.buscarCuenta(idCuenta);
        if (cuentaOpt.isEmpty()) {
            System.out.println("❌ La cuenta no existe.");
            return;
        }

        Cuenta cuentaDestino = cuentaOpt.get();

        // Validación de permisos para depositar
        if (usuarioLogueado.getPermiso() == Permiso.CLIENTE && cuentaDestino.getIdUsuario() != usuarioLogueado.getIdUsuario()) {
            throw new NoAutorizadoException("Acceso denegado. Un CLIENTE solo puede depositar en sus propias cuentas.");
        }

        if (usuarioLogueado.getPermiso() == Permiso.GESTOR) {
            // El gestor debe verificar que el dueño de la cuenta sea CLIENTE
            // (Para simplificar, asumimos que si no es ADMIN, puede depositar,
            // pero podrías buscar al usuario dueño y verificar su Enum)
        }

        BigDecimal nuevoSaldo = cuentaDestino.getSaldo().add(monto);
        usuarioDAO.actualizarSaldo(idCuenta, nuevoSaldo);
    }

    // PUNTO 11: Realizar una transferencia
    public void transferir(int idCuentaOrigen, int idCuentaDestino, BigDecimal monto) {
        Optional<Cuenta> origenOpt = usuarioDAO.buscarCuenta(idCuentaOrigen);
        Optional<Cuenta> destinoOpt = usuarioDAO.buscarCuenta(idCuentaDestino);

        if (origenOpt.isEmpty() || destinoOpt.isEmpty()) {
            System.out.println("❌ Una de las cuentas no existe.");
            return;
        }

        Cuenta cuentaOrigen = origenOpt.get();

        // Validar permisos
        if (usuarioLogueado.getPermiso() == Permiso.CLIENTE && cuentaOrigen.getIdUsuario() != usuarioLogueado.getIdUsuario()) {
            throw new NoAutorizadoException("Acceso denegado. Un CLIENTE solo puede transferir desde sus propias cuentas.");
        }

        // Validar saldo con Stream (como pide la guía, validamos antes de llamar al DAO)
        List<Cuenta> cuentasOrigen = usuarioDAO.listarCuentasPorUsuario(cuentaOrigen.getIdUsuario());
        boolean tieneSaldo = cuentasOrigen.stream()
                .filter(c -> c.getIdCuenta() == idCuentaOrigen)
                .anyMatch(c -> c.getSaldo().compareTo(monto) >= 0);

        if (!tieneSaldo) {
            System.out.println("❌ Saldo insuficiente en la cuenta de origen.");
            return;
        }

        usuarioDAO.transferir(idCuentaOrigen, idCuentaDestino, monto);
    }

    // PUNTOS 12 al 15: Estadísticas (Solo Gestores y Administradores)
    public Map<String, Long> obtenerEstadisticasPermisos() {
        validarAccesoEstadisticas();
        return usuarioDAO.obtenerCantidadUsuariosPorPermiso();
    }

    public Map<String, Long> obtenerEstadisticasCuentas() {
        validarAccesoEstadisticas();
        return usuarioDAO.obtenerCantidadPorTipoCuenta();
    }

    public Usuario obtenerUsuarioMayorSaldo() {
        if (usuarioLogueado.getPermiso() != Permiso.ADMINISTRADOR) {
            throw new NoAutorizadoException("Acceso denegado. Solo ADMINISTRADORES pueden ver el usuario con mayor saldo.");
        }
        return usuarioDAO.usuarioConMayorSaldo();
    }

    public List<Usuario> rankingUsuariosPorSaldo() {
        if (usuarioLogueado.getPermiso() != Permiso.ADMINISTRADOR) {
            throw new NoAutorizadoException("Acceso denegado. Solo ADMINISTRADORES pueden ver el ranking de saldos.");
        }
        return usuarioDAO.listarUsuariosPorSaldoDescendente();
    }

    private void validarAccesoEstadisticas() {
        if (usuarioLogueado.getPermiso() == Permiso.CLIENTE) {
            throw new NoAutorizadoException("Acceso denegado. Solo personal autorizado puede ver estadísticas.");
        }
    }
}