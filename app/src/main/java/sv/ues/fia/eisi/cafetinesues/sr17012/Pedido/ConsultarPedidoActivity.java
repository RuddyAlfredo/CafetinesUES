package sv.ues.fia.eisi.cafetinesues.sr17012.Pedido;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import sv.ues.fia.eisi.cafetinesues.sr17012.TipoProducto.TipoProducto;

public class ConsultarPedidoActivity extends Activity {

    ControlBD helper;
    SharedPreferences prf;
    TextView editMonto;
    TextView editLocal;
    TextView editTipoPedido;
    TextView editTipoPago;
    TextView editId;
    TextView editPagado;
    ListView listaProductos;
    ListView listaCombos;
    ArrayList<String> prodIds = new ArrayList<String>();
    ArrayList<String> prodNames = new ArrayList<String>();
    ArrayList<Integer> prodCantidades = new ArrayList<Integer>();
    ArrayList<String> prodPrecios = new ArrayList<String>();

    ArrayList<String> comboIds = new ArrayList<String>();
    ArrayList<String> comboNames = new ArrayList<String>();
    ArrayList<Integer> comboCantidades = new ArrayList<Integer>();
    ArrayList<String> comboPrecios = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_pedido);
        helper = new ControlBD(this);
        editMonto = (TextView) findViewById(R.id.editMonto);
        editLocal = (TextView) findViewById(R.id.editLocal);
        editTipoPedido = (TextView) findViewById(R.id.editTipoPedido);
        editTipoPago = (TextView) findViewById(R.id.editTipoPago);
        editId = (TextView) findViewById(R.id.editId);
        editPagado = (TextView) findViewById(R.id.editPagado);

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

                if(p.getPagado() == 1)
                    editPagado.setText("SI");
                else
                    editPagado.setText("NO");

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
        editPagado.setText("");

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
}