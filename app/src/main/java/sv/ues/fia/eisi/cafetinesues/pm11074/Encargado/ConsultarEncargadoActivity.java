package sv.ues.fia.eisi.cafetinesues.pm11074.Encargado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;

public class ConsultarEncargadoActivity extends AppCompatActivity {

    ControlBD helper;
    EditText editId;
    EditText editNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_encargado);
        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }

    public void consultarEncargado(View v) {
        helper.abrir();
        Encargado encargado = helper.consultarEncargado(editId.getText().toString());
        helper.cerrar();
        if(encargado == null)
            Toast.makeText(this, "Encargado con Id " + editId.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(encargado.getNomEncargado());
        }
    }

    public void limpiarTexto(View v) {
        editId.setText("");
        editNombre.setText("");
    }
}