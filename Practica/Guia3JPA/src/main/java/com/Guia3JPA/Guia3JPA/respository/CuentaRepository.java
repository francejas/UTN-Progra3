package com.Guia3JPA.Guia3JPA.respository;

import com.Guia3JPA.Guia3JPA.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
}
