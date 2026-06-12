package com.ParcialCejasFrancisco.controller;

import com.ParcialCejasFrancisco.dto.RecursoTecnologicoRequestDTO;
import com.ParcialCejasFrancisco.dto.RecursoTecnologicoResponseDTO;
import com.ParcialCejasFrancisco.model.RecursoTecnologico;
import com.ParcialCejasFrancisco.service.RecursoTecnologicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;
import java.util.List;

@RestController
@RequestMapping("/api/recursos")
public class RecursoTecnologicoController {
    @Autowired
    private RecursoTecnologicoService recursoTecnologicoService;


    @PostMapping
    public ResponseEntity<RecursoTecnologicoResponseDTO> crearRecurso(@RequestBody RecursoTecnologicoRequestDTO requestDTO){
        RecursoTecnologicoResponseDTO respuesta = recursoTecnologicoService.registrarRecurso(requestDTO);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RecursoTecnologicoResponseDTO>> listar (){
        List<RecursoTecnologicoResponseDTO> respuesta = recursoTecnologicoService.listarRecursos();
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecursoTecnologicoResponseDTO> modificar (@PathVariable Integer id, @RequestBody RecursoTecnologicoRequestDTO requestDTO){
        RecursoTecnologicoResponseDTO respuesta = recursoTecnologicoService.modificar(id, requestDTO);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RecursoTecnologicoResponseDTO> eliminar (@PathVariable Integer id){
        RecursoTecnologicoResponseDTO respuesta = recursoTecnologicoService.eliminar(id);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{area}")
    public ResponseEntity<List<RecursoTecnologicoResponseDTO>> listarPorArea (@PathVariable String area){
        List<RecursoTecnologicoResponseDTO> respuesta = recursoTecnologicoService.listarPorArea(area);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


}
