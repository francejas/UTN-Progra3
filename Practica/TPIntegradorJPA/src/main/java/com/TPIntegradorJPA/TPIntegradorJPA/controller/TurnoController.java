package com.TPIntegradorJPA.TPIntegradorJPA.controller;

import com.TPIntegradorJPA.TPIntegradorJPA.dto.TurnoRequestDTO;
import com.TPIntegradorJPA.TPIntegradorJPA.dto.TurnoResponseDTO;
import com.TPIntegradorJPA.TPIntegradorJPA.model.Turno;
import com.TPIntegradorJPA.TPIntegradorJPA.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;

    @PostMapping
    public ResponseEntity<TurnoResponseDTO> resgistrarTurno (@RequestBody TurnoRequestDTO requestDTO){

        TurnoResponseDTO respuesta = turnoService.crearTurno(requestDTO);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TurnoResponseDTO>> listarTurnos(){
        List<TurnoResponseDTO> respuesta = turnoService.listarTodos();
        return new ResponseEntity<>(respuesta, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDTO> buscarPorId(@PathVariable Integer id){
        TurnoResponseDTO turnoResponseDTO = turnoService.buscarPorId(id);
        return new ResponseEntity<>(turnoResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> modificarTurno(@PathVariable Integer id, @RequestBody TurnoRequestDTO requestDTO){

        TurnoResponseDTO turnoModificado = turnoService.modificarTurno(id, requestDTO);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje","Turno modificado.");
        respuesta.put("turno",turnoModificado);

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarTurno (@PathVariable Integer id){
        TurnoResponseDTO turnoEliminado = turnoService.eliminarTurno(id);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("mensaje","Turno eliminado correctamente.");
        resultado.put("turno", turnoEliminado);

        // ¡Acá faltaba el HttpStatus.OK y el punto y coma final!
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @GetMapping("/veterinario/{idVeterinario}")
    public ResponseEntity<List<TurnoResponseDTO>> listarTurnosDeVeterinario(@PathVariable Integer idVeterinario) {

        // El mozo le pide la lista al servicio y se la manda a Postman
        List<TurnoResponseDTO> lista = turnoService.listarTurnosPorVeterinario(idVeterinario);

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


}
