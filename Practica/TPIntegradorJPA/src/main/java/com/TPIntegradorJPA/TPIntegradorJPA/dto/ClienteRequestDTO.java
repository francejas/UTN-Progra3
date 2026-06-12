package com.TPIntegradorJPA.TPIntegradorJPA.dto;

public record ClienteRequestDTO(
        String nombre,
        String apellido,
        Integer telefono,
        String email,
        String direccion,
        Boolean activo
) {}
