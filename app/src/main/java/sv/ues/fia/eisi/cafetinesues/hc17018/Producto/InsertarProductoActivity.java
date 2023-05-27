package sv.ues.fia.eisi.cafetinesues.hc17018.Producto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.hc17018.PrecioProducto.PrecioProducto;

public class InsertarProductoActivity extends Activity {

    ControlBD helper;
    EditText editNombre;
    EditText editDesc;
    EditText editPrecio;
    Spinner editIdLocalSpinner;
    Spinner editIdTipoProductoSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_producto);
        helper = new ControlBD(this);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editPrecio = (EditText) findViewById(R.id.editPrecio);
        editDesc = (EditText) findViewById(R.id.editDesc);

        editIdLocalSpinner = (Spinner) findViewById(R.id.editIdLocalSpinner);
        llenarSpinner(0);
        editIdTipoProductoSpinner = (Spinner) findViewById(R.id.editIdTipoProductoSpinner);
        llenarSpinner(1);
    }

    public void insertarProducto(View v) {
        //Pal local
        String cadena1 = editIdLocalSpinner.getSelectedItem().toString();
        String idLocal = "";
        for (int i = 0; i < cadena1.length(); i++) { // En el spinner, el ID está mezaclado con el nombre, aqui lo separamos pa guardarlo como FK
            if (cadena1.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                idLocal = editIdLocalSpinner.getSelectedItem().toString().substring(0, i);
                break;
            }
        }

        //Pal tipo de producto
        String cadena2 = editIdTipoProductoSpinner.getSelectedItem().toString();
        String idTipo = "";
        for (int i = 0; i < cadena2.length(); i++) { // En el spinner, el ID está mezaclado con el nombre, aqui lo separamos pa guardarlo como FK
            if (cadena2.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                idTipo = editIdTipoProductoSpinner.getSelectedItem().toString().substring(0, i);
                break;
            }
        }

        String regInsertados;
        Producto producto = new Producto();

        int camposLlenos = 0;
        producto.setNomProducto(editNombre.getText().toString());
        if(!editNombre.getText().toString().isEmpty()) {camposLlenos++;}

        producto.setDescripcion(editDesc.getText().toString());
        if(!editDesc.getText().toString().isEmpty()) {camposLlenos++;}
        producto.setIdLocal(idLocal);
        producto.setIdTipoProducto(idTipo);
        producto.setPrecio(editPrecio.getText().toString());
        if(!editPrecio.getText().toString().isEmpty()) {camposLlenos++;}

        if(editIdLocalSpinner.getSelectedItemPosition() != 0 && editIdTipoProductoSpinner.getSelectedItemPosition() != 0 && camposLlenos == 3) {
            helper.abrir();
            regInsertados = helper.insertar(producto);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Faltan Datos", Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editIdLocalSpinner.setSelection(0);
        editIdTipoProductoSpinner.setSelection(0);
        editNombre.setText("");
        editDesc.setText("");
        editPrecio.setText("");
    }

    public void llenarSpinner(int version){
        if(version == 0) {
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
            ArrayList<String> allTipos = helper.getAllTipoProducto();
            helper.cerrar();

            String[] tipos = new String[allTipos.size()];
            tipos = allTipos.toArray(tipos);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipos);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editIdTipoProductoSpinner.setAdapter(dataAdapter);
        }
    }
}