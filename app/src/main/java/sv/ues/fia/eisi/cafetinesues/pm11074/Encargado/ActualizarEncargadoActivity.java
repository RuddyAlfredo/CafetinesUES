package sv.ues.fia.eisi.cafetinesues.pm11074.Encargado;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;

public class ActualizarEncargadoActivity extends Activity {

    ControlBD helper;
    EditText editId;
    EditText editNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_encargado);
        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editNombre.setEnabled(false);
    }

    public void actualizarEncargado(View v) {
        Encargado encargado = new Encargado();
        encargado.setIdEncargado(editId.getText().toString());
        encargado.setNomEncargado(editNombre.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(encargado);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    public void consultarEncargado(View v) {
        helper.abrir();
        Encargado encargado = helper.consultarEncargado(editId.getText().toString());
        helper.cerrar();
        if(encargado == null)
            Toast.makeText(this, "Encargado con Id " + editId.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(encargado.getNomEncargado());
            editId.setEnabled(false);
            editNombre.setEnabled(true);
        }
    }

    public void limpiarTexto(View v) {
        editId.setText("");
        editId.setEnabled(true);
        editNombre.setText("");
        editNombre.setEnabled(true);
    }
}