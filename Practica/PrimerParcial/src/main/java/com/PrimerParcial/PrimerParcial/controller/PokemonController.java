package com.PrimerParcial.PrimerParcial.controller;


import com.PrimerParcial.PrimerParcial.dto.PokemonRequestDTO;
import com.PrimerParcial.PrimerParcial.dto.PokemonResponseDTO;
import com.PrimerParcial.PrimerParcial.modelo.Tipo;
import com.PrimerParcial.PrimerParcial.service.PokemonService;
import org.aspectj.lang.annotation.DeclareError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokemones")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @PostMapping
    public ResponseEntity<PokemonResponseDTO> registrarPokemon (@RequestBody PokemonRequestDTO requestDTO){
        PokemonResponseDTO pokemon = pokemonService.crear(requestDTO);
        return new ResponseEntity<>(pokemon, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PokemonResponseDTO>> listar (){
        List<PokemonResponseDTO> respuesta = pokemonService.listarTodos();
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PokemonResponseDTO> buscarPorId(@PathVariable Integer id){
        PokemonResponseDTO respuesta = pokemonService.buscarPorId(id);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PokemonResponseDTO> modificar (@PathVariable Integer id, @RequestBody PokemonRequestDTO requestDTO){
        PokemonResponseDTO respuesta = pokemonService.modificarPokemon(id, requestDTO);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PokemonResponseDTO> eliminar (@PathVariable Integer id){
        PokemonResponseDTO respuesta = pokemonService.eliminarPokemon(id);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    // Endpoint del Punto 5 (Consultar por Tipo)
    @GetMapping("/tipo/{tipoBuscado}")
    public ResponseEntity<List<PokemonResponseDTO>> consultarPorTipo(@PathVariable Tipo tipoBuscado) {

        List<PokemonResponseDTO> respuesta = pokemonService.consultarPorTipo(tipoBuscado);

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }



}
