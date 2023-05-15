package sv.ues.fia.eisi.cafetinesues.mc15048.Empleado;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;

public class ActualizarEmpleadoActivity extends AppCompatActivity {
    private ControlBD helper;
    private EditText IdEmpleado;
    private EditText IdLocal;
    private EditText NombreEmpleado;
    private EditText ApellidoEmpleado;
    private EditText IdZona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_empleado);
        helper = new ControlBD(this);
        IdEmpleado = (EditText) findViewById(R.id.textIdEmpleado);
        IdLocal = (EditText) findViewById(R.id.textLocalEmpleado);
        NombreEmpleado = (EditText) findViewById(R.id.textNombreEmpleado);
        ApellidoEmpleado = (EditText) findViewById(R.id.textApellidoEmpleado);
        IdZona = (EditText) findViewById(R.id.textZonaEmpleado);
    }

    public void actualizarEmpleado(android.view.View v) {
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(Integer.parseInt(IdEmpleado.getText().toString()));
        empleado.setIdLocal(IdLocal.getText().toString());
        empleado.setNombreEmpleado(NombreEmpleado.getText().toString());
        empleado.setApeEmpleado(ApellidoEmpleado.getText().toString());
        empleado.setIdZona(Integer.parseInt(IdZona.getText().toString()));
        helper.abrir();
        String estado = helper.actualizarEmpleado(empleado);
        helper.cerrar();
        android.widget.Toast.makeText(this, estado, android.widget.Toast.LENGTH_SHORT).show();
    }
}
