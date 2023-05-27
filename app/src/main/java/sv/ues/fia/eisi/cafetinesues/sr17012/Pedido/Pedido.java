package sv.ues.fia.eisi.cafetinesues.sr17012.Pedido;

import java.util.ArrayList;

public class Pedido {
    private String idPedido;
    private String idUsuario;
    private String idLocal;
    private String idTipoPedido;
    private String idTipoPago;
    private String monto;
    private String fechaPedido;
    private int pagado;

    ArrayList<String> prodIds = new ArrayList<String>();
    ArrayList<Integer> prodCant = new ArrayList<Integer>();
    ArrayList<String> prodNames = new ArrayList<String>();
    ArrayList<String> comboIds = new ArrayList<String>();
    ArrayList<String> comboNames = new ArrayList<String>();
    ArrayList<Integer> comboCant = new ArrayList<Integer>();

    public Pedido() {

    }

    public Pedido(String idPedido,String idUsuario, String idLocal, String idTipoPedido, String idTipoPago, String monto, String fechaPedido, int pagado) {
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.idLocal = idLocal;
        this.idTipoPedido = idTipoPedido;
        this.idTipoPago = idTipoPago;
        this.monto = monto;
        this.fechaPedido = fechaPedido;
        this.pagado = pagado;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }

    public void setIdTipoPedido(String idTipoPedido) {
        this.idTipoPedido = idTipoPedido;
    }

    public void setIdTipoPago(String idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public void setPagado(int pagado) {
        this.pagado = pagado;
    }

    public void setProdIds(ArrayList<String> prodIds) {
        this.prodIds = prodIds;
    }

    public void setComboIds(ArrayList<String> comboIds) {
        this.comboIds = comboIds;
    }

    public void setProdCant(ArrayList<Integer> prodCant) {
        this.prodCant = prodCant;
    }

    public void setComboCant(ArrayList<Integer> comboCant) {
        this.comboCant = comboCant;
    }


    public String getIdPedido() {
        return idPedido;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getIdLocal() {
        return idLocal;
    }

    public String getIdTipoPedido() {
        return idTipoPedido;
    }

    public String getIdTipoPago() {
        return idTipoPago;
    }

    public String getMonto() {
        return monto;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public int getPagado() {
        return pagado;
    }

    public ArrayList<String> getProdIds() {
        return prodIds;
    }

    public ArrayList<String> getComboIds() {
        return comboIds;
    }

    public ArrayList<Integer> getProdCant() {
        return prodCant;
    }

    public ArrayList<Integer> getComboCant() {
        return comboCant;
    }

    public ArrayList<String> getProdNames() {
        return prodNames;
    }

    public ArrayList<String> getComboNames() {
        return comboNames;
    }

    public void setProdNames(ArrayList<String> prodNames) {
        this.prodNames = prodNames;
    }

    public void setComboNames(ArrayList<String> comboNames) {
        this.comboNames = comboNames;
    }
}
