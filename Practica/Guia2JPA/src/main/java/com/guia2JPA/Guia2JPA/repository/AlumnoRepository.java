package com.guia2JPA.Guia2JPA.repository;

import com.guia2JPA.Guia2JPA.modelo.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {
}
