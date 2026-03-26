package modelo.implementaciones;

public class Juego extends MediaBase {
    private int numeroVersion;

    public Juego(String id, String titulo, String creador, String genero, int numeroVersion) {
        super(id, titulo, creador, genero);
        setNumeroVersion(numeroVersion);
    }

    public int getNumeroVersion() { return numeroVersion; }

    public void setNumeroVersion(int numeroVersion) {
        if (numeroVersion <= 0) {
            throw new IllegalArgumentException("El número de versión debe ser positivo.");
        }
        this.numeroVersion = numeroVersion;
    }

    @Override
    public String toString() {
        return String.format("[Juego] ID: %s | Título: %s | Creador: %s | Género: %s | Versión: %d",
                id, titulo, creador, genero, numeroVersion);
    }
}