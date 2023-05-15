package sv.ues.fia.eisi.cafetinesues.mc15048.Empleado;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;

public class InsertarEmpleadoActivity extends AppCompatActivity {
    private ControlBD helper;
    private EditText IdLocal;
    private EditText NombreEmpleado;
    private EditText ApellidoEmpleado;
    private EditText IdZona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_empleado);
        helper = new ControlBD(this);
        IdLocal = (EditText) findViewById(R.id.textLocalEmpleado);
        NombreEmpleado = (EditText) findViewById(R.id.textNombreEmpleado);
        ApellidoEmpleado = (EditText) findViewById(R.id.textApellidoEmpleado);
        IdZona = (EditText) findViewById(R.id.textZonaEmpleado);
    }

    public void insertarEmpleado(android.view.View v) {
        String regInsertados;
        Empleado empleado = new Empleado();
        empleado.setIdLocal(IdLocal.getText().toString());
        empleado.setNombreEmpleado(NombreEmpleado.getText().toString());
        empleado.setApeEmpleado(ApellidoEmpleado.getText().toString());
        empleado.setIdZona(Integer.parseInt(IdZona.getText().toString()));
        helper.abrir();
        regInsertados = helper.insertar(empleado);
        helper.cerrar();
        android.widget.Toast.makeText(this, regInsertados, android.widget.Toast.LENGTH_SHORT).show();
    }
}
