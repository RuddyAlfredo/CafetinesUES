package sv.ues.fia.eisi.cafetinesues.mc15048.Zona;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
public class EliminarZonaActivity extends Activity {
    private ControlBD helper;
    EditText deleteIdZona;
    EditText deleteNombreZona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_zona);
        helper = new ControlBD(this);
        deleteIdZona = (EditText) findViewById(R.id.deleteIdZona);
        deleteNombreZona = (EditText) findViewById(R.id.deleteNombreZona);
    }

    public void eliminarZona(View v) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Está seguro que desea eliminar la zona?");
        alertDialog.setTitle("Eliminar zona");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sí", (dialog, which) -> {
            String regEliminadas;
            Zona zona = new Zona();
            if(!deleteIdZona.getText().toString().isEmpty()){
                zona.setIdZona(Integer.parseInt(deleteIdZona.getText().toString()));
            }else {
                zona.setIdZona(-1);
            }

            zona.setNomZona(deleteNombreZona.getText().toString());
            helper.abrir();
            regEliminadas=helper.eliminarZona(zona);
            helper.cerrar();
            Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
        });
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }
}
