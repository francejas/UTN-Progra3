package com.TPIntegradorJPA.TPIntegradorJPA.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter@Setter
@NoArgsConstructor
public class Veterinario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVeterinario;

    private String nombre;
    private String apellido;
    private Integer matricula;
    private String especialidad;
    private Integer telefono;
    private String email;

    //un veterianario deberia de tener una lista de turnos o no ?
    @OneToMany(mappedBy = "veterinario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Turno> turnos;



}
