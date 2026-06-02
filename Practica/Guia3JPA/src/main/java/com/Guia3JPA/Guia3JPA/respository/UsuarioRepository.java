package com.Guia3JPA.Guia3JPA.respository;

import com.Guia3JPA.Guia3JPA.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
