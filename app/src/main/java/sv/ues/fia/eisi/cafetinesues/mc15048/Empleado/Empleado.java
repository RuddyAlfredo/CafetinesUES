package sv.ues.fia.eisi.cafetinesues.mc15048.Empleado;

public class Empleado {
    private int idEmpleado;
    private String nombreEmpleado;
    private String apeEmpleado;
    private int idZona;
    private String idLocal;

    public Empleado() {
    }

    public Empleado(int idEmpleado, String nombreEmpleado, String apeEmpleado,int idZona, String idLocal) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.idZona = idZona;
        this.idLocal = idLocal;
        this.apeEmpleado = apeEmpleado;
    }

    public String getApeEmpleado() {
        return apeEmpleado;
    }

    public void setApeEmpleado(String apeEmpleado) {
        this.apeEmpleado = apeEmpleado;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public String getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }
}
