package com.TPIntegradorJPA.TPIntegradorJPA.repository;

import com.TPIntegradorJPA.TPIntegradorJPA.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Integer> {
    List<Turno> findByVeterinario_IdVeterinario(Integer idVeterinario);
}
