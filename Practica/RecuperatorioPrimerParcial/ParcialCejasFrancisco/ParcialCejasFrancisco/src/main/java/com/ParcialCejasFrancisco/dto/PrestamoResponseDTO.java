package com.ParcialCejasFrancisco.dto;

import com.ParcialCejasFrancisco.model.Docente;
import com.ParcialCejasFrancisco.model.Estado;
import com.ParcialCejasFrancisco.model.RecursoTecnologico;

import java.time.LocalDate;

public record PrestamoResponseDTO(
        Integer id,
        Docente docente,
        RecursoTecnologico recursoTecnologico,
        LocalDate fecha,
        Integer cantidad,
        Integer diasPrestamo,
        Estado estado
) {
}
