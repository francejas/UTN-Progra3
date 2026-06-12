package com.ParcialCejasFrancisco.service;

import com.ParcialCejasFrancisco.dto.PrestamoBreveResponseDTO;
import com.ParcialCejasFrancisco.dto.PrestamoRequestDTO;
import com.ParcialCejasFrancisco.dto.PrestamoResponseDTO;
import com.ParcialCejasFrancisco.exception.PrestamoInvalidoException;
import com.ParcialCejasFrancisco.exception.ResourceNotFound;
import com.ParcialCejasFrancisco.model.Docente;
import com.ParcialCejasFrancisco.model.Estado;
import com.ParcialCejasFrancisco.model.Prestamo;
import com.ParcialCejasFrancisco.model.RecursoTecnologico;
import com.ParcialCejasFrancisco.repository.DocenteRespository;
import com.ParcialCejasFrancisco.repository.PrestamoRepository;
import com.ParcialCejasFrancisco.repository.RecursoTecnologicoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private DocenteRespository docenteRespository;

    @Autowired
    private RecursoTecnologicoRespository recursoTecnologicoRespository;

    public PrestamoResponseDTO registrar (PrestamoRequestDTO requestDTO){
        Docente docente = docenteRespository.findById(requestDTO.docente().getId()).orElseThrow(()->new PrestamoInvalidoException("El docente con ID: "+requestDTO.docente().getId()+" no existe."));

        RecursoTecnologico recursoTecnologico = recursoTecnologicoRespository.findById(requestDTO.recursoTecnologico().getId()).orElseThrow(()->new PrestamoInvalidoException("El recurso con ID: "+requestDTO.recursoTecnologico().getId()+" no existe."));

        if (requestDTO.cantidad() < 0){
            throw new PrestamoInvalidoException("La cantidad debe ser mayor a 0.");
        }
        if (recursoTecnologico.getCantidad_disponible()<requestDTO.cantidad()){
            throw new PrestamoInvalidoException("No hay cantidad suficiente.");
        }
        if (requestDTO.fecha().isAfter(LocalDate.now())){
            throw new PrestamoInvalidoException("La fecha no puede ser posterior a la fecha actual.");
        }

        if (requestDTO.cantidadDias()<0 || requestDTO.cantidadDias()>15){
            throw new PrestamoInvalidoException("La cantidad de dias debe ser mayor a ser y no puede superar los 15 dias.");
        }

        if (requestDTO.recursoTecnologico().getEstado_recurso()=="En reparacion."){
            throw new PrestamoInvalidoException("El recurso tecnológico no puede estar en estado En reparación");
        }

        recursoTecnologico.setCantidad_disponible(recursoTecnologico.getCantidad_disponible()-requestDTO.cantidad());
        recursoTecnologicoRespository.save(recursoTecnologico);

        Prestamo prestamo = new Prestamo();
        prestamo.setDocente(docente);
        prestamo.setRecursoTecnologico(recursoTecnologico);
        prestamo.setFecha_prestamo(requestDTO.fecha());
        prestamo.setCantidad_unidades(requestDTO.cantidad());
        prestamo.setDias_prestamo(requestDTO.cantidadDias());
        prestamo.setEstado(Estado.ACTIVO);

        Prestamo prestamoGuardado = prestamoRepository.save(prestamo);
        return new PrestamoResponseDTO(prestamoGuardado.getId(),
                prestamoGuardado.getDocente(),
                prestamoGuardado.getRecursoTecnologico(),
                prestamoGuardado.getFecha_prestamo(),
                prestamoGuardado.getCantidad_unidades(),
                prestamoGuardado.getDias_prestamo(),
                prestamoGuardado.getEstado());
    }

    public PrestamoResponseDTO cancelar (Integer id){

        Prestamo prestamo = prestamoRepository.findById(id).orElseThrow(()->new PrestamoInvalidoException("El prestamo con ID: "+id+" no existe."));

        if (prestamo.getEstado()!=Estado.ACTIVO){
            throw new PrestamoInvalidoException("El prestamo debe estar ACTIVO");
        }
        prestamo.setEstado(Estado.CANCELADO);

        //revisar esto
        Integer idRecurso = prestamo.getRecursoTecnologico().getId();

        RecursoTecnologico recursoTecnologico = recursoTecnologicoRespository.findById(idRecurso).orElseThrow(()-> new ResourceNotFound("Recurso no encotrado."));
        recursoTecnologico.setCantidad_disponible(recursoTecnologico.getCantidad_disponible()+prestamo.getCantidad_unidades());

        recursoTecnologicoRespository.save(recursoTecnologico);
        Prestamo prestamoCancelado = prestamoRepository.save(prestamo);

        return new PrestamoResponseDTO(
                prestamoCancelado.getId(),
                prestamoCancelado.getDocente(),
                prestamoCancelado.getRecursoTecnologico(),
                prestamoCancelado.getFecha_prestamo(),
                prestamoCancelado.getCantidad_unidades(),
                prestamoCancelado.getDias_prestamo(),
                prestamoCancelado.getEstado()
        );
    }

    public List<PrestamoBreveResponseDTO> listarPorDocente (Integer id){
        Docente docente = docenteRespository.findById(id).orElseThrow(()->new ResourceNotFound("El docente con ID: "+id+" no existe."));


        List<Prestamo> lista = prestamoRepository.findByDocenteId(id);

        return lista.stream()
                .map(prestamo -> new PrestamoBreveResponseDTO(
                        prestamo.getDocente().getNombre_completo(),
                        prestamo.getDocente().getMateria(),
                        prestamo.getRecursoTecnologico().getNombre(),
                        prestamo.getRecursoTecnologico().getTipo_recurso(),
                        prestamo.getRecursoTecnologico().getArea_uso_princiapal(),
                        prestamo.getFecha_prestamo(),
                        prestamo.getCantidad_unidades(),
                        prestamo.getDias_prestamo(),
                        prestamo.getEstado()
                )).toList();


    }

}
