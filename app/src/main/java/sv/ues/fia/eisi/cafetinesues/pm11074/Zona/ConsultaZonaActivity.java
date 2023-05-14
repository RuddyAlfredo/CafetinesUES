package sv.ues.fia.eisi.cafetinesues.pm11074.Zona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.ControlBD;

public class ConsultaZonaActivity extends AppCompatActivity {
    private ControlBD helper;

    private ListView listaZonas;
    ArrayList<Zona> zonas = new ArrayList<Zona>();
    ListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_zona);
        helper = new ControlBD(this);
        listaZonas = (ListView) findViewById(R.id.listViewZonas);
        helper.abrir();
        zonas = helper.consultarZona();
        helper.cerrar();
        adapter = new ListAdapter(this,R.layout.zona_row ,zonas);
        listaZonas.setAdapter(adapter);
    }


}

