package com.Guia3JPA.Guia3JPA.respository;

import com.Guia3JPA.Guia3JPA.model.Credenciales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredencialesRespository extends JpaRepository<Credenciales, Integer> {
    // Spring Boot lee el nombre del método y arma el SQL automáticamente:
    // SELECT * FROM credenciales WHERE username = ? AND password = ?
    Optional<Credenciales> findByUsernameAndPassword(String username, String password);
}
