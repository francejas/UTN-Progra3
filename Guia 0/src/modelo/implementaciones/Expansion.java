package modelo.implementaciones;

import java.time.LocalDate;

public class Expansion extends MediaBase {
    private LocalDate fechaLanzamiento;

    public Expansion(String id, String titulo, String creador, String genero, LocalDate fechaLanzamiento) {
        super(id, titulo, creador, genero);
        setFechaLanzamiento(fechaLanzamiento);
    }

    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        if (fechaLanzamiento == null) {
            throw new IllegalArgumentException("La fecha de lanzamiento no puede ser nula.");
        }
        this.fechaLanzamiento = fechaLanzamiento;
    }

    @Override
    public String toString() {
        return String.format("[Expansión] ID: %s | Título: %s | Creador: %s | Género: %s | Lanzamiento: %s",
                id, titulo, creador, genero, fechaLanzamiento.toString());
    }
}