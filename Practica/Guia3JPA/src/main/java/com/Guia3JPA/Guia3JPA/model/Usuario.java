package com.Guia3JPA.Guia3JPA.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="usuarios")
@Getter @Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario") // Así solucionás que se llame distinto en MySQL
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(unique = true, nullable = false)
    private String dni;

    @Column(unique = true, nullable = false)
    private String email;

    @CreationTimestamp // Magia: se llena solo con la fecha/hora actual al guardar
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    /* == RELACIONES == */

    // 1 Usuario tiene 1 Credencial (CascadeType.ALL hace el ON DELETE CASCADE)
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Credenciales credenciales;

    // 1 Usuario tiene Muchas Cuentas
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cuenta> cuentas = new ArrayList<>();

    // Opcional: Constructor sin ID ni fecha (porque se generan solos)
    public Usuario(String nombre, String apellido, String dni, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
    }
}