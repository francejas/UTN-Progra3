package com.Guia3JPA.Guia3JPA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "credenciales")
@Getter @Setter
@NoArgsConstructor
public class Credenciales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_credencial;

    /* == ELIMINAMOS Integer id_usuario Y PONEMOS EL OBJETO == */
    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false) // Esta es la Foreign Key en MySQL
    @JsonIgnore // Evita bucle infinito en Postman
    private Usuario usuario;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    /* @Enumerated es OBLIGATORIO para los Enums.
       EnumType.STRING le dice a MySQL que guarde la palabra "CLIENTE" y no un número (0, 1, 2) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Permiso permiso;

    public Credenciales(Usuario usuario, String username, String password, Permiso permiso) {
        this.usuario = usuario;
        this.username = username;
        this.password = password;
        this.permiso = permiso;
    }
}