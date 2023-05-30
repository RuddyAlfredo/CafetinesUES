package sv.ues.fia.eisi.cafetinesues.mc15048.Zona;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
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
public class EliminarZonaActivity extends Activity {
    private ControlBD helper;
    EditText deleteIdZona;
    EditText deleteNombreZona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_zona);
        helper = new ControlBD(this);
        deleteIdZona = (EditText) findViewById(R.id.deleteIdZona);
        deleteNombreZona = (EditText) findViewById(R.id.deleteNombreZona);
    }

    public void eliminarZona(View v) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Está seguro que desea eliminar la zona?");
        alertDialog.setTitle("Eliminar zona");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sí", (dialog, which) -> {
            String regEliminadas;
            Zona zona = new Zona();
            if(!deleteIdZona.getText().toString().isEmpty()){
                zona.setIdZona(Integer.parseInt(deleteIdZona.getText().toString()));
            }else {
                zona.setIdZona(-1);
            }

            zona.setNomZona(deleteNombreZona.getText().toString());
            helper.abrir();
            regEliminadas=helper.eliminarZona(zona);
            helper.cerrar();
            EliminarServicioWeb("http://192.168.58.101:80/cafetines/eliminar_zona.php", zona);
            Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
        });
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }

    public void EliminarServicioWeb(String URL, Zona zona){
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
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        System.out.println(stringRequest);
    }
}
