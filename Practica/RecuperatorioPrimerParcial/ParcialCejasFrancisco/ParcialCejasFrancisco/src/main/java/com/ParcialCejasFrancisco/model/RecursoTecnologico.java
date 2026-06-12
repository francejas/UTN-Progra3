package com.ParcialCejasFrancisco.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter@Setter
@NoArgsConstructor
public class RecursoTecnologico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String tipo_recurso;

    private String area_uso_princiapal;
    private String area_uso_secundario;

    private String estado_recurso;
    private Integer cantidad_disponible;
}
