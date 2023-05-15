package sv.ues.fia.eisi.cafetinesues.mc15048.Zona;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;

public class InsertarZonaActivity extends Activity {

    ControlBD helper;

    EditText editNombreZona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_zona);
        helper = new ControlBD(this);
        editNombreZona = (EditText) findViewById(R.id.editNombreZona);
    }

    public void insertarZona(View v){
        String nomZona = editNombreZona.getText().toString();
        String regInsertados;
        Zona zona = new Zona();
        zona.setNomZona(nomZona);
        helper.abrir();
        regInsertados = helper.insertar(zona);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        editNombreZona.setText("");
    }
}

