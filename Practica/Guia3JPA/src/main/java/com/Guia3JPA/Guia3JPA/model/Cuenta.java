package com.Guia3JPA.Guia3JPA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "cuentas")
@Getter @Setter
@NoArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cuenta;

    /* == ELIMINAMOS Integer id_usuario Y PONEMOS EL OBJETO == */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false) // Foreign Key en MySQL
    @JsonIgnore
    private Usuario usuario;

    // Al igual que con permisos, necesitamos decirle cómo guardar el Enum
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCuenta tipo;

    // Le podemos dar un valor por defecto de 0.0 si no nos mandan saldo
    @Column(nullable = false)
    private Double saldo = 0.0;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion; // Corregí un pequeño error de tipeo que tenías acá

    public Cuenta(Usuario usuario, TipoCuenta tipo, Double saldo) {
        this.usuario = usuario;
        this.tipo = tipo;
        this.saldo = saldo != null ? saldo : 0.0;
    }
}