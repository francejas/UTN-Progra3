package com.PrimerParcial.PrimerParcial.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter@Setter
@NoArgsConstructor
public class Entrenador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre_completo;

    private String ciudad;
    private Integer cantidad_medallas;
    private String gimnasio_favorito;

    // ✅ CAMBIALO POR ESTO:
    @OneToMany(mappedBy = "entrenador")
    private List<Captura> capturas;

}
