package sv.ues.fia.eisi.cafetinesues.pm11074.Local;

public class Local {
    private String idLocal;
    private String nomLocal;
    private boolean esInterno;

    public Local() {

    }

    public Local(String idLocal, String nomLocal, boolean esInterno) {
        this.idLocal = idLocal;
        this.nomLocal = nomLocal;
        this.esInterno = esInterno;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }

    public String getIdLocal() {
        return idLocal;
    }

    public void setNomLocal(String nomLocal) {
        this.nomLocal = nomLocal;
    }

    public String getNomLocal() {
        return nomLocal;
    }

    public void setEsInterno(boolean esInterno) {
        this.esInterno = esInterno;
    }

    public boolean getEsInterno() {
        return esInterno;
    }
}

