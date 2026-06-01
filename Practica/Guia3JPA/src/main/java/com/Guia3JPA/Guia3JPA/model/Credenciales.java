package com.Guia3JPA.Guia3JPA.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "credenciales")
@Getter@Setter
@NoArgsConstructor
public class Credenciales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_credencial;

    @Column(nullable = false)
    private Integer id_usuario;

    @Column(unique = true, nullable = true)
    private String 

}
