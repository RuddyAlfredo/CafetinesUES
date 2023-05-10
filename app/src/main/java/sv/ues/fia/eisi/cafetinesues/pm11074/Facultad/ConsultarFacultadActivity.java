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
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;

public class ConsultarFacultadActivity extends Activity {

    ControlBD helper;
    EditText editId;
    EditText editNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_facultad);
        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
        editNombre = (EditText) findViewById(R.id.editNombre);
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