package modelo;

public class Credencial {
    private int idCredencial;
    private int idUsuario;
    private String username;
    private String password;
    private Permiso permiso;

    public Credencial(int idCredencial, int idUsuario, String username, String password, Permiso permiso) {
        this.idCredencial = idCredencial;
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.permiso = permiso;
    }

    public Credencial(int idUsuario, String username, String password, Permiso permiso) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.permiso = permiso;
    }

    public int getIdCredencial() {
        return idCredencial;
    }

    public void setIdCredencial(int idCredencial) {
        this.idCredencial = idCredencial;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    @Override
    public String toString() {
        return "Credencial{" +
                "idCredencial=" + idCredencial +
                ", idUsuario=" + idUsuario +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", permiso=" + permiso +
                '}';
    }
}
