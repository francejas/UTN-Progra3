package com.TPIntegradorJPA.TPIntegradorJPA.repository;

import com.TPIntegradorJPA.TPIntegradorJPA.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MascotaRespository extends JpaRepository<Mascota, Integer> {
    List<Mascota> findByCliente_IdCliente(Integer idCliente);
}
