package com.TPIntegradorJPA.TPIntegradorJPA.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter@Setter
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //misma pregunta aca que las de abajo, porque Integer y no int
    private Integer idCliente;

    private String nombre;
    private String apellido;

    //aca tengo la misma pregunta que la de boolean y Boolean que esta mas abajo, porque Integer y no int ?
    private Integer telefono;
    private String email;
    private String direccion;

    //porque aca tiene que ser Boolean y no boolean , cual es la diferencia, porquee tiene que ser asi ?
    private Boolean activo;

    //deberia de tener una lista de turnos y de mascotas ?
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Turno> turnos;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mascota> mascotas;

}
