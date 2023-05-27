package sv.ues.fia.eisi.cafetinesues.sr17012.TipoProducto;

public class TipoProducto {
    private String idTipoProducto;
    private String tipo;

    public TipoProducto() {

    }

    public TipoProducto(String idTipoProducto, String tipo) {
        this.idTipoProducto = idTipoProducto;
        this.tipo = tipo;
    }

    public void setIdTipoProducto(String idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
