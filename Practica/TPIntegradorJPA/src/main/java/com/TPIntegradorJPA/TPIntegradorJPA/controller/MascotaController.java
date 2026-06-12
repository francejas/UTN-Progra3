package com.TPIntegradorJPA.TPIntegradorJPA.controller;

import com.TPIntegradorJPA.TPIntegradorJPA.dto.MascotaRequestDTO;
import com.TPIntegradorJPA.TPIntegradorJPA.dto.MascotaResponseDTO;
import com.TPIntegradorJPA.TPIntegradorJPA.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {
    @Autowired
    private MascotaService mascotaService;

    @PostMapping
    public ResponseEntity<MascotaResponseDTO> registrarMascota (@RequestBody MascotaRequestDTO requestDTO){
        MascotaResponseDTO respuesta = mascotaService.crearMascota(requestDTO);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MascotaResponseDTO>> listarMascotas(){
        List<MascotaResponseDTO> respuesta = mascotaService.listarTodas();
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<MascotaResponseDTO>> buscarPorCliente(@PathVariable Integer id){
        List<MascotaResponseDTO> lista = mascotaService.listarPorCliente(id);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


}
