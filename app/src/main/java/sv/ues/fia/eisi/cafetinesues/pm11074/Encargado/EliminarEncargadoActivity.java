package sv.ues.fia.eisi.cafetinesues.pm11074.Encargado;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;

public class EliminarEncargadoActivity extends Activity {

    EditText editId;
    Button eliminar;
    EditText editNombre;
    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_encargado);
        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
        editNombre = (EditText) findViewById(R.id.editNombre);
        eliminar = (Button) findViewById(R.id.eliminar);
        eliminar.setEnabled(false);
    }

    public void eliminarEncargado(View v){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Eliminar Encargado");
        alertDialog.setMessage("¿Está seguro que desea eliminar éste elemento?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sí", (dialog, which) -> {
            String regEliminados;
            Encargado enc = new Encargado();
            enc.setIdEncargado(editId.getText().toString());
            helper.abrir();
            regEliminados = helper.eliminar(enc);
            helper.cerrar();
            Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
            eliminar.setEnabled(false);
            editNombre.setText("");
        });
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }

    public void consultarEncargado(View v) {
        helper.abrir();
        Encargado encargado = helper.consultarEncargado(editId.getText().toString());
        helper.cerrar();
        if(encargado == null)
            Toast.makeText(this, "Encargado con Id " + editId.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(encargado.getNomEncargado());
            eliminar.setEnabled(true);
        }
    }
}