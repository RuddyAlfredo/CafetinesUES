package sv.ues.fia.eisi.cafetinesues.Modelos;

public class OpcionCRUD {

    private String idOpcion;
    private String desOpcion;
    private int numCrud;

    public OpcionCRUD() {

    }

    public OpcionCRUD(String idOpcion, String desOpcion,  int numCrud) {
        this.idOpcion = idOpcion;
        this.desOpcion = desOpcion;
        this.numCrud = numCrud;
    }

    public void setIdOpcion(String idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getIdOpcion() {
        return idOpcion;
    }

    public void setDesOpcion(String desOpcion) {
        this.desOpcion = desOpcion;
    }

    public String getDesOpcion() {
        return desOpcion;
    }

    public void setNumCrud(int numCrud) {
        this.numCrud = numCrud;
    }

    public int getNumCrud() {
        return numCrud;
    }
}
