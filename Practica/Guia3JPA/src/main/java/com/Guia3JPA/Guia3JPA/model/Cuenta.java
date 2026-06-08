package com.Guia3JPA.Guia3JPA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter@Setter
@NoArgsConstructor
@Table(name = "cuentas")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cuenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    @JsonIgnore
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCuenta tipoCuenta;

    @Column(nullable = false)
    private Double saldo = 0.0;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fecha_creacion;

    public Cuenta(Usuario usuario, TipoCuenta tipoCuenta, Double saldo) {
        this.usuario = usuario;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
    }
}
