package com.PrimerParcial.PrimerParcial.service;

import com.PrimerParcial.PrimerParcial.dto.PokemonRequestDTO;
import com.PrimerParcial.PrimerParcial.dto.PokemonResponseDTO;
import com.PrimerParcial.PrimerParcial.exception.ResourceNotFoundException;
import com.PrimerParcial.PrimerParcial.modelo.Pokemon;
import com.PrimerParcial.PrimerParcial.modelo.Tipo;
import com.PrimerParcial.PrimerParcial.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonService {

    @Autowired
    private PokemonRepository pokemonRepository;

    public PokemonResponseDTO crear (PokemonRequestDTO requestDTO){
        Pokemon pokemon = new Pokemon();
        pokemon.setNombre(requestDTO.nombre());
        pokemon.setTipo_principal(requestDTO.tipoPrincipal());
        pokemon.setTipo_secundario(requestDTO.tipoSecundario());
        pokemon.setNivel_base(requestDTO.nivelBase());
        pokemon.setCantidad_disponible(requestDTO.cantidadDisponible());

        Pokemon pokemonGuardado = pokemonRepository.save(pokemon);
        return new PokemonResponseDTO(pokemonGuardado.getId(), pokemonGuardado.getNombre(), pokemonGuardado.getTipo_principal(), pokemonGuardado.getTipo_secundario(), pokemonGuardado.getNivel_base());
    }

    public List<PokemonResponseDTO> listarTodos (){
        List<Pokemon> lista = pokemonRepository.findAll();

        return lista.stream()
                .map(pokemon -> new PokemonResponseDTO(pokemon.getId(), pokemon.getNombre(), pokemon.getTipo_principal(), pokemon.getTipo_secundario(), pokemon.getNivel_base())).toList();
    }

    public PokemonResponseDTO buscarPorId(Integer id){
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("El Pokemon con ID " + id + " no existe."));

        return new PokemonResponseDTO(pokemon.getId(), pokemon.getNombre(), pokemon.getTipo_principal(), pokemon.getTipo_secundario(), pokemon.getNivel_base()); // Faltaba el punto y coma acá
    }

    // ACA FALTABA RECIBIR EL Integer id POR PARÁMETRO
    public PokemonResponseDTO modificarPokemon (Integer id, PokemonRequestDTO requestDTO){
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("El Pokemon con ID " + id + " no existe."));

        pokemon.setNombre(requestDTO.nombre());
        pokemon.setTipo_principal(requestDTO.tipoPrincipal());
        pokemon.setTipo_secundario(requestDTO.tipoSecundario());
        pokemon.setNivel_base(requestDTO.nivelBase());
        pokemon.setCantidad_disponible(requestDTO.cantidadDisponible());

        Pokemon pokemonModificado = pokemonRepository.save(pokemon);

        return new PokemonResponseDTO(pokemonModificado.getId(), pokemonModificado.getNombre(), pokemonModificado.getTipo_principal(), pokemonModificado.getTipo_secundario(), pokemonModificado.getNivel_base()); // Faltaba el punto y coma y usar pokemonModificado
    }

    public PokemonResponseDTO eliminarPokemon (Integer id){
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Pokemon no encontrado."));

        pokemonRepository.delete(pokemon);

        return new PokemonResponseDTO(pokemon.getId(), pokemon.getNombre(), pokemon.getTipo_principal(), pokemon.getTipo_secundario(), pokemon.getNivel_base());
    }

    // Método para el Punto 5
    public List<PokemonResponseDTO> consultarPorTipo(Tipo tipoBuscado) {

        // 1. Buscamos usando el método personalizado de tu repositorio
        List<Pokemon> pokemonesFiltrados = pokemonRepository.buscarPorTipoPrincipal(tipoBuscado);

        // 2. Transformamos a DTO
        return pokemonesFiltrados.stream()
                .map(pokemon -> new PokemonResponseDTO(
                        pokemon.getId(),
                        pokemon.getNombre(),
                        pokemon.getTipo_principal(),
                        pokemon.getTipo_secundario(),
                        pokemon.getNivel_base()
                )).toList();
    }
}