package com.TPIntegradorJPA.TPIntegradorJPA.dto;

import com.TPIntegradorJPA.TPIntegradorJPA.model.Estado;

import java.time.LocalDate;
import java.time.LocalTime;

public record TurnoRequestDTO(LocalDate fecha,
                              LocalTime hora,
                              String motivo,
                              Integer idCliente,
                              Integer idVeterinario,
                              Integer idMascota
                              ) {
}
