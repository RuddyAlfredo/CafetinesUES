package sv.ues.fia.eisi.cafetinesues.hc17018.PrecioProducto;

public class PrecioProducto {
    private String idPrecioProducto;
    private String idProducto;
    private String precioProducto;
    private int esActivo;
    private String fechaPrecio;

    public PrecioProducto() {

    }

    public PrecioProducto(String idPrecioProducto, String idProducto, String precioProducto, int esActivo, String fechaPrecio) {
        this.idPrecioProducto = idPrecioProducto;
        this.idProducto = idProducto;
        this.precioProducto = precioProducto;
        this.esActivo = esActivo;
        this.fechaPrecio = fechaPrecio;
    }

    public void setIdPrecioProducto(String idPrecioProducto) {
        this.idPrecioProducto = idPrecioProducto;
    }

    public String getIdPrecioProducto() {
        return idPrecioProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setPrecioProducto(String precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getPrecioProducto() {
        return precioProducto;
    }

    public void setEsActivo(int esActivo) {
        this.esActivo = esActivo;
    }

    public int getEsActivo() {
        return esActivo;
    }

    public void setFechaPrecio(String fechaPrecio) {
        this.fechaPrecio = fechaPrecio;
    }

    public String getFechaPrecio() {
        return fechaPrecio;
    }
}
