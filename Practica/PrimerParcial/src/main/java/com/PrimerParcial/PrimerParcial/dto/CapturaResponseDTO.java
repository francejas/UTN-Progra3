package com.PrimerParcial.PrimerParcial.dto;

import com.PrimerParcial.PrimerParcial.modelo.Estado;
import java.time.LocalDate;

public record CapturaResponseDTO(
        Integer idCaptura,
        Integer idEntrenador,
        String nombrePokemon, // Devolvemos datitos limpios, no la Entidad gigante
        LocalDate fecha,
        Integer nivelPokemon,
        Estado estadoCaptura
) {}