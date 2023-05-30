package sv.ues.fia.eisi.cafetinesues.sr17012.TipoProducto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
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

public class ActualizarTipoProductoActivity extends Activity {

    ControlBD helper;
    EditText editId;
    EditText editTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_tipo_producto);

        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
        editTipo = (EditText) findViewById(R.id.editTipo);
        editTipo.setEnabled(false);
    }

    public void actualizarTipoProducto(View v) {
        if(editTipo.getText().toString().isEmpty() == false) {
            TipoProducto tipo = new TipoProducto();
            tipo.setIdTipoProducto(editId.getText().toString());
            tipo.setTipo(editTipo.getText().toString());

            helper.abrir();
            String estado = helper.actualizar(tipo);
            helper.cerrar();
            actualizarServicioWeb("http://192.168.58.101:80/cafetines/actualizar_tipo.php", tipo);
            Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Debe ingresar el nombre del tipo de producto", Toast.LENGTH_SHORT).show();
    }

    public void consultarTipoProducto(View v) {
        if(editId.getText().toString().isEmpty() == false) {

            TipoProducto tp = consultarWebService("http://192.168.58.101:80/cafetines/buscar_tipo.php?idTipoProducto=" + editId.getText().toString() + "");

            if (tp == null)
                Toast.makeText(this, "Tipo de Producto con Id " + editId.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
            else {
                editTipo.setText(tp.getTipo());
                editTipo.setEnabled(true);
                editId.setEnabled(false);
            }
        }else
            Toast.makeText(this, "Debe ingresar el ID del tipo de producto", Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editId.setText("");
        editId.setEnabled(true);
        editTipo.setText("");
        editTipo.setEnabled(false);
    }

    public void actualizarServicioWeb(String URL,TipoProducto tipo){
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
                parametros.put("idTipoProducto", String.valueOf(tipo.getIdTipoProducto()));
                parametros.put("tipo", tipo.getTipo());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private TipoProducto consultarWebService(String URL){
        TipoProducto tipo = new TipoProducto();
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        tipo.setIdTipoProducto(jsonObject.getString("idTipoProducto"));
                        tipo.setTipo(jsonObject.getString("tipo"));
                        editTipo.setText(tipo.getTipo());
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
        return tipo;
    }
}