package com.TPIntegradorJPA.TPIntegradorJPA.service;

import com.TPIntegradorJPA.TPIntegradorJPA.dto.ClienteRequestDTO;
import com.TPIntegradorJPA.TPIntegradorJPA.dto.ClienteResponseDTO;
import com.TPIntegradorJPA.TPIntegradorJPA.model.Cliente;
import com.TPIntegradorJPA.TPIntegradorJPA.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteResponseDTO crearCliente (ClienteRequestDTO requestDTO){
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(requestDTO.nombre());
        nuevoCliente.setApellido(requestDTO.apellido());
        nuevoCliente.setTelefono(requestDTO.telefono());
        nuevoCliente.setEmail(requestDTO.email());
        nuevoCliente.setDireccion(requestDTO.direccion());
        nuevoCliente.setActivo(requestDTO.activo());

        Cliente clienteGuardado = clienteRepository.save(nuevoCliente);

        return new ClienteResponseDTO(
                clienteGuardado.getIdCliente(),
                clienteGuardado.getNombre() + " " + clienteGuardado.getApellido(),
                clienteGuardado.getTelefono(),
                clienteGuardado.getEmail(),
                new ArrayList<>() // Como es nuevo, todavía no tiene mascotas
        );
    }

    public List<ClienteResponseDTO> listarTodos() {
        // 1. Buscamos todos los clientes en la base de datos
        List<Cliente> clientes = clienteRepository.findAll();

        // 2. Transformamos la lista de Entidades a lista de DTOs usando Streams
        return clientes.stream()
                .map(cliente -> new ClienteResponseDTO(
                        cliente.getIdCliente(),
                        cliente.getNombre() + " " + cliente.getApellido(),
                        cliente.getTelefono(),
                        cliente.getEmail(),
                        // Si el cliente tiene mascotas, sacamos solo los nombres. Si no, mandamos lista vacía.
                        cliente.getMascotas() != null ?
                                cliente.getMascotas().stream().map(mascota -> mascota.getNombre()).toList()
                                : new ArrayList<>()
                ))
                .toList(); // Usamos .toList() nativo de Java 16+
    }


}
