package sv.ues.fia.eisi.cafetinesues.pm11074.Facultad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;

public class EliminarFacultadActivity extends AppCompatActivity {

    EditText editId;
    ControlBD helper;

    Button eliminar;
    TextView mensaje;
    LinearLayout botones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_facultad);
        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);

        eliminar = (Button) findViewById(R.id.eliminar);
        mensaje = (TextView) findViewById(R.id.mensaje);
        mensaje.setAlpha(0);
        botones = (LinearLayout) findViewById(R.id.botones);
        botones.setAlpha(0);
        botones.setEnabled(false);
    }

    public void eliminarFacultad(View v){
        String regEliminados;
        Facultad fac = new Facultad();
        fac.setIdFacultad(editId.getText().toString());
        helper.abrir();
        regEliminados = helper.eliminar(fac);
        helper.cerrar();
        Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
        noEliminar(v);
    }

    public void preguntar(View v){
        mensaje.setAlpha(1);
        botones.setAlpha(1);
        botones.setEnabled(true);

        eliminar.setEnabled(false);
        editId.setEnabled(false);
    }

    public void noEliminar(View v){
        mensaje.setAlpha(0);
        botones.setAlpha(0);
        botones.setEnabled(false);

        eliminar.setEnabled(true);
        editId.setEnabled(true);
    }
}