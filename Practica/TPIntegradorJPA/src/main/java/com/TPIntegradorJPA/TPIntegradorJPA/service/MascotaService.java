package com.TPIntegradorJPA.TPIntegradorJPA.service;

import com.TPIntegradorJPA.TPIntegradorJPA.dto.MascotaRequestDTO;
import com.TPIntegradorJPA.TPIntegradorJPA.dto.MascotaResponseDTO;
import com.TPIntegradorJPA.TPIntegradorJPA.exceptions.ResourceNotFoundException;
import com.TPIntegradorJPA.TPIntegradorJPA.model.Cliente;
import com.TPIntegradorJPA.TPIntegradorJPA.model.Mascota;
import com.TPIntegradorJPA.TPIntegradorJPA.repository.ClienteRepository;
import com.TPIntegradorJPA.TPIntegradorJPA.repository.MascotaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService {

    @Autowired
    private MascotaRespository mascotaRepository;

    // ¡NUEVO! Inyectamos el repo de Cliente para poder buscar al dueño
    @Autowired
    private ClienteRepository clienteRepository;

    // Le sacamos el @RequestBody, eso va en el Controller
    public MascotaResponseDTO crearMascota (MascotaRequestDTO requestDTO){

        // 1. BUSCAMOS AL CLIENTE EN LA BASE DE DATOS
        // Usamos el idCliente que viene en el DTO.
        // Si no existe, tiramos una excepción temporal (¡esto después lo agarra el gerente de quejas!)
        Cliente duenio = clienteRepository.findById(requestDTO.idCliente())
                .orElseThrow(() -> new ResourceNotFoundException("Error: El cliente con ID " + requestDTO.idCliente() + " no existe."));

        // 2. CREAMOS LA ENTIDAD Y SETEAMOS LOS DATOS
        Mascota nuevaMascota = new Mascota();
        nuevaMascota.setNombre(requestDTO.nombre());
        nuevaMascota.setEspecie(requestDTO.especie());
        nuevaMascota.setRaza(requestDTO.raza());
        nuevaMascota.setEdad(requestDTO.edad());
        nuevaMascota.setPeso(requestDTO.peso());

        // ¡Magia! Ahora sí le pasamos el objeto Cliente completo que encontramos arriba
        nuevaMascota.setCliente(duenio);

        // 3. GUARDAMOS EN LA BASE DE DATOS
        Mascota mascotaGuardada = mascotaRepository.save(nuevaMascota);

        // 4. ARMAMOS EL PLATO FINAL (El Response DTO)
        return new MascotaResponseDTO(
                mascotaGuardada.getIdMascota(),
                mascotaGuardada.getNombre(),
                mascotaGuardada.getEspecie(),
                mascotaGuardada.getRaza(),
                mascotaGuardada.getEdad(),
                mascotaGuardada.getPeso(),
                duenio.getIdCliente() // Podrías poner mascotaGuardada.getCliente().getIdCliente() también
        );
    }

    public List<MascotaResponseDTO> listarTodas(){

        List<Mascota> mascotas = mascotaRepository.findAll();

        return mascotas.stream()
                .map(mascota -> new MascotaResponseDTO(mascota.getIdMascota(),mascota.getNombre(), mascota.getEspecie(), mascota.getRaza(), mascota.getEdad(), mascota.getPeso(), mascota.getCliente().getIdCliente())).toList();
    }

    public List<MascotaResponseDTO> listarPorCliente(Integer idCliente){
        if (clienteRepository.findById(idCliente).isEmpty()){
            throw new ResourceNotFoundException("El cliente no existe.");
        }

        List<Mascota> lista = mascotaRepository.findByCliente_IdCliente(idCliente);

        return lista.stream()
                .map(mascota -> new MascotaResponseDTO(mascota.getIdMascota(), mascota.getNombre(),
                        mascota.getEspecie(),
                        mascota.getRaza(),
                        mascota.getEdad(),
                        mascota.getPeso(),
                        mascota.getCliente().getIdCliente())).toList();

    }


}