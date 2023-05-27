package sv.ues.fia.eisi.cafetinesues.hc17018.PrecioProducto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;

public class ConsultarPrecioProductoActivity extends AppCompatActivity {

    ControlBD helper;
    Spinner editIdProdSpinner;
    ListView listaPrecios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_precio_producto);
        helper = new ControlBD(this);

        editIdProdSpinner = (Spinner) findViewById(R.id.editIdProdSpinner);
        llenarSpinner();
        listaPrecios = (ListView) findViewById(R.id.listViewPrecios);
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
        ArrayList<PrecioProducto> precioProd = helper.consultarPrecioProducto(idProducto, 0);
        helper.cerrar();
        if(precioProd.size() == 0) {
            Toast.makeText(this, "No se encontraron precios para el producto " + cadena, Toast.LENGTH_LONG).show();
            limpiarTexto(v);
        }else{
            String[] precioInfo = new String[precioProd.size()];
            for (int i = 0; i < precioProd.size(); i++) {
                String actual ="Anterior";
                if (precioProd.get(i).getEsActivo() == 1)
                    actual = "Precio Actual";

                precioInfo[i] = "$ " + precioProd.get(i).getPrecioProducto() + "      - Fecha: " + precioProd.get(i).getFechaPrecio() + "      - " + actual;
            }
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,precioInfo);
            listaPrecios.setAdapter(adaptador);
        }
    }

    public void limpiarTexto(View v) {
        editIdProdSpinner.setSelection(0);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new String[] {""});
        listaPrecios.setAdapter(adaptador);
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