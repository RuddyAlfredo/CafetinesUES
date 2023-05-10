package sv.ues.fia.eisi.cafetinesues.pm11074.Local;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;

public class ActualizarLocalActivity extends Activity {

    ControlBD helper;
    EditText editId;
    EditText editIdEnc;
    EditText editNombre;
    CheckBox checkEsInterno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_local);
        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
        editIdEnc = (EditText) findViewById(R.id.editIdEnc);
        editNombre = (EditText) findViewById(R.id.editNombre);
        checkEsInterno = (CheckBox) findViewById(R.id.checkEsInterno);
    }

    public void actualizarLocal(View v) {
        Local local = new Local();
        local.setIdLocal(editId.getText().toString());
        local.setIdEncargado(editIdEnc.getText().toString());
        local.setNomLocal(editNombre.getText().toString());

        int esInterno = 0;
        if(checkEsInterno.isChecked())
            esInterno = 1;
        local.setEsInterno(esInterno);

        helper.abrir();
        String estado = helper.actualizar(local);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editId.setText("");
        editIdEnc.setText("");
        editNombre.setText("");
        checkEsInterno.setChecked(false);
    }
}