package sv.ues.fia.eisi.cafetinesues.mc15048.Zona;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
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

public class ActualizarZonaActivity extends Activity {

  private ControlBD helper;
  private EditText IdZona;
  private EditText NomZona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_actualizar_zona);
    helper = new ControlBD(this);
    IdZona = (EditText) findViewById(R.id.editIdZona);
    NomZona = (EditText) findViewById(R.id.editNombreZona);
  }

  public void actualizarZona(android.view.View v) {
    Zona zona = new Zona();
    zona.setIdZona(Integer.parseInt(IdZona.getText().toString()));
    zona.setNomZona(NomZona.getText().toString());
    helper.abrir();
    String estado = helper.actualizarZona(zona);
    helper.cerrar();
    actualizarServicioWeb("http://192.168.58.101:80/cafetines/actualizar_zona.php", zona);
    Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
  }

  public void actualizarServicioWeb(String URL, Zona zona){
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
        parametros.put("idZona", String.valueOf(zona.getIdZona()));
        parametros.put("nomZona", zona.getNomZona());
        return parametros;
      }
    };
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
  }

}
