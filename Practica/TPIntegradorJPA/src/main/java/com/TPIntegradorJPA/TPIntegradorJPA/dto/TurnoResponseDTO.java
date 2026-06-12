package com.TPIntegradorJPA.TPIntegradorJPA.dto;

import com.TPIntegradorJPA.TPIntegradorJPA.model.Estado;
import java.time.LocalDate;
import java.time.LocalTime;

public record TurnoResponseDTO(
        Integer idTurno,
        LocalDate fecha,
        LocalTime hora,
        String motivo,
        Estado estado,

        // ¡La magia de la composición! Usamos los otros records que ya creaste
        ClienteResponseDTO cliente,
        MascotaResponseDTO mascota,
        VeterinarioResponseDTO veterinario
) {}