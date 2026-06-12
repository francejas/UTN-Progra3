package com.TPIntegradorJPA.TPIntegradorJPA.controller;

import com.TPIntegradorJPA.TPIntegradorJPA.dto.ClienteRequestDTO;
import com.TPIntegradorJPA.TPIntegradorJPA.dto.ClienteResponseDTO;
import com.TPIntegradorJPA.TPIntegradorJPA.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
   public ResponseEntity<ClienteResponseDTO> registrarCliente (@RequestBody ClienteRequestDTO requestDTO){

        ClienteResponseDTO respuesta = clienteService.crearCliente(requestDTO);

        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes(){
        List<ClienteResponseDTO> lista = clienteService.listarTodos();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }




}
