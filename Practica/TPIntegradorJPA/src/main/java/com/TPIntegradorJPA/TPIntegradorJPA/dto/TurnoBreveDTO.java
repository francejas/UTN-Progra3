package com.TPIntegradorJPA.TPIntegradorJPA.dto;

import com.TPIntegradorJPA.TPIntegradorJPA.model.Estado;
import java.time.LocalDate;
import java.time.LocalTime;

public record TurnoBreveDTO(
        Integer idTurno,
        LocalDate fecha,
        LocalTime hora,
        String motivo,
        Estado estado,
        String nombreMascota // Un dato útil extra para el veterinario
) {}