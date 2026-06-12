package com.TPIntegradorJPA.TPIntegradorJPA.dto;

import java.util.List;

public record VeterinarioResponseDTO(
        Integer idVeterinario,
        String nombre,
        String apellido,
        Integer matricula,
        String especialidad,
        Integer telefono,
        String email,
        List<TurnoBreveDTO> turnos // ¡Ahora usamos el Breve!
) {}