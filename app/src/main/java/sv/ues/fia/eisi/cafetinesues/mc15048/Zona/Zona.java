package sv.ues.fia.eisi.cafetinesues.mc15048.Zona;

public class Zona {

    private int idZona;
    private String nomZona;

    public Zona() {
    }

    public Zona(int idZona, String nomZona) {
        this.idZona = idZona;
        this.nomZona = nomZona;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public String getNomZona() {
        return nomZona;
    }

    public void setNomZona(String nomZona) {
        this.nomZona = nomZona;
    }
}
