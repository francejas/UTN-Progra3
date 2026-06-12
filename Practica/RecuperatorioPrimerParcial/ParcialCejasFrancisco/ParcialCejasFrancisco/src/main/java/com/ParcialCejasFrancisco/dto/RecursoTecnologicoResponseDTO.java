package com.ParcialCejasFrancisco.dto;

public record RecursoTecnologicoResponseDTO(
        Integer id,
        String nombre,
        String tipoRecurso,
        String areaPrincipal,
        String areaSecundaria,
        String estadoRecurso,
        Integer cantidadDisponible
) {
}
