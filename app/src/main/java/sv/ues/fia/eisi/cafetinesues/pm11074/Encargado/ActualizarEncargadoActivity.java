package sv.ues.fia.eisi.cafetinesues.pm11074.Encargado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;

public class ActualizarEncargadoActivity extends AppCompatActivity {

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

    public void limpiarTexto(View v) {
        editId.setText("");
        editNombre.setText("");
    }
}