package com.Guia3JPA.Guia3JPA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.SpringApplicationRunListener;

@Entity
@Getter@Setter
@NoArgsConstructor
@Table(name = "credenciales")
public class Credenciales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_credencial;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private  Permiso permiso;

    public Credenciales(Usuario usuario, String username, String password, Permiso permiso) {
        this.usuario = usuario;
        this.username = username;
        this.password = password;
        this.permiso = permiso;
    }
}
