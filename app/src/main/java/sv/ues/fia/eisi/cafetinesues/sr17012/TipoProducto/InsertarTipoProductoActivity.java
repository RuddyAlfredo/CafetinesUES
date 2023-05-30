package sv.ues.fia.eisi.cafetinesues.sr17012.TipoProducto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.mc15048.Zona.Zona;
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;

public class InsertarTipoProductoActivity extends Activity {

    ControlBD helper;
    EditText editTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_tipo_producto);

        helper = new ControlBD(this);
        editTipo = (EditText) findViewById(R.id.editTipo);
    }

    public void insertarTipoProducto(View v) {
        if(editTipo.getText().toString().isEmpty() == false) {
            String tipo = editTipo.getText().toString();

            String regInsertados;
            TipoProducto tp = new TipoProducto();
            tp.setTipo(tipo);
            helper.abrir();
            regInsertados = helper.insertar(tp);
            helper.cerrar();
            insertarServicioWeb("http://192.168.58.101:80/cafetines/insertar_tipo.php", tp);
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Debe ingresar el nombre del tipo de producto", Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editTipo.setText("");
    }

    public void insertarServicioWeb(String URL, TipoProducto tipo){
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
                parametros.put("tipo", tipo.getTipo() );
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}