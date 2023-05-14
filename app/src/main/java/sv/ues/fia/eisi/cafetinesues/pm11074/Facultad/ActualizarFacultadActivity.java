package sv.ues.fia.eisi.cafetinesues.pm11074.Facultad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;

public class ActualizarFacultadActivity extends Activity {

    ControlBD helper;
    EditText editId;
    EditText editNombre;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_facultad);
        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }

    public void actualizarFacultad(View v) {
        Facultad facultad = new Facultad();
        facultad.setIdFacultad(editId.getText().toString());
        facultad.setNomFacultad(editNombre.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(facultad);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    public void consultarFacultad(View v) {
        helper.abrir();
        Facultad facultad = helper.consultarFacultad(editId.getText().toString());
        helper.cerrar();
        if(facultad == null)
            Toast.makeText(this, "Facultad con Id " + editId.getText().toString() + " no encontrada", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(facultad.getNomFacultad());
        }
    }

    public void limpiarTexto(View v) {
        editId.setText("");
        editNombre.setText("");
    }
}