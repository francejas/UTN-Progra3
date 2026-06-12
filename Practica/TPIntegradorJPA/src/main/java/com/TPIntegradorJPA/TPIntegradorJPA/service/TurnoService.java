package com.TPIntegradorJPA.TPIntegradorJPA.service;

import com.TPIntegradorJPA.TPIntegradorJPA.dto.*;
import com.TPIntegradorJPA.TPIntegradorJPA.exceptions.GlobalExceptionHandler;
import com.TPIntegradorJPA.TPIntegradorJPA.exceptions.ResourceNotFoundException;
import com.TPIntegradorJPA.TPIntegradorJPA.model.*;
import com.TPIntegradorJPA.TPIntegradorJPA.repository.ClienteRepository;
import com.TPIntegradorJPA.TPIntegradorJPA.repository.MascotaRespository;
import com.TPIntegradorJPA.TPIntegradorJPA.repository.TurnoRepository;
import com.TPIntegradorJPA.TPIntegradorJPA.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private MascotaRespository mascotaRespository;

    public TurnoResponseDTO crearTurno (TurnoRequestDTO requestDTO){

        Cliente cliente = clienteRepository.findById(requestDTO.idCliente())
                .orElseThrow(()->new ResourceNotFoundException("El cliente con ID " + requestDTO.idCliente() + " no existe."));

        Veterinario veterinario = veterinarioRepository.findById(requestDTO.idVeterinario())
                .orElseThrow(()->new ResourceNotFoundException("El veterinario con ID " + requestDTO.idVeterinario() + " no existe."));

        Mascota mascota = mascotaRespository.findById(requestDTO.idMascota())
                .orElseThrow(()->new ResourceNotFoundException("La mascota con ID " + requestDTO.idMascota() + " no existe."));

        Turno nuevoTurno = new Turno();
        nuevoTurno.setFecha(requestDTO.fecha());
        nuevoTurno.setHora(requestDTO.hora());
        nuevoTurno.setMotivo(requestDTO.motivo());
        nuevoTurno.setEstado(Estado.PENDIENTE);
        nuevoTurno.setCliente(cliente);
        nuevoTurno.setVeterinario(veterinario);
        nuevoTurno.setMascota(mascota);

        Turno turnoGuardado = turnoRepository.save(nuevoTurno);

        // Usamos nuestro método helper
        return mapearATurnoResponseDTO(turnoGuardado);
    }

    public List<TurnoResponseDTO> listarTodos(){
        List<Turno> lista = turnoRepository.findAll();
        // Usamos un atajo de Java para decirle: "Pasa cada turno por mi método helper"
        return lista.stream().map(this::mapearATurnoResponseDTO).toList();
    }

    public TurnoResponseDTO buscarPorId(Integer id){
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No existe un turno con el ID: " + id));

        // Usamos nuestro método helper
        return mapearATurnoResponseDTO(turno);
    }

    public TurnoResponseDTO modificarTurno(Integer id, TurnoRequestDTO requestDTO){
        Turno turno = turnoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Turno no econtrado."));
        Cliente cliente = clienteRepository.findById(turno.getCliente().getIdCliente()).orElseThrow(()->new ResourceNotFoundException("Cliente no encontrado"));
        Veterinario veterinario = veterinarioRepository.findById(turno.getVeterinario().getIdVeterinario()).orElseThrow(()->new ResourceNotFoundException("Veterinario no encontrado."));
        Mascota mascota = mascotaRespository.findById(turno.getMascota().getIdMascota()).orElseThrow(()->new ResourceNotFoundException("Mascota no encontrada."));

        // 3. ACTUALIZAMOS LOS VALORES DE LA ENTIDAD
        turno.setFecha(requestDTO.fecha());
        turno.setHora(requestDTO.hora());
        turno.setMotivo(requestDTO.motivo());
        turno.setCliente(cliente);
        turno.setVeterinario(veterinario);
        turno.setMascota(mascota);
        // Nota: Conservamos el 'Estado' original que tenía el turno.
        // Si quisieras que también se pueda modificar el estado, deberías agregarlo a tu TurnoRequestDTO.

        // 4. GUARDAMOS LOS CAMBIOS EN LA BASE DE DATOS
        // Al tener ID coincidente, JPA ejecuta un SQL UPDATE por detrás
        Turno turnoModificado = turnoRepository.save(turno);
        return mapearATurnoResponseDTO(turnoModificado);
    }

    public TurnoResponseDTO eliminarTurno(Integer id) {
        // 1. Buscamos si el turno existe. Si no, nuestro Gerente de Quejas tira un 404.
        Turno turnoAEliminar = turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un turno con el ID: " + id));

        // 2. Mapeamos el DTO de respuesta ANTES de borrarlo de la base de datos
        // para tener los datos guardados y poder mostrárselos a Postman.
        TurnoResponseDTO turnoResponse = mapearATurnoResponseDTO(turnoAEliminar);

        // 3. Ahora sí, lo eliminamos físicamente de MySQL
        turnoRepository.deleteById(id);

        // 4. Devolvemos la "fotografía" de los datos que acabamos de borrar
        return turnoResponse;
    }

    public List<TurnoResponseDTO> listarTurnosPorVeterinario(Integer idVeterinario) {

        // 1. Validamos si el veterinario existe en el sistema
        if (!veterinarioRepository.existsById(idVeterinario)) {
            throw new ResourceNotFoundException("El veterinario con ID " + idVeterinario + " no existe.");
        }

        // 2. Buscamos sus turnos usando el método que creamos en el Repository
        List<Turno> turnosDelVeterinario = turnoRepository.findByVeterinario_IdVeterinario(idVeterinario);

        // 3. Los pasamos por la cañería del Stream para transformarlos a DTOs anidados
        return turnosDelVeterinario.stream()
                .map(this::mapearATurnoResponseDTO)
                .toList();
    }


    // ====================================================================================
    // MÉTODOS AUXILIARES PRIVADOS (Solo los puede usar el Chef, no el Controlador)
    // ====================================================================================

    private TurnoResponseDTO mapearATurnoResponseDTO(Turno turno) {

        // 1. DTO Cliente (Respetando los campos de tu ClienteResponseDTO original)
        ClienteResponseDTO clienteDTO = new ClienteResponseDTO(
                turno.getCliente().getIdCliente(),
                turno.getCliente().getNombre() + " " + turno.getCliente().getApellido(), // Concatenamos
                turno.getCliente().getTelefono(),
                turno.getCliente().getEmail(),
                turno.getCliente().getMascotas() != null ?
                        turno.getCliente().getMascotas().stream().map(Mascota::getNombre).toList()
                        : new ArrayList<>()
        );

        // 2. DTO Veterinario (Respetando los campos actualizados)
        VeterinarioResponseDTO veterinarioDTO = new VeterinarioResponseDTO(
                turno.getVeterinario().getIdVeterinario(),
                turno.getVeterinario().getNombre(),
                turno.getVeterinario().getApellido(),
                turno.getVeterinario().getMatricula(),
                turno.getVeterinario().getEspecialidad(),
                turno.getVeterinario().getTelefono(),
                turno.getVeterinario().getEmail(),
                new ArrayList<>() // ¡EL ARREGLO! Pasamos lista vacía para no cargar toda la agenda del veterinario innecesariamente.
        );

        // 3. DTO Mascota (Respetando los campos de tu MascotaResponseDTO original)
        MascotaResponseDTO mascotaDTO = new MascotaResponseDTO(
                turno.getMascota().getIdMascota(),
                turno.getMascota().getNombre(),
                turno.getMascota().getEspecie(),
                turno.getMascota().getRaza(),
                turno.getMascota().getEdad(),
                turno.getMascota().getPeso(),
                turno.getCliente().getIdCliente()
        );

        // 4. Armamos la gran "Caja final"
        return new TurnoResponseDTO(
                turno.getIdTurno(),
                turno.getFecha(),
                turno.getHora(),
                turno.getMotivo(),
                turno.getEstado(),
                clienteDTO,
                mascotaDTO,
                veterinarioDTO
        );
    }
}