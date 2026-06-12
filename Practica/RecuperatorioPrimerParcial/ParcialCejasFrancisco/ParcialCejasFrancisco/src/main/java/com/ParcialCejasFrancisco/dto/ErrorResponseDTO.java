package com.ParcialCejasFrancisco.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ErrorResponseDTO(
        String aviso,
        String http,
        LocalDateTime fecha
) {
}
