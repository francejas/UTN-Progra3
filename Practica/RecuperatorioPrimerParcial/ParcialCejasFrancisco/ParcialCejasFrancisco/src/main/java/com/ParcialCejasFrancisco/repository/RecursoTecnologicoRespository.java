package com.ParcialCejasFrancisco.repository;

import com.ParcialCejasFrancisco.model.RecursoTecnologico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecursoTecnologicoRespository extends JpaRepository<RecursoTecnologico, Integer> {
}
