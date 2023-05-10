package sv.ues.fia.eisi.cafetinesues.pm11074.Encargado;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;

public class InsertarEncargadoActivity extends Activity {

    ControlBD helper;
    EditText editId;
    EditText editNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_encargado);
        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }

    public void insertarEncargado(View v) {
        String id = editId.getText().toString();
        String nombre = editNombre.getText().toString();

        String regInsertados;
        Encargado encargado = new Encargado();
        encargado.setIdEncargado(id);
        encargado.setNomEncargado(nombre);
        helper.abrir();
        regInsertados = helper.insertar(encargado);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editId.setText("");
        editNombre.setText("");
    }
}