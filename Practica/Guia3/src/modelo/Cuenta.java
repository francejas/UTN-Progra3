package modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Cuenta {
    private int idCuenta;
    private int idUsuario;
    TipoCuenta tipo;
    BigDecimal saldo;
    LocalDateTime fechaCreacion;

    public Cuenta(int idCuenta, int idUsuario, TipoCuenta tipo, BigDecimal saldo, LocalDateTime fechaCreacion) {
        this.idCuenta = idCuenta;
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.saldo = saldo;
        this.fechaCreacion = fechaCreacion;
    }

    public Cuenta(int idUsuario, TipoCuenta tipo, BigDecimal saldo, LocalDateTime fechaCreacion) {
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.saldo = saldo;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TipoCuenta getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuenta tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "idCuenta=" + idCuenta +
                ", idUsuario=" + idUsuario +
                ", tipo=" + tipo +
                ", saldo=" + saldo +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
