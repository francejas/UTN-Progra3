package com.PrimerParcial.PrimerParcial.repository;

import com.PrimerParcial.PrimerParcial.modelo.Pokemon;
import com.PrimerParcial.PrimerParcial.modelo.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

    // Escribimos la consulta SQL (JPQL) a mano para evitar el problema del guion bajo
    @Query("SELECT p FROM Pokemon p WHERE p.tipo_principal = :tipoBusqueda")
    List<Pokemon> buscarPorTipoPrincipal(@Param("tipoBusqueda") Tipo tipoBusqueda);

}