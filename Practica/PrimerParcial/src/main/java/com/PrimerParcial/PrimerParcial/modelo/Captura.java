package com.PrimerParcial.PrimerParcial.modelo;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Getter@Setter
@NoArgsConstructor
public class Captura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrenador")
    private Entrenador entrenador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pokemon")
    private Pokemon pokemon;

    private LocalDate fecha_captura;

    private Integer nivel_capturado;

    @Enumerated(EnumType.STRING)
    private Estado estado;

}
