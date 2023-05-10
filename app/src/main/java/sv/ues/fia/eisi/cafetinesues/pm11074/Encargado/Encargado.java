package sv.ues.fia.eisi.cafetinesues.pm11074.Encargado;

public class Encargado {
    private String idEncargado;
    private String nomEncargado;

    public Encargado() {

    }

    public Encargado(String idEncargado, String nomEncargado) {
        this.idEncargado = idEncargado;
        this.nomEncargado = nomEncargado;
    }

    public void setIdEncargado(String idEncargado) {
        this.idEncargado = idEncargado;
    }

    public String getIdEncargado() {
        return idEncargado;
    }

    public void setNomEncargado(String nomEncargado) {
        this.nomEncargado = nomEncargado;
    }

    public String getNomEncargado() {
        return nomEncargado;
    }
}
