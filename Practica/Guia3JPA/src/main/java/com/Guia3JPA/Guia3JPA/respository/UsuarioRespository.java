package com.Guia3JPA.Guia3JPA.respository;

import com.Guia3JPA.Guia3JPA.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


// Al extender de JpaRepository, SpringBoot le inyecta automáticamente los métodos
// save(), findAll(), findById(), deleteById(), etc. ¡Sin escribir una sola línea de SQL!
public interface UsuarioRespository extends JpaRepository<Usuario, Integer> {
}
