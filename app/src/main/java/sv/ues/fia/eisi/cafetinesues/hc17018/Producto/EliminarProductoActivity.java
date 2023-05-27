package sv.ues.fia.eisi.cafetinesues.hc17018.Producto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.hc17018.PrecioProducto.PrecioProducto;

public class EliminarProductoActivity extends Activity {

    ControlBD helper;

    Spinner editIdLocalSpinner;
    Spinner editIdProductosSpinner;
    Button btnEliminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_producto);
        helper = new ControlBD(this);

        editIdLocalSpinner = (Spinner) findViewById(R.id.editIdLocalSpinner);
        llenarSpinner(0, "");

        editIdLocalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cadena = editIdLocalSpinner.getSelectedItem().toString();
                String idLocal = "";
                for (int i = 0; i < cadena.length(); i++) { // En el spinner, el ID está mezaclado con el nombre, aqui lo separamos pa guardarlo como FK
                    if (cadena.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                        idLocal = editIdLocalSpinner.getSelectedItem().toString().substring(0, i);
                        break;
                    }
                }
                llenarSpinner(1, idLocal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        editIdProductosSpinner = (Spinner) findViewById(R.id.editIdProductosSpinner);

        btnEliminar = (Button) findViewById(R.id.btnEliminar);
    }

    public void eliminarProducto(View v){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Eliminar Producto");
        alertDialog.setMessage("¿Está seguro que desea eliminar éste elemento?  Se eliminarán también sus precios");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sí", (dialog, which) -> {
            if(editIdLocalSpinner.getSelectedItemPosition() != 0 && editIdProductosSpinner.getSelectedItemPosition() != 0) {
                //Obtenemos el Id del Producto
                String cadena2 = editIdProductosSpinner.getSelectedItem().toString();
                String idProducto = "";
                for (int i = 0; i < cadena2.length(); i++) { // En el spinner, el ID está mezaclado con el nombre, aqui lo separamos pa guardarlo como FK
                    if (cadena2.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                        idProducto = editIdProductosSpinner.getSelectedItem().toString().substring(0, i);
                        break;
                    }
                }

                String regEliminados;
                Producto p = new Producto();
                p.setIdProducto(idProducto);
                helper.abrir();
                regEliminados = helper.eliminar(p);
                helper.cerrar();
                Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
                limpiarTexto(v);
            }else
                Toast.makeText(this, "Seleccione Local y Prdoucto", Toast.LENGTH_SHORT).show();
        });
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }

    public void limpiarTexto(View v) {
        editIdLocalSpinner.setSelection(0);
        editIdProductosSpinner.setSelection(0);
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
        }else{
            helper.abrir();
            ArrayList<String> allProductos = helper.getAllProductos(id);
            helper.cerrar();

            String[] productos = new String[allProductos.size()];
            productos = allProductos.toArray(productos);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, productos);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editIdProductosSpinner.setAdapter(dataAdapter);
        }
    }
}