package sv.ues.fia.eisi.cafetinesues.sr17012.Combo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import sv.ues.fia.eisi.cafetinesues.hc17018.PrecioProducto.PrecioProducto;
import sv.ues.fia.eisi.cafetinesues.hc17018.Producto.Producto;
import sv.ues.fia.eisi.cafetinesues.sr17012.TipoProducto.TipoProducto;

public class ConsultarComboActivity extends Activity {

    ControlBD helper;
    Spinner editIdLocalSpinner;
    Spinner editIdComboSpinner;
    ListView listaCombo;
    ArrayList<String> prodComboIds = new ArrayList<String>();
    ArrayList<String> prodComboNames = new ArrayList<String>();
    String idLocal = "--";
    String idProductos = "";
    String nomProductos = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_combo);
        helper = new ControlBD(this);

        prodComboNames.clear();
        prodComboIds.clear();

        listaCombo = (ListView) findViewById(R.id.listaCombo);
        editIdComboSpinner = (Spinner) findViewById(R.id.editIdComboSpinner);
        editIdLocalSpinner = (Spinner) findViewById(R.id.editIdLocalSpinner);
        llenarSpinner(0,"");

        editIdLocalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sacarId();
                llenarSpinner(1, idLocal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void consultarCombo(View v) {
        String cadena = editIdComboSpinner.getSelectedItem().toString();
        String idCombo = "";
        for (int i = 0; i < cadena.length(); i++) { // En el spinner, el ID está mezaclado con el nombre, aqui lo separamos pa guardarlo como FK
            if (cadena.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                idCombo = editIdComboSpinner.getSelectedItem().toString().substring(0, i);
                break;
            }
        }

        if(editIdLocalSpinner.getSelectedItemPosition() != 0 && editIdComboSpinner.getSelectedItemPosition() != 0) {
            helper.abrir();
            Combo combo = helper.consultarCombo(idCombo);
            helper.cerrar();
            if(combo == null) {
                Toast.makeText(this, "No se encontraron productos para este combo ", Toast.LENGTH_LONG).show();
            }else{
                ArrayList<String> productos = combo.getProdComboNom();
                productos.add(0,"Nombre: "+combo.getNomCombo());
                productos.add(1,"Precio: $"+combo.getPrecioCombo());

                if(combo.getDisponible() == 1)
                    productos.add(2,"Disponible: SI");
                else
                    productos.add(2,"Disponible: NO");

                productos.add(3,"Contiene:");

                String[] comboInfo = new String[productos.size()];
                comboInfo = productos.toArray(comboInfo);
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,comboInfo);
                listaCombo.setAdapter(adaptador);
                }
        }else
            Toast.makeText(this, "Debe seleccionar Local y Combo", Toast.LENGTH_SHORT).show();
    }

    public void sacarId() {
        String cadena = editIdLocalSpinner.getSelectedItem().toString();
        for (int i = 0; i < cadena.length(); i++) { // En el spinner, el ID está mezaclado con el nombre, aqui lo separamos pa guardarlo como FK
            if (cadena.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                idLocal = editIdLocalSpinner.getSelectedItem().toString().substring(0, i);
                break;
            }
        }
    }

    public void limpiarTexto(View v) {
        editIdLocalSpinner.setSelection(0);
        editIdComboSpinner.setSelection(0);
        clearList();
    }

    public void clearList(){
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new String[] {""});
        listaCombo.setAdapter(adaptador);
        prodComboNames.clear();
        prodComboIds.clear();
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