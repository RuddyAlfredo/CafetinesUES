package sv.ues.fia.eisi.cafetinesues.sr17012.Combo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.sr17012.TipoProducto.TipoProducto;

public class EliminarComboActivity extends Activity {

    ControlBD helper;
    Spinner editIdLocalSpinner;
    Spinner editIdComboSpinner;
    String idLocal = "";
    String idCombo = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_combo);
        helper = new ControlBD(this);

        editIdComboSpinner = (Spinner) findViewById(R.id.editIdComboSpinner);

        editIdLocalSpinner = (Spinner) findViewById(R.id.editIdLocalSpinner);
        llenarSpinner(0,"");

        editIdLocalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cadena = editIdLocalSpinner.getSelectedItem().toString();
                for (int i = 0; i < cadena.length(); i++) { // En el spinner, el ID está mezaclado con el nombre, aqui lo separamos pa guardarlo como FK
                    if (cadena.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                        idLocal = editIdLocalSpinner.getSelectedItem().toString().substring(0, i);
                        break;
                    }
                }
                llenarSpinner(1, idLocal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void eliminarCombo(View v) {
        String cadena = editIdComboSpinner.getSelectedItem().toString();
        for (int i = 0; i < cadena.length(); i++) { // En el spinner, el ID está mezaclado con el nombre, aqui lo separamos pa guardarlo como FK
            if (cadena.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                idCombo = editIdComboSpinner.getSelectedItem().toString().substring(0, i);
                break;
            }
        }

        if(editIdLocalSpinner.getSelectedItemPosition() != 0 && editIdComboSpinner.getSelectedItemPosition() != 0) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Eliminar Combo");
            alertDialog.setMessage("¿Está seguro que desea eliminar éste elemento?");
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("Sí", (dialog, which) -> {
                String regEliminados ="";
                Combo c = new Combo();
                c.setIdCombo(idCombo);
                helper.abrir();
                regEliminados = helper.eliminar(c);
                helper.cerrar();
                Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
            });
            alertDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            alertDialog.show();
        }else
            Toast.makeText(this, "Debe seleccionar Local y Combo", Toast.LENGTH_SHORT).show();
    }



    public void llenarSpinner(int spinner, String id){
        if(spinner == 0) {
            helper.abrir();
            ArrayList<String> allLocales = helper.getAllLocales();
            helper.cerrar();

            String[] locales = new String[allLocales.size()];
            locales = allLocales.toArray(locales);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locales);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editIdLocalSpinner.setAdapter(dataAdapter);
        }
        else{
            helper.abrir();
            ArrayList<String> allCombos = helper.getAllCombos(id);
            helper.cerrar();

            String[] combos = new String[allCombos.size()];
            combos = allCombos.toArray(combos);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, combos);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editIdComboSpinner.setAdapter(dataAdapter);
        }
    }
}