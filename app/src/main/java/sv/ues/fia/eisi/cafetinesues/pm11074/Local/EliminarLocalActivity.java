package sv.ues.fia.eisi.cafetinesues.pm11074.Local;

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
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;

public class EliminarLocalActivity extends Activity {

    ControlBD helper;
    EditText editId;
    EditText editNombre;
    Button eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_local);
        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);

        editNombre = (EditText) findViewById(R.id.editNombre);
        eliminar = (Button) findViewById(R.id.eliminar);
        eliminar.setEnabled(false);
    }

    public void eliminarLocal(View v){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Eliminar Encargado");
        alertDialog.setMessage("¿Está seguro que desea eliminar éste elemento?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sí", (dialog, which) -> {
            String regEliminados;
            Local local = new Local();
            local.setIdLocal(editId.getText().toString());
            helper.abrir();
            regEliminados = helper.eliminar(local);
            helper.cerrar();
            Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
            eliminar.setEnabled(false);
            editNombre.setText("");
        });
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }

    public void consultarLocal(View v) {
        helper.abrir();
        Local local = helper.consultarLocal(editId.getText().toString());
        helper.cerrar();
        if(local == null)
            Toast.makeText(this, "Local con Id " + editId.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(local.getNomLocal());
            eliminar.setEnabled(true);
        }
    }
}