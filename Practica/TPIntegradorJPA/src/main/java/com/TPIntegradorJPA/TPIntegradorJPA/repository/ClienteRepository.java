package com.TPIntegradorJPA.TPIntegradorJPA.repository;

import com.TPIntegradorJPA.TPIntegradorJPA.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
