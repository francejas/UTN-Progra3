package com.guia2JPA.Guia2JPA.repository;

import com.guia2JPA.Guia2JPA.modelo.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
    // Si querés buscar direcciones por el ID del alumno (como pedía tu Guía #2 en el punto 5)
    // Spring Boot lo programa solo con solo escribir el nombre del método así:
    List<Direccion> findByAlumnoId(Integer alumnoId);
}
