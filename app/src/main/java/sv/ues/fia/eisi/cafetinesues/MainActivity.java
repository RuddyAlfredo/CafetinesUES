package sv.ues.fia.eisi.cafetinesues;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.EncargadoMenuActivity;

public class MainActivity extends ListActivity {

    private ControlBD BDhelper;
    Button btnCerrar;
    SharedPreferences prf;

    ArrayList<String> MenuOpciones = new ArrayList<String>(); // Listado para agregar las opciones a las que tiene acceso un usuario.

// Listado con toddos los menus disponibles, DE AQUI SE ELEGIRÁ A LO QUE CADA USUARIO TIENE ACCESO
    String[] allOptions = {
        "Gestionar Encargados","Gestionar Locales","Gestionar Facultades","Gestionar Producto","Gestionar Combo","Gestionar Combo/Producto"
    };

    ArrayList<String> MenuActivities = new ArrayList<String>(); // Listado para agregar las MenuActivities a las que tiene acceso un usuario.

// Listado de todas las Activities disponibles, DEBEN ESTAR EN EL MISMO ORDEN QUE EL MENU ALLOPTIONS
// Por Ejemplo: en 1er lugar en allOptions esta Gestionar Encargado por tanto EncargadoMenuActivity debe estar en 1er lugar en allActivities y asi con los demas.
     String[] allActivities = {
            "pm11074.Encargado.EncargadoMenuActivity",
            "pm11074.Local.LocalMenuActivity",
            "pm11074.Facultad.FacultadMenuActivity",
            "ql17001activities.horario.HorarioMenuActivity",
            "hr17016activities.salon.salonMenuActivity",
            "hr17016activities.tiposalon.tipoSalonMenuActivity"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setItemsList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MenuOpciones);
        setListAdapter(adapter);
        setContentView(R.layout.activity_main);
        btnCerrar = (Button) findViewById(R.id.btnCerrarS);
        Intent intent = new Intent(MainActivity.this, IniciarSesion.class);
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prf.edit();
                editor.clear();
                editor.commit();
                startActivity(intent);
            }
        });
    }

    private void setItemsList() {
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        String permisos = prf.getString("permisos", "no hay");
        ArrayList<String> permisosList = ControlBD.retornarArrayPermisos(permisos);
        
        for (String p : permisosList) {
            int id = Integer.parseInt(p);
            for(int i = 0; i < allOptions.length; i++){
                if( id -1 == i) {
                    MenuOpciones.add(allOptions[id - 1]); // Esto solo es para el menu de Gestionar --
                    MenuActivities.add(allActivities[id - 1]); // Esto solo es para Agregar las Activities --
                }
            }
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try {
            String nombreValue = MenuActivities.get(position);
            Class<?> clase = Class.forName("sv.ues.fia.eisi.cafetinesues." + nombreValue);
            Intent inte = new Intent(this, clase);
            startActivity(inte);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}