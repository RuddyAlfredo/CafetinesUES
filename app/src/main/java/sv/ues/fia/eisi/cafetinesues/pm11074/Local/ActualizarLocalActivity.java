package sv.ues.fia.eisi.cafetinesues.pm11074.Local;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;

public class ActualizarLocalActivity extends Activity {

    ControlBD helper;
    EditText editId;
    EditText editIdEncActual;
    Spinner editIdEncSpinner;
    EditText editNombre;
    CheckBox checkEsInterno;
    String idBackup = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_local);
        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
        editIdEncActual = (EditText) findViewById(R.id.editIdEncActual);

        editIdEncSpinner = (Spinner) findViewById(R.id.editIdEncSpinner);
        llenarSpinner();

        editNombre = (EditText) findViewById(R.id.editNombre);
        checkEsInterno = (CheckBox) findViewById(R.id.checkEsInterno);
    }

    public void actualizarLocal(View v) {
        Local local = new Local();

        String idEncargado = "";

        if(editIdEncSpinner.getSelectedItemPosition() != 0) {
            String cadena = editIdEncSpinner.getSelectedItem().toString();
            for (int i = 0; i < cadena.length(); i++) {
                if (cadena.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                    idEncargado += cadena.substring(0, i);
                    break;
                }
            }
        }
        else{
            String cadena = editIdEncActual.getText().toString();
            for (int i = 0; i < cadena.length(); i++) {
                if (cadena.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                    idEncargado += cadena.substring(0, i);
                    break;
                }
            }
        }

        local.setIdLocal(idBackup);
        local.setIdEncargado(idEncargado);
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
        editId.setEnabled(true);
        editIdEncActual.setText("");
        editIdEncSpinner.setSelection(0);
        editNombre.setText("");
        checkEsInterno.setChecked(false);
    }

    public void llenarSpinner(){
        helper.abrir();
        ArrayList<String> allEncargados = helper.getAllEncargados();
        helper.cerrar();

        String[] encargados = new String[allEncargados.size()];
        encargados = allEncargados.toArray(encargados);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, encargados);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editIdEncSpinner.setAdapter(dataAdapter);
    }

    public void consultarLocal(View v) {
        helper.abrir();
        Local local = helper.consultarLocal(editId.getText().toString());
        helper.cerrar();
        idBackup = editId.getText().toString();
        if(local == null)
            Toast.makeText(this, "Local con Id " + editId.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editIdEncActual.setText(local.getIdEncargado());
            editNombre.setText(local.getNomLocal());
            editId.setEnabled(false);
            if(local.getEsInterno() == 1)
                checkEsInterno.setChecked(true);
            else
                checkEsInterno.setChecked(false);
        }
    }
}