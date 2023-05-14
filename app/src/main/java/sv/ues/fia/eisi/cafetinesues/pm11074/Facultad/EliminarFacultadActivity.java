package sv.ues.fia.eisi.cafetinesues.pm11074.Facultad;

import androidx.appcompat.app.AppCompatActivity;

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

public class EliminarFacultadActivity extends AppCompatActivity {

    EditText editId;
    EditText editNombre;
    Button eliminar;
    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_facultad);
        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
        editNombre = (EditText) findViewById(R.id.editNombre);
        eliminar = (Button) findViewById(R.id.eliminar);
        eliminar.setEnabled(false);
    }

    public void eliminarFacultad(View v){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Eliminar Facultad");
        alertDialog.setMessage("¿Está seguro que desea eliminar éste elemento?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sí", (dialog, which) -> {
            String regEliminados;
            Facultad fac = new Facultad();
            fac.setIdFacultad(editId.getText().toString());
            helper.abrir();
            regEliminados = helper.eliminar(fac);
            helper.cerrar();
            Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
            eliminar.setEnabled(false);
            editNombre.setText("");
        });
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }

    public void consultarFacultad(View v) {
        helper.abrir();
        Facultad facultad = helper.consultarFacultad(editId.getText().toString());
        helper.cerrar();
        if(facultad == null)
            Toast.makeText(this, "Facultad con Id " + editId.getText().toString() + " no encontrada", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(facultad.getNomFacultad());
            eliminar.setEnabled(true);
        }
    }
}