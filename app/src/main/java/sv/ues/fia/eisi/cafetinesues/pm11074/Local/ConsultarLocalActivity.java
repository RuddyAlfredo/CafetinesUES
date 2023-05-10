package sv.ues.fia.eisi.cafetinesues.pm11074.Local;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;

public class ConsultarLocalActivity extends Activity {

    ControlBD helper;
    EditText editId;
    EditText editIdEnc;
    EditText editNombre;
    EditText checkEsInterno;
    EditText editNomLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_local);
        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
        editIdEnc = (EditText) findViewById(R.id.editIdEnc);
        editNombre = (EditText) findViewById(R.id.editNombre);
        checkEsInterno = (EditText) findViewById(R.id.checkEsInterno);

        editNomLocal = (EditText) findViewById(R.id.editNomLocal);
    }

    public void consultarLocal(View v) {
        helper.abrir();
        Local local = helper.consultarLocal(editId.getText().toString(), editNomLocal.getText().toString());
        helper.cerrar();
        if(local == null)
            Toast.makeText(this, "Local con Id " + editId.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editIdEnc.setText(local.getIdEncargado());
            editNombre.setText(local.getNomLocal());
            if(local.getEsInterno() == 1)
                checkEsInterno.setText("Es un local Interno");
            else
                checkEsInterno.setText("Es un local Externo");
        }
    }

    public void limpiarTexto(View v) {
        editId.setText("");
        editIdEnc.setText("");
        editNombre.setText("");
        checkEsInterno.setText("");
    }
}