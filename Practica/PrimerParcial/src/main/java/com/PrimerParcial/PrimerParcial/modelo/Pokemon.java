package com.PrimerParcial.PrimerParcial.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@Getter@Setter
@NoArgsConstructor
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private Tipo tipo_principal;

    @Enumerated(EnumType.STRING)
    private Tipo tipo_secundario;

    private Integer nivel_base;

    private Integer cantidad_disponible;


}
