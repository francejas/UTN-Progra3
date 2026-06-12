package com.PrimerParcial.PrimerParcial.dto;

import com.PrimerParcial.PrimerParcial.modelo.Entrenador;
import com.PrimerParcial.PrimerParcial.modelo.Tipo;
import jakarta.persistence.criteria.CriteriaBuilder;

public record PokemonRequestDTO(
        String nombre,
        Tipo tipoPrincipal,
        Tipo tipoSecundario,
        Integer nivelBase,
        Integer cantidadDisponible
) {
}
