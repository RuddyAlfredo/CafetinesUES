package sv.ues.fia.eisi.cafetinesues.pm11074.Facultad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;

public class EliminarFacultadActivity extends AppCompatActivity {

    EditText editId;
    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_facultad);
        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
    }

    public void eliminarFacultad(View v){
        String regEliminados;
        Facultad fac = new Facultad();
        fac.setIdFacultad(editId.getText().toString());
        helper.abrir();
        regEliminados = helper.eliminar(fac);
        helper.cerrar();
        Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
    }
}