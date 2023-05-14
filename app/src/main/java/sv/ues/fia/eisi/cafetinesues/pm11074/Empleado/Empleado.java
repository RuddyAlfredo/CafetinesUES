package sv.ues.fia.eisi.cafetinesues.pm11074.Empleado;

public class Empleado {
    private int idEmpleado;
    private String nombreEmpleado;
    private int idZona;
    private int idLocal;

    public Empleado() {
    }

    public Empleado(int idEmpleado, String nombreEmpleado, int idZona, int idLocal) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.idZona = idZona;
        this.idLocal = idLocal;
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

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }
}
