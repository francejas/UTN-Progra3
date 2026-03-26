package modelo.implementaciones;

import modelo.interfaces.Media;

public abstract class MediaBase implements Media {
    protected String id;
    protected String titulo;
    protected String creador;
    protected String genero;

    public MediaBase(String id, String titulo, String creador, String genero) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("El ID no puede estar vacío");
        this.id = id;
        this.titulo = titulo;
        this.creador = creador;
        this.genero = genero;
    }

    @Override public String getId() { return id; }
    @Override public String getTitulo() { return titulo; }
    @Override public String getCreador() { return creador; }
    @Override public String getGenero() { return genero; }

    @Override public void setTitulo(String titulo) { this.titulo = titulo; }
    @Override public void setCreador(String creador) { this.creador = creador; }
    @Override public void setGenero(String genero) { this.genero = genero; }

    // Implementación de Comparable basada en el Identificador Único
    @Override
    public int compareTo(Media otraMedia) {
        return this.id.compareTo(otraMedia.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Media)) return false;
        Media otraMedia = (Media) obj;
        return this.id.equals(otraMedia.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}