package sv.ues.fia.eisi.cafetinesues.mc15048.Zona;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.internal.TextWatcherAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.ControlBD;

public class ConsultaZonaActivity extends AppCompatActivity {
    private ControlBD helper;

    private ListView listaZonas;
    private EditText search;
    ArrayList<Zona> zonas = new ArrayList<Zona>();

    ListAdapter adapter;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_zona);
        helper = new ControlBD(this);
        search = (EditText) findViewById(R.id.Search);
        listaZonas = (ListView) findViewById(R.id.listViewZonas);
        zonas = consultarWebService("http://192.168.58.101:80/cafetines/consultar_zona.php");
        adapter = new ListAdapter(this, R.layout.zona_row, zonas);
        if (zonas.isEmpty()) {
            listaZonas.setAdapter(null);
            Toast.makeText(this, "No hay registros", Toast.LENGTH_SHORT).show();
        } else {
            listaZonas.setAdapter(adapter);
        }
        search.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                ArrayList<Zona> suggestions = new ArrayList<>();

                //Si no hay nada que filtrar retorna todos los elementos
                if(s == null || s.length() == 0){
                    suggestions.addAll(zonas);
                }else{
                    String filterPattern = s.toString().toLowerCase().trim();
                    for(Zona zona: zonas){
                        if(zona.getNomZona().toLowerCase().contains(filterPattern)){
                            suggestions.add(zona);
                        }
                    }
                }
                adapter = new ListAdapter(ConsultaZonaActivity.this, R.layout.zona_row, suggestions);
                listaZonas.setAdapter(adapter);
            }

            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            }

            public void afterTextChanged(Editable s) {

            }
        });

    }


    private ArrayList<Zona> consultarWebService(String URL){
        ArrayList<Zona> lista = new ArrayList<>();
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Zona zona = new Zona();
                        zona.setIdZona(jsonObject.getInt("idZona"));
                        System.out.println(zona.getIdZona());
                        zona.setNomZona(jsonObject.getString("nomZona"));
                        System.out.println(zona.getNomZona());
                        lista.add(zona);
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
        System.out.println(lista);
        return lista;
    }


}

