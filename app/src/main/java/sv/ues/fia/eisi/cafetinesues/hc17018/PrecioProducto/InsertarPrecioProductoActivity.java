package sv.ues.fia.eisi.cafetinesues.hc17018.PrecioProducto;

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
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;
import sv.ues.fia.eisi.cafetinesues.pm11074.Local.Local;

public class InsertarPrecioProductoActivity extends Activity {

    ControlBD helper;
    Spinner editIdProdSpinner;
    EditText editPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_precio_producto);
        helper = new ControlBD(this);

        editIdProdSpinner = (Spinner) findViewById(R.id.editIdProdSpinner);
        llenarSpinner();

        editPrecio = (EditText) findViewById(R.id.editPrecio);
        editPrecio.setText("");
    }

    public void insertarPrecioProducto(View v) {
        String cadena = editIdProdSpinner.getSelectedItem().toString();
        String idProductos = "";
        for (int i = 0; i < cadena.length(); i++) { // En el spinner, el ID está mezaclado con el nombre, aqui lo separamos pa guardarlo como FK
            if (cadena.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                idProductos = editIdProdSpinner.getSelectedItem().toString().substring(0, i);
                break;
            }
        }

        String prod = "--";
        if (idProductos != "--")
            prod = idProductos;

        String precio = editPrecio.getText().toString();

        String regInsertados;
        PrecioProducto pp = new PrecioProducto();
        pp.setIdProducto(prod);
        pp.setPrecioProducto(precio);

        if(editPrecio.getText().toString().isEmpty() == false && editIdProdSpinner.getSelectedItemPosition() != 0) {
            helper.abrir();
            regInsertados = helper.insertar(pp);
            helper.cerrar();

            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Falta ingresar el Precio o seleccionar Producto", Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editIdProdSpinner.setSelection(0);
        editPrecio.setText("");
    }

    public void llenarSpinner(){
        helper.abrir();
        ArrayList<String> allProductos = helper.getAllProductos("");
        helper.cerrar();

        String[] productos = new String[allProductos.size()];
        productos = allProductos.toArray(productos);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, productos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editIdProdSpinner.setAdapter(dataAdapter);
    }
}