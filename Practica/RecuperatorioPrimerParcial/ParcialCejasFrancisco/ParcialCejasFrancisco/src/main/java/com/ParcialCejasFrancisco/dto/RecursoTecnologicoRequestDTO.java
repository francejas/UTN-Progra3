package com.ParcialCejasFrancisco.dto;

public record RecursoTecnologicoRequestDTO(
        String nombre,
        String tipoRecurso,
        String areaPrincipal,
        String areaSecundaria,
        String estadoRecurso,
        Integer cantidadDisponible
) {
}
