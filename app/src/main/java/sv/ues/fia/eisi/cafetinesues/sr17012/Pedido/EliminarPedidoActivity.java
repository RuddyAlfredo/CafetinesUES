package sv.ues.fia.eisi.cafetinesues.sr17012.Pedido;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.hc17018.Producto.Producto;

public class EliminarPedidoActivity extends Activity {

    ControlBD helper;
    SharedPreferences prf;
    TextView editMonto;
    TextView editLocal;
    TextView editTipoPedido;
    TextView editTipoPago;
    TextView editId;
    ListView listaProductos;
    ListView listaCombos;

    String idBackup="";
    ArrayList<String> prodNames = new ArrayList<String>();

    ArrayList<String> comboNames = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_pedido);

        helper = new ControlBD(this);
        editMonto = (TextView) findViewById(R.id.editMonto);
        editLocal = (TextView) findViewById(R.id.editLocal);
        editTipoPedido = (TextView) findViewById(R.id.editTipoPedido);
        editTipoPago = (TextView) findViewById(R.id.editTipoPago);
        editId = (TextView) findViewById(R.id.editId);

        listaProductos = (ListView) findViewById(R.id.listaProductos);
        listaCombos = (ListView) findViewById(R.id.listaCombos);
    }

    public void consultarPedido(View v) {
        if(editId.getText().toString().isEmpty() == false) {
            helper.abrir();
            Pedido p = helper.consultarPedido(editId.getText().toString());
            helper.cerrar();
            if (p == null)
                Toast.makeText(this, "Pedido con Id " + editId.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
            else {
                editMonto.setText(p.getMonto());
                editLocal.setText(p.getIdLocal());
                editTipoPago.setText(p.getIdTipoPago());
                editTipoPedido.setText(p.getIdTipoPedido());
                idBackup = p.getIdPedido();
                prodNames = p.getProdNames();
                comboNames = p.getComboNames();
                listaCombos();

            }
        }else
            Toast.makeText(this, "Debe ingresar el ID del Pedido", Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editId.setText("");
        editMonto.setText("");
        editLocal.setText("");
        editTipoPedido.setText("");
        editTipoPago.setText("");

        clearList();
    }

    public void clearList(){
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new String[] {""});
        listaCombos.setAdapter(adaptador);
        listaProductos.setAdapter(adaptador);
        prodNames.clear();
        comboNames.clear();
    }

    public void listaProductos(){
        String[] productoInfo = new String[prodNames.size()];
        for (int i = 0; i < prodNames.size(); i++) {
            productoInfo[i] = prodNames.get(i);
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,productoInfo);
        listaProductos.setAdapter(adaptador);
    }

    public void listaCombos(){
        String[] comboInfo = new String[comboNames.size()];
        for (int i = 0; i < comboNames.size(); i++) {
            comboInfo[i] = comboNames.get(i);
        }
        Log.d("cont", String.valueOf(comboNames));

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,comboInfo);
        listaCombos.setAdapter(adaptador);
        listaProductos();
    }

    public void eliminarPedido(View v){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Eliminar Pedido");
        alertDialog.setMessage("¿Está seguro que desea eliminar éste elemento?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sí", (dialog, which) -> {
            if(editId.getText().toString().isEmpty() == false ) {

                String regEliminados;
                Pedido p = new Pedido();
                p.setIdPedido(idBackup);
                helper.abrir();
                regEliminados = helper.eliminar(p);
                helper.cerrar();
                Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
                limpiarTexto(v);
            }else
                Toast.makeText(this, "Ingrese el numero de pedido", Toast.LENGTH_SHORT).show();
        });
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }
}