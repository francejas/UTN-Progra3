package com.ParcialCejasFrancisco.service;


import com.ParcialCejasFrancisco.dto.RecursoTecnologicoRequestDTO;
import com.ParcialCejasFrancisco.dto.RecursoTecnologicoResponseDTO;
import com.ParcialCejasFrancisco.exception.ResourceNotFound;
import com.ParcialCejasFrancisco.model.RecursoTecnologico;
import com.ParcialCejasFrancisco.repository.RecursoTecnologicoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecursoTecnologicoService {

    @Autowired
    private RecursoTecnologicoRespository recursoTecnologicoRespository;

    public RecursoTecnologicoResponseDTO registrarRecurso(RecursoTecnologicoRequestDTO requestDTO){

        RecursoTecnologico recurso = new RecursoTecnologico();
        recurso.setNombre(requestDTO.nombre());
        recurso.setTipo_recurso(requestDTO.tipoRecurso());
        recurso.setArea_uso_princiapal(requestDTO.areaPrincipal());
        recurso.setArea_uso_secundario(requestDTO.areaSecundaria());
        recurso.setEstado_recurso(requestDTO.estadoRecurso());
        recurso.setCantidad_disponible(requestDTO.cantidadDisponible());

        RecursoTecnologico recursoGuardado = recursoTecnologicoRespository.save(recurso);

        return new RecursoTecnologicoResponseDTO(recursoGuardado.getId(),
                recursoGuardado.getNombre(),
                recursoGuardado.getTipo_recurso(),
                recursoGuardado.getArea_uso_princiapal(),
                recursoGuardado.getArea_uso_secundario(),
                recursoGuardado.getEstado_recurso(),
                recursoGuardado.getCantidad_disponible());

    }

    public List<RecursoTecnologicoResponseDTO> listarRecursos (){
        List<RecursoTecnologico> lista = recursoTecnologicoRespository.findAll();

        return lista.stream()
                .map(recursoTecnologico -> new RecursoTecnologicoResponseDTO(recursoTecnologico.getId(),
                        recursoTecnologico.getNombre(),
                        recursoTecnologico.getTipo_recurso(),
                        recursoTecnologico.getArea_uso_princiapal(),
                        recursoTecnologico.getArea_uso_secundario(),
                        recursoTecnologico.getEstado_recurso(),
                        recursoTecnologico.getCantidad_disponible())).toList();


    }

    public RecursoTecnologicoResponseDTO modificar(Integer id, RecursoTecnologicoRequestDTO requestDTO){

        RecursoTecnologico recurso = recursoTecnologicoRespository.findById(id).orElseThrow(()->new ResourceNotFound("Recurso con ID: "+id+" no encontrado."));

        recurso.setNombre(requestDTO.nombre());
        recurso.setTipo_recurso(requestDTO.tipoRecurso());
        recurso.setArea_uso_princiapal(requestDTO.areaPrincipal());
        recurso.setArea_uso_secundario(requestDTO.areaSecundaria());
        recurso.setEstado_recurso(requestDTO.estadoRecurso());
        recurso.setCantidad_disponible(requestDTO.cantidadDisponible());

        RecursoTecnologico recursoModificado = recursoTecnologicoRespository.save(recurso);

        return new RecursoTecnologicoResponseDTO(recursoModificado.getId(),
                recursoModificado.getNombre(),
                recursoModificado.getTipo_recurso(),
                recursoModificado.getArea_uso_princiapal(),
                recursoModificado.getArea_uso_secundario(),
                recursoModificado.getEstado_recurso(),
                recursoModificado.getCantidad_disponible());

    }

    public RecursoTecnologicoResponseDTO eliminar (Integer id){
        RecursoTecnologico recurso = recursoTecnologicoRespository.findById(id).orElseThrow(()->new ResourceNotFound("Recurso con ID: "+id+" no encontrado."));

        recursoTecnologicoRespository.delete(recurso);

        return new RecursoTecnologicoResponseDTO(recurso.getId(),
                recurso.getNombre(),
                recurso.getTipo_recurso(),
                recurso.getArea_uso_princiapal(),
                recurso.getArea_uso_secundario(),
                recurso.getEstado_recurso(),
                recurso.getCantidad_disponible());

    }

    public List<RecursoTecnologicoResponseDTO> listarPorArea (String area){
        List<RecursoTecnologico> lista = recursoTecnologicoRespository.findAll();

        List<RecursoTecnologico> filtro =  lista.stream()
                .filter(recursoTecnologico -> recursoTecnologico.getArea_uso_princiapal().equals(area) || recursoTecnologico.getArea_uso_secundario().equals(area))
                .collect(Collectors.toList());


        return filtro.stream()
                .map(recursoTecnologico->new RecursoTecnologicoResponseDTO(
                        recursoTecnologico.getId(),
                        recursoTecnologico.getNombre(),
                        recursoTecnologico.getTipo_recurso(),
                        recursoTecnologico.getArea_uso_princiapal(),
                        recursoTecnologico.getArea_uso_secundario(),
                        recursoTecnologico.getEstado_recurso(),
                        recursoTecnologico.getCantidad_disponible()
                )).toList();



    }

}
