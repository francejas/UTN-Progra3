package com.TPIntegradorJPA.TPIntegradorJPA.service;

import com.TPIntegradorJPA.TPIntegradorJPA.dto.TurnoBreveDTO;
import com.TPIntegradorJPA.TPIntegradorJPA.dto.VeterinarioRequestDTO;
import com.TPIntegradorJPA.TPIntegradorJPA.dto.VeterinarioResponseDTO;
import com.TPIntegradorJPA.TPIntegradorJPA.model.Veterinario;
import com.TPIntegradorJPA.TPIntegradorJPA.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VeterinarioService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    public VeterinarioResponseDTO crearVeterianario (VeterinarioRequestDTO requestDTO){

        Veterinario nuevoVeterinario = new Veterinario();
        nuevoVeterinario.setNombre(requestDTO.nombre());
        nuevoVeterinario.setApellido(requestDTO.apellido());
        nuevoVeterinario.setMatricula(requestDTO.matricula());
        nuevoVeterinario.setEspecialidad(requestDTO.especialidad());
        nuevoVeterinario.setTelefono(requestDTO.telefono());
        nuevoVeterinario.setEmail(requestDTO.email());

        // Efectivamente, al crearlo no tiene turnos asignados aún
        Veterinario veterinarioGuardado = veterinarioRepository.save(nuevoVeterinario);

        return mapearAVeterinarioResponseDTO(veterinarioGuardado);
    }

    public List<VeterinarioResponseDTO> listarTodos(){
        List<Veterinario> veterinarios = veterinarioRepository.findAll();

        // Pasamos cada entidad por nuestro método traductor
        return veterinarios.stream().map(this::mapearAVeterinarioResponseDTO).toList();
    }


    // ====================================================================================
    // MÉTODOS AUXILIARES PRIVADOS
    // ====================================================================================

    private VeterinarioResponseDTO mapearAVeterinarioResponseDTO(Veterinario veterinario) {

        // Transformamos la lista de Entidades Turno a una lista de TurnoBreveDTO
        List<TurnoBreveDTO> turnosDelVeterinario = veterinario.getTurnos() != null ?
                veterinario.getTurnos().stream()
                .map(turno -> new TurnoBreveDTO(
                        turno.getIdTurno(),
                        turno.getFecha(),
                        turno.getHora(),
                        turno.getMotivo(),
                        turno.getEstado(),
                        turno.getMascota() != null ? turno.getMascota().getNombre() : "Sin mascota"
                )).toList()
                : new ArrayList<>();

        // Armamos el DTO final del veterinario
        return new VeterinarioResponseDTO(
                veterinario.getIdVeterinario(),
                veterinario.getNombre(),
                veterinario.getApellido(),
                veterinario.getMatricula(),
                veterinario.getEspecialidad(),
                veterinario.getTelefono(),
                veterinario.getEmail(),
                turnosDelVeterinario // Le inyectamos la lista limpia
        );
    }
}