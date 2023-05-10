package sv.ues.fia.eisi.cafetinesues.Modelos;


public class AccesoUsuario {
    private String idUsuario;
    private String idOpcion;

    public AccesoUsuario() {

    }

    public AccesoUsuario(String idUsuario, String idOpcion) {
        this.idUsuario = idUsuario;
        this.idOpcion = idOpcion;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdOpcion(String idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getIdOpcion() {
        return idOpcion;
    }
}
