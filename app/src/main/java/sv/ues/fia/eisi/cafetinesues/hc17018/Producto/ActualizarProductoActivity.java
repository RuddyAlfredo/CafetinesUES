package sv.ues.fia.eisi.cafetinesues.hc17018.Producto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import sv.ues.fia.eisi.cafetinesues.pm11074.Local.Local;

public class ActualizarProductoActivity extends Activity {

    ControlBD helper;
    EditText editTipo;
    EditText editNombre;
    EditText editPrecio;
    EditText editDesc;
    Spinner editIdLocalSpinner;
    Spinner editIdProductosSpinner;
    Spinner editIdTiposSpinner;
    String tipoBackup = "";
    String idBackup = "";

    Button btnActualizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_producto);

        helper = new ControlBD(this);

        editTipo = (EditText) findViewById(R.id.editTipo);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editPrecio = (EditText) findViewById(R.id.editPrecio);
        editDesc = (EditText) findViewById(R.id.editDesc);

        editIdLocalSpinner = (Spinner) findViewById(R.id.editIdLocalSpinner);
        llenarSpinner(0, "");

        editIdLocalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cadena = editIdLocalSpinner.getSelectedItem().toString();
                String idLocal = "--";
                for (int i = 0; i < cadena.length(); i++) { // En el spinner, el ID está mezaclado con el nombre, aqui lo separamos pa guardarlo como FK
                    if (cadena.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                        idLocal = editIdLocalSpinner.getSelectedItem().toString().substring(0, i);
                        break;
                    }
                }
                llenarSpinner(2, idLocal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        editIdProductosSpinner = (Spinner) findViewById(R.id.editIdProductosSpinner);
        editIdTiposSpinner = (Spinner) findViewById(R.id.editIdTiposSpinner);
        llenarSpinner(1, "");

        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnActualizar.setEnabled(false);
    }

    public void consultarProducto(View v) {
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

            helper.abrir();
            Producto producto = helper.consultarProducto(idProducto);
            helper.cerrar();
            if (producto == null)
                Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_LONG).show();
            else {
                editTipo.setText(producto.getIdTipoProducto());
                editPrecio.setText(producto.getPrecio());
                editNombre.setText(producto.getNomProducto());
                editDesc.setText(producto.getDescripcion());
                tipoBackup = producto.getTipo();
                idBackup = producto.getIdProducto();
                btnActualizar.setEnabled(true);
                editIdTiposSpinner.setSelection(0);
            }
        }else
            Toast.makeText(this, "No ha seleccionado Local o Producto", Toast.LENGTH_LONG).show();
    }

    public void actualizarProducto(View v) {
        Producto producto = new Producto();

        String idTipo = "";        //Si elegimos otro valor del spinner de
        if(editIdTiposSpinner.getSelectedItemPosition() != 0) { // Si no está seleccionado el primero del Spinner
            String cadena3 = editIdTiposSpinner.getSelectedItem().toString();
            for (int i = 0; i < cadena3.length(); i++) {
                if (cadena3.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                    idTipo += cadena3.substring(0, i);
                    break;
                }
            }
            producto.setIdTipoProducto(idTipo);
        }else
            producto.setIdTipoProducto(tipoBackup);

        producto.setPrecio(editPrecio.getText().toString());
        producto.setNomProducto(editNombre.getText().toString());
        producto.setDescripcion(editDesc.getText().toString());
        producto.setIdProducto(idBackup);

        helper.abrir();
        String estado = helper.actualizar(producto);
        helper.cerrar();
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editIdLocalSpinner.setSelection(0);
        editIdProductosSpinner.setSelection(0);
        editIdTiposSpinner.setSelection(0);
        editPrecio.setText("");
        editTipo.setText("");
        editNombre.setText("");
        editDesc.setText("");
        tipoBackup = "";
        idBackup = "";
        btnActualizar.setEnabled(false);
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
        }else if(spinner == 1){
            helper.abrir();
            ArrayList<String> allTipos = helper.getAllTipoProducto();
            helper.cerrar();

            String[] tipos = new String[allTipos.size()];
            tipos = allTipos.toArray(tipos);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipos);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editIdTiposSpinner.setAdapter(dataAdapter);
        }
        else{
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