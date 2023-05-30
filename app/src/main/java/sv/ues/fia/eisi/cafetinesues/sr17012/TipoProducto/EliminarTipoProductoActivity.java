package sv.ues.fia.eisi.cafetinesues.sr17012.TipoProducto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.mc15048.Zona.Zona;
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;

public class EliminarTipoProductoActivity extends Activity {

    EditText editId;
    Button eliminar;
    EditText editTipo;
    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_tipo_producto);

        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
        editTipo = (EditText) findViewById(R.id.editTipo);
        eliminar = (Button) findViewById(R.id.eliminar);
        eliminar.setEnabled(false);
    }

    public void eliminarTipoProducto(View v){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Eliminar Tipo de Producto");
        alertDialog.setMessage("¿Está seguro que desea eliminar éste elemento?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sí", (dialog, which) -> {
            String regEliminados;
            TipoProducto tp = new TipoProducto();
            tp.setIdTipoProducto(editId.getText().toString());
            /*helper.abrir();
            regEliminados = helper.eliminar(tp);
            helper.cerrar();*/
            EliminarServicioWeb("http://192.168.58.101:80/cafetines/eliminar_tipo.php", tp);
           //Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
            eliminar.setEnabled(false);
            editTipo.setText("");
            editId.setText("");
        });
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }

    public void consultarTipoProducto(View v) {
        consultarWebService("http://192.168.58.101:80/cafetines/buscar_tipo.php?idTipoProducto=" + editId.getText().toString() + "");
        if (!editId.getText().toString().isEmpty()) {
            eliminar.setEnabled(true);
        } else {
            Toast.makeText(this, "Debe de introducir el ID del tipo de producto", Toast.LENGTH_SHORT).show();
        }

    }

    public void EliminarServicioWeb(String URL, TipoProducto tp){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR EN LA OPERACION", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("idTipoProducto", tp.getIdTipoProducto());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        System.out.println(stringRequest);
    }
    private void consultarWebService(String URL){

        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        editTipo.setText(jsonObject.getString("tipo"));
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);

    }
}