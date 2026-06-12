package com.PrimerParcial.PrimerParcial.dto;

import com.PrimerParcial.PrimerParcial.modelo.Entrenador;
import com.PrimerParcial.PrimerParcial.modelo.Tipo;

public record PokemonResponseDTO(Integer idPokemon,
                                 String nombre,
                                 Tipo tipoPrincipal,
                                 Tipo tipoSecundario,
                                 Integer nivelBase) {
}
