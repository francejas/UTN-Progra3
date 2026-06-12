package com.PrimerParcial.PrimerParcial.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(String mensaje,
                               String estadoHttp,
                               LocalDateTime fechaError) {
}
