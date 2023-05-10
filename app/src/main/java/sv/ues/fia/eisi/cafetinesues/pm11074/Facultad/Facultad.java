package sv.ues.fia.eisi.cafetinesues.pm11074.Facultad;

public class Facultad {
    private String idFacultad;
    private String nomFacultad;

    public Facultad() {

    }

    public Facultad(String idFacultad, String nomFacultad) {
        this.idFacultad = idFacultad;
        this.nomFacultad = nomFacultad;
    }

    public void setIdFacultad(String idFacultad) {
        this.idFacultad = idFacultad;
    }

    public String getIdFacultad() {
        return idFacultad;
    }

    public void setNomFacultad(String nomFacultad) {
        this.nomFacultad = nomFacultad;
    }

    public String getNomFacultad() {
        return nomFacultad;
    }
}
