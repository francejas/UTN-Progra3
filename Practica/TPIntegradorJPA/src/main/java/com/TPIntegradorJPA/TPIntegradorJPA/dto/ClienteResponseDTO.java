package com.TPIntegradorJPA.TPIntegradorJPA.dto;

import java.util.List;

public record ClienteResponseDTO(Integer idCliente,
                                 String nombreCompleto,
                                 Integer telefono,
                                 String email,
                                 List<String> nombresMascotas) {
}
