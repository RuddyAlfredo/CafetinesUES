package sv.ues.fia.eisi.cafetinesues.sr17012.Combo;

import java.util.ArrayList;

public class Combo {
    private String idCombo;
    private String idLocal;
    private String nomCombo;
    private String precioCombo;
    private int disponible;

    ArrayList<String> prodComboIds = new ArrayList<String>();
    ArrayList<String> prodComboNom = new ArrayList<String>();

    public Combo() {

    }

    public Combo(String idCombo, String idLocal, String nomCombo, String precioCombo, int disponible, ArrayList<String> prodComboIds, ArrayList<String> prodComboNom ) {
        this.idCombo = idCombo;
        this.idLocal = idLocal;
        this.nomCombo = nomCombo;
        this.precioCombo = precioCombo;
        this.disponible = disponible;
        this.prodComboIds = prodComboIds;
        this.prodComboNom = prodComboNom;
    }

    public void setIdCombo(String idCombo) {
        this.idCombo = idCombo;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }

    public void setNomCombo(String nomCombo) {
        this.nomCombo = nomCombo;
    }

    public void setPrecioCombo(String precioCombo) {
        this.precioCombo = precioCombo;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public String getIdCombo() {
        return idCombo;
    }

    public String getIdLocal() {
        return idLocal;
    }

    public String getNomCombo() {
        return nomCombo;
    }

    public String getPrecioCombo() {
        return precioCombo;
    }

    public int getDisponible() {
        return disponible;
    }

    public ArrayList<String> getProdComboIds() {
        return prodComboIds;
    }

    public void setProdComboIds(ArrayList<String> prodComboIds) {
        this.prodComboIds = prodComboIds;
    }

    public void setProdComboNom(ArrayList<String> prodComboNom) {
        this.prodComboNom = prodComboNom;
    }

    public ArrayList<String> getProdComboNom() {
        return prodComboNom;
    }
}
