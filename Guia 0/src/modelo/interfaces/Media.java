package modelo.interfaces;

public interface Media extends Comparable<Media> {
    String getId();
    String getTitulo();
    String getCreador();
    String getGenero();

    void setTitulo(String titulo);
    void setCreador(String creador);
    void setGenero(String genero);
}