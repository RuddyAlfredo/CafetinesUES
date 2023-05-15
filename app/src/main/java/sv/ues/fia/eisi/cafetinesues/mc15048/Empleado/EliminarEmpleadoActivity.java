package sv.ues.fia.eisi.cafetinesues.mc15048.Empleado;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;

public class EliminarEmpleadoActivity extends AppCompatActivity {
    private ControlBD helper;
    private EditText IdEmpleado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_empleado);
        helper = new ControlBD(this);
        IdEmpleado = (EditText) findViewById(R.id.IdEmpleado);
    }

    public void eliminarEmpleado(View v){
        String regEliminadas;
        Empleado empleado = new Empleado();
        helper.abrir();
        empleado = helper.obtenerEmpleado(IdEmpleado.getText().toString());
        helper.cerrar();
        if(empleado == null) {
            Toast.makeText(this, "Empleado con ID " + IdEmpleado.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
        }else if(empleado.getIdLocal() != null){
            Toast.makeText(this, "No se puede eliminar el empleado con ID " + IdEmpleado.getText().toString() + " porque está asignado a un local", Toast.LENGTH_LONG).show();
        }else if(empleado.getIdZona() >0) {
            Toast.makeText(this, "No se puede eliminar el empleado con ID " + IdEmpleado.getText().toString() + " porque está asignado a una zona", Toast.LENGTH_LONG).show();
        }else{
            helper.abrir();
            regEliminadas=helper.eliminarEmpleado(empleado);
            helper.cerrar();
            Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
        }
    }
}
