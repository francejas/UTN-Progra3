package com.PrimerParcial.PrimerParcial.dto;

import java.time.LocalDate;

public record CapturaRequestDTO(
        Integer idEntrenador, // ¡Solo IDs!
        Integer idPokemon,    // ¡Solo IDs!
        LocalDate fecha,
        Integer nivelPokemon
        // El estado no se pide en el request porque el requerimiento dice que siempre nace "ACTIVA"
) {}