package com.Guia3JPA.Guia3JPA.respository;

import com.Guia3JPA.Guia3JPA.model.Credenciales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredencialRepository extends JpaRepository<Credenciales, Integer> {
    Optional<Credenciales> findByUsernameAndPassword(String username, String pasword);
}
