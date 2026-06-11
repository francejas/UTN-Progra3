package com.TPIntegradorJPA.TPIntegradorJPA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter@Setter
@NoArgsConstructor
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMascota;

    private String nombre;
    private String especie;
    private String raza;
    private Integer edad;
    private Double peso;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCliente")
    @JsonIgnore
    private Cliente duenio;

    //una mascota tiene muchos turnos
    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Turno> turnos;


}
