package com.TPIntegradorJPA.TPIntegradorJPA.dto;

import jakarta.persistence.criteria.CriteriaBuilder;

public record VeterinarioRequestDTO(String nombre,
                                    String apellido,
                                    Integer matricula,
                                    String especialidad,
                                    Integer telefono,
                                    String email) {
}
