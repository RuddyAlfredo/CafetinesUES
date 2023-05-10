package sv.ues.fia.eisi.cafetinesues.pm11074.Encargado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;

public class EliminarEncargadoActivity extends AppCompatActivity {

    EditText editId;
    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_encargado);
        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
    }

    public void eliminarAlumno(View v){
        String regEliminados;
        Encargado enc = new Encargado();
        enc.setIdEncargado(editId.getText().toString());
        helper.abrir();
        regEliminados = helper.eliminar(enc);
        helper.cerrar();
        Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
    }

}