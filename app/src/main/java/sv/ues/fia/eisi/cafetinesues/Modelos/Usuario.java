package sv.ues.fia.eisi.cafetinesues.Modelos;

public class Usuario {
    private String idUsuario;
    private String nomUsuario;
    private String clave;

    public Usuario() {

    }

    public Usuario(String idUsuario, String nomUsuario, String clave) {
        this.idUsuario = idUsuario;
        this.nomUsuario = nomUsuario;
        this.clave = clave;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getClave() {
        return clave;
    }
}