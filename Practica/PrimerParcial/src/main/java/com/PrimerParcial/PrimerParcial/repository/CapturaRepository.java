package com.PrimerParcial.PrimerParcial.repository;

import com.PrimerParcial.PrimerParcial.modelo.Captura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CapturaRepository extends JpaRepository<Captura, Integer> {
    // Spring va a buscar todas las capturas donde el ID del entrenador coincida
    List<Captura> findByEntrenador_Id(Integer idEntrenador);
}
