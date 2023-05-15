package sv.ues.fia.eisi.cafetinesues.pm11074.Local;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;

public class InsertarLocalActivity extends Activity {

    ControlBD helper;
    Spinner editIdEncSpinner;
    EditText editNombre;
    CheckBox checkEsInterno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_local);
        helper = new ControlBD(this);

        editIdEncSpinner = (Spinner) findViewById(R.id.editIdEncSpinner);
        llenarSpinner();

        editNombre = (EditText) findViewById(R.id.editNombre);
        checkEsInterno = (CheckBox) findViewById(R.id.checkEsInterno);
    }

    public void insertarLocal(View v) {

        String cadena = editIdEncSpinner.getSelectedItem().toString();
        String idEncargado = "";
        for (int i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                idEncargado = editIdEncSpinner.getSelectedItem().toString().substring(0, i);
                break;
            }
        }

        String encargado = "--";
        if (idEncargado != "--")
            encargado = idEncargado;

        String nombre = editNombre.getText().toString();

        int esInterno = 0;
        if(checkEsInterno.isChecked())
            esInterno = 1;

        String regInsertados;
        Local local = new Local();
        local.setIdEncargado(encargado);
        local.setNomLocal(nombre);
        local.setEsInterno(esInterno);
        helper.abrir();
        regInsertados = helper.insertar(local);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
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
}