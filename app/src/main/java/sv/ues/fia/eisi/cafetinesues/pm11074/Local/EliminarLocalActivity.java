package sv.ues.fia.eisi.cafetinesues.pm11074.Local;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;

public class EliminarLocalActivity extends Activity {

    ControlBD helper;
    EditText editId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_local);
        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
    }

    public void eliminarLocal(View v){
        String regEliminados;
        Local local = new Local();
        local.setIdLocal(editId.getText().toString());
        helper.abrir();
        regEliminados = helper.eliminar(local);
        helper.cerrar();
        Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
    }
}