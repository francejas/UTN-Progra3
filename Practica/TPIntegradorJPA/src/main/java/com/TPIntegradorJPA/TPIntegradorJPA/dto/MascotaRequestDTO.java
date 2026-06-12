package com.TPIntegradorJPA.TPIntegradorJPA.dto;

import com.TPIntegradorJPA.TPIntegradorJPA.model.Cliente;

public record MascotaRequestDTO(String nombre,
                                String especie,
                                String raza,
                                Integer edad,
                                Double peso,
                                Integer idCliente) {
}
