package com.PrimerParcial.PrimerParcial.controller;

import com.PrimerParcial.PrimerParcial.dto.CapturaRequestDTO;
import com.PrimerParcial.PrimerParcial.dto.CapturaResponseDTO;
import com.PrimerParcial.PrimerParcial.service.CapturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/capturas")
public class CapturaController {

    @Autowired
    private CapturaService capturaService;

    // Endpoint del Punto 2 (Registrar)
    @PostMapping
    public ResponseEntity<CapturaResponseDTO> registrarCaptura(@RequestBody CapturaRequestDTO requestDTO) {
        CapturaResponseDTO respuesta = capturaService.registrarCaptura(requestDTO);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // Endpoint del Punto 3 (Liberar) - ¡NUEVO!
    @PutMapping("/liberar/{id}")
    public ResponseEntity<CapturaResponseDTO> liberarCaptura(@PathVariable Integer id) {
        CapturaResponseDTO respuesta = capturaService.liberarCaptura(id);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    // Endpoint del Punto 4 (Listar por Entrenador)
    @GetMapping("/entrenador/{idEntrenador}")
    public ResponseEntity<List<CapturaResponseDTO>> listarCapturasPorEntrenador(@PathVariable Integer idEntrenador) {

        List<CapturaResponseDTO> capturas = capturaService.listarCapturasPorEntrenador(idEntrenador);

        return new ResponseEntity<>(capturas, HttpStatus.OK);
    }
}