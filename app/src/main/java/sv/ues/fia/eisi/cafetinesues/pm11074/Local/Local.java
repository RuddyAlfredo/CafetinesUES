package sv.ues.fia.eisi.cafetinesues.pm11074.Local;

public class Local {
    private String idLocal;
    private String idEncargado;
    private String nomLocal;
    private int esInterno;

    public Local() {

    }

    public Local(String idLocal, String idEncargado, String nomLocal, int esInterno) {
        this.idLocal = idLocal;
        this.idEncargado = idEncargado;
        this.nomLocal = nomLocal;
        this.esInterno = esInterno;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }

    public String getIdLocal() {
        return idLocal;
    }

    public void setIdEncargado(String idEncargado) {
        this.idEncargado = idEncargado;
    }

    public String getIdEncargado() {
        return idEncargado;
    }

    public void setNomLocal(String nomLocal) {
        this.nomLocal = nomLocal;
    }

    public String getNomLocal() {
        return nomLocal;
    }

    public void setEsInterno(int esInterno) {
        this.esInterno = esInterno;
    }

    public int getEsInterno() {
        return esInterno;
    }
}

