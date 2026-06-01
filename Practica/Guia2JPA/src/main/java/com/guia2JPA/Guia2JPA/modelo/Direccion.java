package com.guia2JPA.Guia2JPA.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "direcciones")
@Getter@Setter
@NoArgsConstructor
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String calle;

    @Column(nullable = false)
    private Integer altura;

    //Relacion N a 1 : muchas direcciones pertenecen a un alumno
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id", nullable = false)
    @JsonIgnore
    private Alumno alumno;

    public Direccion(String calle, Integer altura, Alumno alumno) {
        this.calle = calle;
        this.altura = altura;
        this.alumno = alumno;
    }
}
