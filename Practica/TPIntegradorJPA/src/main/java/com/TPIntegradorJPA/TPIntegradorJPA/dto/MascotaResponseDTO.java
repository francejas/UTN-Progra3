package com.TPIntegradorJPA.TPIntegradorJPA.dto;

import com.TPIntegradorJPA.TPIntegradorJPA.model.Cliente;
import jakarta.persistence.criteria.CriteriaBuilder;

public record MascotaResponseDTO(Integer idMascota,
                                 String nombre,
                                 String especie,
                                 String raza,
                                 Integer edad,
                                 Double peso,
                                 Integer idCliente) {
}
