package com.TPIntegradorJPA.TPIntegradorJPA.controller;

import com.TPIntegradorJPA.TPIntegradorJPA.dto.VeterinarioRequestDTO;
import com.TPIntegradorJPA.TPIntegradorJPA.dto.VeterinarioResponseDTO;
import com.TPIntegradorJPA.TPIntegradorJPA.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
public class VeterinarioController {
    @Autowired
    private VeterinarioService veterinarioService;

    @PostMapping
    public ResponseEntity<VeterinarioResponseDTO> registrarVeterinario (@RequestBody VeterinarioRequestDTO requestDTO){
        VeterinarioResponseDTO respuesta = veterinarioService.crearVeterianario(requestDTO);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VeterinarioResponseDTO>> listarVeterinarios(){
        List<VeterinarioResponseDTO> respuesta = veterinarioService.listarTodos();
        return new ResponseEntity<>(respuesta, HttpStatus.OK);

    }




}


