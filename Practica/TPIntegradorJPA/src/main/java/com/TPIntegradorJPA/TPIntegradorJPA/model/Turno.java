package com.TPIntegradorJPA.TPIntegradorJPA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter@Setter
@NoArgsConstructor
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTurno;

    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    @Enumerated(EnumType.STRING) // Agregamos esto para que guarde el texto del Enum y no un número
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idCliente")
    @JsonIgnore
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idVeterinario")
    @JsonIgnore
    private Veterinario veterinario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMascota")
    @JsonIgnore
    private Mascota mascota;


}
