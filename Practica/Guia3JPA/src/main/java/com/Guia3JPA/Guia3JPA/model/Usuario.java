package com.Guia3JPA.Guia3JPA.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="usuarios")
@Getter@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //aca que pasa si el atributo no coincide con la columna de la tabla ? porque en la tabla esta puesta como id_usuario, que pasa si aca pongo id solo ?
    private Integer id_usuario;

    //hay alguna forma de aplicar esto de Column nullable ... pero para un grupoo de atributos asi no lo tengo que andar anotando 30 veces
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(unique = true,nullable = false)
    private String dni;

    @Column(unique = true,nullable = false)
    private String email;

    //aca que iria ?
    @Column()
    private LocalDate fechaCreacion;

    public Usuario(String nombre, String apellido, String dni, String email, LocalDate fechaCreacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaCreacion = fechaCreacion;
    }
}
