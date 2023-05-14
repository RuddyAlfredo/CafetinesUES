package sv.ues.fia.eisi.cafetinesues.pm11074.Facultad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;

public class InsertarFacultadActivity extends Activity {

    ControlBD helper;

    EditText editNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_facultad);
        helper = new ControlBD(this);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }

    public void insertarFacultad(View v) {
        String nombre = editNombre.getText().toString();

        String regInsertados;
        Facultad facultad = new Facultad();
        facultad.setNomFacultad(nombre);
        helper.abrir();
        regInsertados = helper.insertar(facultad);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editNombre.setText("");
    }
}