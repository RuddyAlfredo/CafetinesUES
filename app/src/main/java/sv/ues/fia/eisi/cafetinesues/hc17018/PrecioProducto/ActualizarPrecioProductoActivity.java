package sv.ues.fia.eisi.cafetinesues.hc17018.PrecioProducto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.pm11074.Local.Local;

public class ActualizarPrecioProductoActivity extends Activity {

    ControlBD helper;
    Spinner editIdProdSpinner;
    EditText editPrecio;
    EditText editPrecioNuevo;
    String idBackup = "";
    Button btnActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_precio_producto);
        helper = new ControlBD(this);

        editIdProdSpinner = (Spinner) findViewById(R.id.editIdProdSpinner);
        llenarSpinner();

        editPrecio = (EditText) findViewById(R.id.editPrecio);
        editPrecioNuevo = (EditText) findViewById(R.id.editPrecioNuevo);
        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnActualizar.setEnabled(false);
    }

    public void consultarPrecioProducto(View v) {
        String cadena = editIdProdSpinner.getSelectedItem().toString();
        String idProducto = "--";
        for (int i = 0; i < cadena.length(); i++) { // En el spinner, el ID está mezaclado con el nombre, aqui lo separamos pa guardarlo como FK
            if (cadena.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                idProducto = editIdProdSpinner.getSelectedItem().toString().substring(0, i);
                break;
            }
        }

        helper.abrir();
        ArrayList<PrecioProducto> precioProd = helper.consultarPrecioProducto(idProducto, 1);
        helper.cerrar();
        if(precioProd.size() == 0) {
            Toast.makeText(this, "No se encontró precio ACTUAL para el producto " + cadena, Toast.LENGTH_LONG).show();
            editPrecio.setText("");
            btnActualizar.setEnabled(false);
        }else{
            PrecioProducto pp = precioProd.get(0);
            editPrecio.setText(pp.getPrecioProducto());
            idBackup = pp.getIdPrecioProducto();
            btnActualizar.setEnabled(true);
        }
    }

    public void actualizarPrecioProducto(View v) {
        PrecioProducto pp = new PrecioProducto();

        pp.setIdPrecioProducto(idBackup);

        if(editPrecioNuevo.getText().toString() == "")
            pp.setPrecioProducto(editPrecio.getText().toString());
        else
            pp.setPrecioProducto(editPrecioNuevo.getText().toString());

        helper.abrir();
        String estado = helper.actualizar(pp);
        helper.cerrar();

        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editIdProdSpinner.setSelection(0);
        editPrecio.setText("");
        editPrecioNuevo.setText("");
        btnActualizar.setEnabled(false);
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