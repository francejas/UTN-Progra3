package com.PrimerParcial.PrimerParcial.service;

import com.PrimerParcial.PrimerParcial.dto.CapturaRequestDTO;
import com.PrimerParcial.PrimerParcial.dto.CapturaResponseDTO;
import com.PrimerParcial.PrimerParcial.exception.CapturaInvalidaException;
import com.PrimerParcial.PrimerParcial.exception.ResourceNotFoundException;
import com.PrimerParcial.PrimerParcial.modelo.Captura;
import com.PrimerParcial.PrimerParcial.modelo.Entrenador;
import com.PrimerParcial.PrimerParcial.modelo.Estado;
import com.PrimerParcial.PrimerParcial.modelo.Pokemon;
import com.PrimerParcial.PrimerParcial.repository.CapturaRepository;
import com.PrimerParcial.PrimerParcial.repository.EntrenadorRespository;
import com.PrimerParcial.PrimerParcial.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CapturaService {

    @Autowired
    private EntrenadorRespository entrenadorRespository;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private CapturaRepository capturaRepository;

    public CapturaResponseDTO registrarCaptura(CapturaRequestDTO requestDTO){

        // 1. Validar que existan (Usando CapturaInvalidaException como pide el PDF)
        Entrenador entrenador = entrenadorRespository.findById(requestDTO.idEntrenador())
                .orElseThrow(()->new CapturaInvalidaException("El Entrenador no existe"));

        Pokemon pokemon = pokemonRepository.findById(requestDTO.idPokemon())
                .orElseThrow(()->new CapturaInvalidaException("El Pokemon no existe"));

        // 2. Validar disponibilidad (Al menos 1)
        if (pokemon.getCantidad_disponible() < 1){
            throw new CapturaInvalidaException("Cantidad insuficiente del pokemon.");
        }

        // 3. Validar Fecha (No puede ser posterior a hoy)
        if (requestDTO.fecha().isAfter(LocalDate.now())){
            throw new CapturaInvalidaException("La fecha de captura no puede ser en el futuro.");
        }

        // 4. Descontar stock
        pokemon.setCantidad_disponible(pokemon.getCantidad_disponible() - 1);
        pokemonRepository.save(pokemon); // Opcional, pero buena práctica para asegurar que se actualice la cantidad.

        // 5. Crear la captura
        Captura captura = new Captura();
        captura.setEntrenador(entrenador); // Usamos las entidades que buscamos arriba, NO las del DTO
        captura.setPokemon(pokemon);
        captura.setFecha_captura(requestDTO.fecha());
        captura.setNivel_capturado(requestDTO.nivelPokemon());
        captura.setEstado(Estado.ACTIVA);

        Captura capturaGuardada = capturaRepository.save(captura);

        // 6. Devolver Respuesta limpia
        return new CapturaResponseDTO(
                capturaGuardada.getId(),
                entrenador.getId(),
                pokemon.getNombre(),
                capturaGuardada.getFecha_captura(),
                capturaGuardada.getNivel_capturado(),
                capturaGuardada.getEstado()
        );
    }

    public CapturaResponseDTO liberarCaptura(Integer idCaptura) {

        // 1. Validar que la captura exista. Si no existe -> CapturaInvalidaException (¡Lo pide el PDF así!)
        Captura captura = capturaRepository.findById(idCaptura)
                .orElseThrow(() -> new CapturaInvalidaException("La captura con ID " + idCaptura + " no existe."));

        // 2. Validar que esté en estado ACTIVA
        if (captura.getEstado() != Estado.ACTIVA) {
            throw new CapturaInvalidaException("La captura no se puede liberar porque no está ACTIVA. Estado actual: " + captura.getEstado());
        }

        // 3. Cambiar el estado a LIBERADA
        captura.setEstado(Estado.LIBERADA);

        // 4. Incrementar nuevamente en 1 la cantidad disponible del Pokémon
        Pokemon pokemon = captura.getPokemon();
        pokemon.setCantidad_disponible(pokemon.getCantidad_disponible() + 1);

        // Guardamos los cambios en la base de datos
        pokemonRepository.save(pokemon);
        Captura capturaActualizada = capturaRepository.save(captura);

        // 5. Devolvemos la respuesta mapeada al DTO
        return new CapturaResponseDTO(
                capturaActualizada.getId(),
                capturaActualizada.getEntrenador().getId(),
                capturaActualizada.getPokemon().getNombre(),
                capturaActualizada.getFecha_captura(),
                capturaActualizada.getNivel_capturado(),
                capturaActualizada.getEstado()
        );
    }

    public List<CapturaResponseDTO> listarCapturasPorEntrenador(Integer idEntrenador) {

        // 1. Validamos que el entrenador exista (usando tu excepción de No Encontrado)
        if (!entrenadorRespository.existsById(idEntrenador)) {
            throw new ResourceNotFoundException("El entrenador con ID " + idEntrenador + " no existe.");
        }

        // 2. Traemos la lista de la base de datos usando el repositorio
        List<Captura> capturas = capturaRepository.findByEntrenador_Id(idEntrenador);

        // 3. Mapeamos la lista de Entidades a DTOs usando Streams
        return capturas.stream()
                .map(captura -> new CapturaResponseDTO(
                        captura.getId(),
                        captura.getEntrenador().getId(),
                        captura.getPokemon().getNombre(),
                        captura.getFecha_captura(),
                        captura.getNivel_capturado(),
                        captura.getEstado()
                )).toList();
    }


}