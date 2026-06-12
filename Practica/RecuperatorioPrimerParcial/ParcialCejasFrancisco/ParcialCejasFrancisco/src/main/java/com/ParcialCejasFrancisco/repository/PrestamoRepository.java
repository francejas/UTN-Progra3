package com.ParcialCejasFrancisco.repository;

import com.ParcialCejasFrancisco.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
    public List<Prestamo> findByDocenteId(Integer id);
}
