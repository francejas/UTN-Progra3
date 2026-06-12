package com.ParcialCejasFrancisco.model;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter@Setter
@NoArgsConstructor
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_docente")
    private Docente docente;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recurso")
    private RecursoTecnologico recursoTecnologico;

    private LocalDate fecha_prestamo;
    private Integer cantidad_unidades;
    private Integer dias_prestamo;


    @Enumerated(EnumType.STRING)
    private Estado estado;
}
