package sv.ues.fia.eisi.cafetinesues.hc17018.Producto;

import android.service.controls.templates.RangeTemplate;

public class Producto {

    private String idProducto;
    private String idLocal;
    private String idTipoProducto;
    private String nomProducto;
    private String descripcion;
    private String precio;
    private String tipo;

    public Producto() {

    }

    public Producto(String idProducto, String idLocal, String idTipoProducto, String nomProducto, String descripcion, String precio, String tipo) {
        this.idProducto = idProducto;
        this.idLocal = idLocal;
        this.idTipoProducto = idTipoProducto;
        this.nomProducto = nomProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }

    public String getIdLocal() {
        return idLocal;
    }

    public void setIdTipoProducto(String idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getPrecio() {
        return precio;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
