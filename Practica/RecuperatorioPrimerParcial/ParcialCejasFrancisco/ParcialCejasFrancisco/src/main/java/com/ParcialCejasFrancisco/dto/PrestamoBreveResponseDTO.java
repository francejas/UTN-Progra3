package com.ParcialCejasFrancisco.dto;

import com.ParcialCejasFrancisco.model.Estado;

import java.time.LocalDate;

public record PrestamoBreveResponseDTO(
        String nombreDocente,
        String areaDocente,
        String nombreRecurso,
        String tipoRecurso,
        String areaPrinciapal,
        LocalDate fecha,
        Integer cantidad,
        Integer cantidadDias,
        Estado estado
        /*
        Integer cantidadPrestamos,
        Integer cantiadUnidadesPrestadas,
        Integer cantidadDeDias

         */
) {
}
