package sv.ues.fia.eisi.cafetinesues.hc17018.PrecioProducto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.pm11074.Local.Local;

public class EliminarPrecioProductoActivity extends Activity {

    ControlBD helper;
    Spinner editIdProdSpinner;
    EditText editPrecio;
    EditText editFecha;
    String idBackup = "";
    Button btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_precio_producto);
        helper = new ControlBD(this);

        editIdProdSpinner = (Spinner) findViewById(R.id.editIdProdSpinner);
        llenarSpinner();

        editPrecio = (EditText) findViewById(R.id.editPrecio);
        editFecha = (EditText) findViewById(R.id.editFecha);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnEliminar.setEnabled(false);
    }

    public void eliminarPrecioProducto(View v){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Eliminar Precio");
        alertDialog.setMessage("¿Está seguro que desea eliminar éste elemento?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sí", (dialog, which) -> {
            String regEliminados;
            PrecioProducto pp = new PrecioProducto();
            pp.setIdPrecioProducto(idBackup);
            helper.abrir();
            regEliminados = helper.eliminar(pp);
            helper.cerrar();
            Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
            limpiarTexto(v);
        });
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        alertDialog.show();
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
            editFecha.setText("");
            btnEliminar.setEnabled(false);
        }else{
            PrecioProducto pp = precioProd.get(0);
            editPrecio.setText("$ "+ pp.getPrecioProducto());
            editFecha.setText(pp.getFechaPrecio());
            idBackup = pp.getIdPrecioProducto();
            btnEliminar.setEnabled(true);
        }
    }


    public void limpiarTexto(View v) {
        editIdProdSpinner.setSelection(0);
        editPrecio.setText("");
        editFecha.setText("");
        btnEliminar.setEnabled(false);
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