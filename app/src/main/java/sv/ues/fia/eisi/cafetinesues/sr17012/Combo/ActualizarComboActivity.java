package sv.ues.fia.eisi.cafetinesues.sr17012.Combo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.hc17018.PrecioProducto.PrecioProducto;
import sv.ues.fia.eisi.cafetinesues.hc17018.Producto.Producto;

public class ActualizarComboActivity extends Activity {

    ControlBD helper;
    EditText editNombre;
    EditText editPrecio;
    Spinner editIdLocalSpinner;
    Spinner editIdProductoSpinner;
    Spinner editIdComboSpinner;
    ListView listaCombo;
    ArrayList<String> prodComboIds = new ArrayList<String>();
    ArrayList<String> prodComboNames = new ArrayList<String>();
    String idLocal = "";
    String idCombo = "";
    String idProductos = "";
    String nomProductos = "";
    CheckBox disponible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_combo);
        helper = new ControlBD(this);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editPrecio = (EditText) findViewById(R.id.editPrecio);
        disponible = (CheckBox) findViewById(R.id.disponible);
        disponible.setChecked(false);
        prodComboNames.clear();
        prodComboIds.clear();

        listaCombo = (ListView) findViewById(R.id.listaCombo);
        editIdProductoSpinner = (Spinner) findViewById(R.id.editIdProductoSpinner);
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
                llenarSpinner(2, idLocal);
                clearList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        editIdProductoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(editIdProductoSpinner.getSelectedItemPosition() != 0) {
                    String cadena2 = editIdProductoSpinner.getSelectedItem().toString();
                    for (int i = 0; i < cadena2.length(); i++) { // En el spinner, el ID está mezaclado con el nombre, aqui lo separamos pa guardarlo como FK
                        if (cadena2.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                            idProductos = editIdProductoSpinner.getSelectedItem().toString().substring(0, i);
                            break;
                        }
                    }

                    for (int i = cadena2.length() - 1; i > 0; i--) {
                        if (cadena2.charAt(i) == '-') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                            nomProductos = editIdProductoSpinner.getSelectedItem().toString().substring(i+1, cadena2.length());
                            break;
                        }
                    }

                    if (!prodComboIds.contains(idProductos)) { //Solo agregamos el producto si no esta ya metido
                        prodComboIds.add(idProductos);
                        prodComboNames.add(nomProductos);
                        listaCombo();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Eliminar elementos del listview
        listaCombo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActualizarComboActivity.this);
                alertDialog.setTitle("Importante");
                alertDialog.setMessage("¿ Eliminar '" + prodComboNames.get(i) + "' de la lista ?");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        prodComboNames.remove(i);
                        prodComboIds.remove(i);
                        listaCombo();
                    }
                });
                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                alertDialog.show();

                return false;
            }
        });
    }

    public void actualizarCombo(View v) {
        Combo combo = new Combo();

        combo.setIdCombo(idCombo);
        combo.setIdLocal(idLocal);
        combo.setNomCombo(editNombre.getText().toString());
        combo.setPrecioCombo(editPrecio.getText().toString());

        combo.setDisponible(0);
        if(disponible.isChecked() == true)
            combo.setDisponible(1);

        combo.setProdComboIds(prodComboIds);

        int camposLlenos = 0;
        if(editNombre.getText().toString().isEmpty() == false){camposLlenos++;}
        if(editPrecio.getText().toString().isEmpty() == false){camposLlenos++;}
        if(prodComboNames.size() > 0){camposLlenos++;}

        if(editIdLocalSpinner.getSelectedItemPosition() != 0 && editIdComboSpinner.getSelectedItemPosition() != 0 && camposLlenos == 3) {
            helper.abrir();
            String estado = helper.actualizar(combo);
            helper.cerrar();

            Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Faltan datos que llenar", Toast.LENGTH_SHORT).show();

    }

    public void consultarCombo(View v) {
        String cadena = editIdComboSpinner.getSelectedItem().toString();
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
                Toast.makeText(this, "No se encontró este combo ", Toast.LENGTH_LONG).show();
            }else{
                editNombre.setText(combo.getNomCombo());
                editPrecio.setText(combo.getPrecioCombo());

                if(combo.getDisponible() == 1)
                    disponible.setChecked(true);

                prodComboIds = combo.getProdComboIds();
                prodComboNames = combo.getProdComboNom();

                String[] comboInfo = new String[prodComboIds.size()];
                for (int i = 0; i < prodComboNames.size(); i++) {
                    comboInfo[i] = prodComboIds.get(i) + " - " + prodComboNames.get(i);
                }
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,comboInfo);
                listaCombo.setAdapter(adaptador);
            }
        }else
            Toast.makeText(this, "Debe seleccionar Local y Combo", Toast.LENGTH_SHORT).show();
    }

    public void listaCombo(){
        String[] productoInfo = new String[prodComboNames.size()];
        for (int i = 0; i < prodComboNames.size(); i++) {
            productoInfo[i] = prodComboIds.get(i) + " - " + prodComboNames.get(i);
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,productoInfo);
        listaCombo.setAdapter(adaptador);
    }

    public void limpiarTexto(View v) {
        editIdLocalSpinner.setSelection(0);
        editIdProductoSpinner.setSelection(0);
        editIdComboSpinner.setSelection(0);
        disponible.setChecked(false);
        editNombre.setText("");
        editPrecio.setText("");
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
        else if(spinner == 2){
            helper.abrir();
            ArrayList<String> allCombos = helper.getAllCombos(id);
            helper.cerrar();

            String[] combos = new String[allCombos.size()];
            combos = allCombos.toArray(combos);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, combos);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editIdComboSpinner.setAdapter(dataAdapter);
        }
        else{
            helper.abrir();
            ArrayList<String> allProductos = helper.getAllProductos(id);
            helper.cerrar();

            String[] productos = new String[allProductos.size()];
            productos = allProductos.toArray(productos);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, productos);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editIdProductoSpinner.setAdapter(dataAdapter);
        }
    }
}