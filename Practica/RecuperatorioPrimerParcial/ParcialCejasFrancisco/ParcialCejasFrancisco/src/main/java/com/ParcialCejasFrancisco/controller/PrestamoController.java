package com.ParcialCejasFrancisco.controller;

import com.ParcialCejasFrancisco.dto.PrestamoBreveResponseDTO;
import com.ParcialCejasFrancisco.dto.PrestamoRequestDTO;
import com.ParcialCejasFrancisco.dto.PrestamoResponseDTO;
import com.ParcialCejasFrancisco.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<PrestamoResponseDTO> crearPrestamo(@RequestBody PrestamoRequestDTO requestDTO){
        PrestamoResponseDTO respuesta = prestamoService.registrar(requestDTO);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<PrestamoResponseDTO> cancelarPresamo(@PathVariable Integer id){
        PrestamoResponseDTO respuesta = prestamoService.cancelar(id);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/docente/{id}")
    public ResponseEntity<List<PrestamoBreveResponseDTO>> listarPrestamoDocente (@PathVariable Integer id){
        List<PrestamoBreveResponseDTO> respuesta = prestamoService.listarPorDocente(id);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }










}
